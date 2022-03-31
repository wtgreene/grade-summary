import java.io.*;
import java.nio.file.*;
import java.util.Scanner;

/**
 * Creates a file containing the overall student grades for a course or
 * a file containing a summary for each grade item.
 * 
 * @author Will Greene
 */
public class Grader {

    /** Number of percentage points in the number "1" */
    public static final double PERCENTAGE_POINTS_IN_1 = 100.0;
    
    
    /**
     * Analyzes command line arguments and refers to the respective methods.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        
        // Parameter error checking - usage
        if (args.length != 3) {
            System.out.println("\nUsage: java -cp bin Grader {-o|-s} infile outfile\n");
            System.exit(1);
        }
        
        String flag = args[0];
        String inputFilePath = args[1];
        String outputFilePath = args[2];
        
        // Parameter error checking - usage
        if (!flag.equals("-o") && !flag.equals("-s")) {
            System.out.println("\nUsage: java -cp bin Grader {-o|-s} infile outfile\n");
            System.exit(1);
        }
        
        FileOutputStream outputStream = null;
        
        // Checks whether output file path exists - overwrite?
        Path path = Path.of(outputFilePath);
        if (Files.exists(path)) {
            System.out.println("\n" + outputFilePath + " exists - OK to overwrite");
            System.out.print("(y,n)?: ");
            Scanner console = new Scanner(System.in);
            String response = console.next();
            char decision = response.charAt(0);
            if (response.length() != 1 || (decision != 'y' && decision != 'Y')) {
                System.out.println();
                System.exit(1);
            }
                        
            try {
                outputStream = new FileOutputStream(outputFilePath);
            } catch (FileNotFoundException e) {
                System.out.println("\nCannot create output file\n");
                System.exit(1);
            }
        }
        
        try {
            outputStream = new FileOutputStream(outputFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("\nCannot create output file\n");
            System.exit(1);
        }
        
        PrintWriter output = new PrintWriter(outputStream);
        
        if (flag.equals("-o")) {
            try {
                outputOverallGrades(inputFilePath, output);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        
        else if (flag.equals("-s")) {
            try {
                outputGradeSummary(inputFilePath, output);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        
        else {
            System.out.println("\nTHIS CODE HAS A BUG\n");
        }
        
        System.out.println();
        output.close();
    }
    
    
    /**
     * Outputs overall student grades.
     *
     * @param inputFile input file name
     * @param out PrinterWriter
     * @throws IllegalArgumentException if unable to access inputFile
     * @throws IllegalArgumentException if out is null
     */
    public static void outputOverallGrades(String inputFile, PrintWriter out) {
        
        // Parameter error checking - unable to access inputFile
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream(inputFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to access input file: " + inputFile);
        }
        
        // Parameter error checking - null PrintWriter
        if (out == null) {
            throw new IllegalArgumentException("Null PrintWriter");
        }
                        
        int[] weights = getWeights(in);
                
        while (in.hasNextLine()) {
            String line = in.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            
            int id = lineScanner.nextInt();
            String name = lineScanner.next();
            
            int[] grades = new int[weights.length];
            
            for (int i = 0; i < weights.length; i++) {
                grades[i] = lineScanner.nextInt();
            }
            
            double avg = computeWeightedAverage(grades, weights);
            
            // Output file
            out.print (id + "," + name + ",");
            out.printf("%.2f\n", avg);
        }
        
        in.close();
    }
    
    
    /**
     * Outputs grade item summary.
     *
     * @param inputFile input file
     * @param out PrintWriter
     * @throws IllegalArgumentException if unable to access input file
     * @throws IllegalArgumentException if out is null
     */
    public static void outputGradeSummary(String inputFile, PrintWriter out) {
        
        // Parameter error checking - unable to access inputFile
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream(inputFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to access input file: " + inputFile);
        }
        
        // Parameter error checking - null PrintWriter
        if (out == null) {
            throw new IllegalArgumentException("Null PrintWriter");
        }
        
        String[] gradeItemNames = getGradeItemNames(in);
        
        // New Scanner
        try {
            in = new Scanner(new FileInputStream(inputFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to access input file: " + inputFile);
        }
        
        // Subtracts column header and grade weight lines
        int numLines = getNumberOfLines(in) - 2;
        
        // New Scanner
        try {
            in = new Scanner(new FileInputStream(inputFile));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to access input file: " + inputFile);
        }
        
        int[][] allGrades = getAllGrades(in, numLines, gradeItemNames.length);
        
        // Output file
        for (int i = 0; i < gradeItemNames.length; i++) {
            out.print (gradeItemNames[i] + ",");
            out.printf("%.2f,", computeAverage(allGrades, i));
            out.print (getMinimum(allGrades, i) + ",");
            out.print (getMaximum(allGrades, i) + "\n");
        }
        
        in.close();
    }
    
    
    /**
     * Initializes and creates an array with weights of assignment grades.
     *
     * @param in file scanner
     * @return array that contains the weights
     * @throws IllegalArgumentException if in is null
     */
    public static int[] getWeights(Scanner in) {  
    
        // Parameter error checking - null scanner
        if (in == null) {
            throw new IllegalArgumentException("Null file");
        }
    
        String line = in.nextLine();
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter(",");
        
        lineScanner.next();
        lineScanner.next();
        
        int numOfGradeItems = 0;
        
        while (lineScanner.hasNext()) {
            lineScanner.next();
            numOfGradeItems++;
        }
                
        int[] weights = new int[numOfGradeItems];
        lineScanner.close();
        
        String lineAgain = in.nextLine();
        Scanner lineAgainScanner = new Scanner(lineAgain);
        lineAgainScanner.useDelimiter(",");
        
        lineAgainScanner.next();
        lineAgainScanner.next();
        
        for (int i = 0; i < numOfGradeItems; i++) {
            weights[i] = lineAgainScanner.nextInt();
        }
        
        return weights;
    }
    
    
    /**
     * Computes and returns a weighted average of a student's grades.
     *
     * @param grades array of a student's grades
     * @param weights array of grades' weights
     * @return weighted average of a student's grades
     * @throws IllegalArgumentException if the grades and weights arrays do not have the same length
     * @throws IllegalArgumentException if the sum of the weights is not 100
     */
    public static double computeWeightedAverage(int[] grades, int[] weights) {
    
        // Parameter error checking - array lengths
        if (grades.length != weights.length) {
            throw new IllegalArgumentException("Different array lengths");
        }
        
        // Parameter error checking - sum of weights
        int totalWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            totalWeight += weights[i];
        }
        if (totalWeight != 100) {
            throw new IllegalArgumentException("Invalid weights");
        }
        
        double totalGrade = 0.0;
        
        for (int i = 0; i < grades.length; i++) {
            totalGrade += grades[i] * weights[i] / PERCENTAGE_POINTS_IN_1;
        }
        
        return totalGrade;
    }
    
    
    /**
     * Returns an array that contains the grade item names.
     *
     * @param in scanner
     * @return array that contains the grade item names
     * @throws IllegalArgumentException if "in" is null
     */
    public static String[] getGradeItemNames(Scanner in) {
    
        // Parameter error checking - null scanner
        if (in == null) {
            throw new IllegalArgumentException("Null file");
        }
        
        String line = in.nextLine();
        Scanner lineScanner = new Scanner(line);
        lineScanner.useDelimiter(",");
        
        lineScanner.next();
        lineScanner.next();
        
        int numOfGradeItems = 0;
        
        while (lineScanner.hasNext()) {
            lineScanner.next();
            numOfGradeItems++;
        }
        
        String[] gradeItemNames = new String[numOfGradeItems];
        
        lineScanner = new Scanner(line);
        lineScanner.useDelimiter(",");
        
        lineScanner.next();
        lineScanner.next();
        
        for (int i = 0; i < numOfGradeItems; i++) {
            gradeItemNames[i] = lineScanner.next();
        }
        
        return gradeItemNames;
    }
    
    
    /**
     * Returns the number of lines in the file.
     * 
     * @param in scanner
     * @return number of lines in the file
     * @throws IllegalArgumentException if "in" is null
     */
    public static int getNumberOfLines(Scanner in) {
    
        // Parameter error checking - null scanner
        if (in == null) {
            throw new IllegalArgumentException("Null file");
        }
        
        int numLines = 0;
        
        while (in.hasNextLine()) {
            in.nextLine();
            numLines++;
        }
        
        return numLines;
    }
    
    
    /**
     * Returns a 2D array with the given number of rows and columns 
     * that contains the student grades.
     * 
     * @param in scanner
     * @param rows number of rows
     * @param columns number of columns
     * @return 2D array of student grades
     * @throws IllegalArgumentException if "in" is null
     * @throws IllegalArgumentException if rows < 1
     * @throws IllegalArgumentException if columns < 1
     */
    public static int[][] getAllGrades(Scanner in, int rows, int columns) {
    
        // Parameter error checking - null scanner
        if (in == null) {
            throw new IllegalArgumentException("Null file");
        }
        
        // Parameter error checking - invalid rows
        if (rows < 1) {
            throw new IllegalArgumentException("Invalid rows");
        }
        
        // Parameter error checking - invalid columns
        if (columns < 1) {
            throw new IllegalArgumentException("Invalid columns");
        }
        
        in.nextLine();
        in.nextLine();
        
        int[][] allGrades = new int[rows][columns];
        
        for (int i = 0; i < rows; i++) {
            String line = in.nextLine();
            Scanner lineScanner = new Scanner(line);
            lineScanner.useDelimiter(",");
            
            lineScanner.next();
            lineScanner.next();
            
            for (int j = 0; j < columns; j++) {
                allGrades[i][j] = lineScanner.nextInt();
            }
        }
        
        return allGrades;
    }
    
    
    /**
     * Returns the average of the values in the given column of the grades array.
     * 
     * @param grades array of student grades
     * @param column assignment type
     * @return average of the values in the given column
     * @throws IllegalArgumentException if column is < 0 or >= grades[0].length
     * @throws IllegalArgumentException if one of more rows do not have the same length as row 0
     */
    public static double computeAverage(int[][] grades, int column) {
    
        // Parameter error checking - invalid column
        if (column < 0 || column >= grades[0].length) {
            throw new IllegalArgumentException("Invalid column");
        }
        
        // Parameter error checking - jagged array
        for (int i = 0; i < grades.length; i++) {
            if (grades[i].length != grades[0].length) {
                throw new IllegalArgumentException("Jagged array");
            }
        }
        
        int sum = 0;
        int numGrades = 0;
        
        for (int i = 0; i < grades.length; i++) {
            sum += grades[i][column];
            numGrades++;
        }
        
        double avg = (double)sum / numGrades;
        return avg;
    }


    /**
     * Returns the minimum value in the given column of the grades array.
     * 
     * @param grades array of student grades
     * @param column assignment type
     * @return minimum value
     * @throws IllegalArgumentException if column is < 0 or >= grades[0].length
     * @throws IllegalArgumentException if one of more rows do not have the same length as row 0
     */
    public static int getMinimum(int[][] grades, int column) {
    
        // Parameter error checking - invalid column
        if (column < 0 || column >= grades[0].length) {
            throw new IllegalArgumentException("Invalid column");
        }
        
        // Parameter error checking - jagged array
        for (int i = 0; i < grades.length; i++) {
            if (grades[i].length != grades[0].length) {
                throw new IllegalArgumentException("Jagged array");
            }
        }
        
        int min = grades[0][column];
        
        for (int i = 0; i < grades.length; i++) {
            if (grades[i][column] < min) {
                min = grades[i][column];
            }
        }
        
        return min;
    }


    /**
     * Returns the maximum value in the given column of the grades array
     *
     * @param grades array of student grades
     * @param column assignment type
     * @return maximum value
     * @throws IllegalArgumentException if column is < 0 or >= grades[0].length
     * @throws IllegalArgumentException if one of more rows do not have the same length as row 0
     */
    public static int getMaximum(int[][] grades, int column) {
    
        // Parameter error checking - invalid column
        if (column < 0 || column >= grades[0].length) {
            throw new IllegalArgumentException("Invalid column");
        }
        
        // Parameter error checking - jagged array
        for (int i = 0; i < grades.length; i++) {
            if (grades[i].length != grades[0].length) {
                throw new IllegalArgumentException("Jagged array");
            }
        }
        
        int max = grades[0][column];
        
        for (int i = 0; i < grades.length; i++) {
            if (grades[i][column] > max) {
                max = grades[i][column];
            }
        }
        
        return max;
    }
}