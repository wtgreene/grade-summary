import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Program to test Grader methods
 *
 * @author Suzanne Balik
 * @author Will Greene
 */
public class GraderTest {

    /** tolerance used for comparing double values */
    public static final double DELTA = .00001;
    
    /** six weights */
    public static final int[] SIX_WEIGHTS = {10, 10, 10, 20, 20, 30};
    
    /** six grades */
    public static final int[] SIX_GRADES =  {90, 86, 92, 78, 100, 95};

    /** six grades for three students */
    public static final int[][] GRADES = {{ 90, 86, 92, 78, 100, 95},
                                          { 89, 78, 95, 88,  97, 76}, 
                                          {100, 90, 87, 92,  98, 80}};
    
    // Personal Class Constants
    
    /** four grades */
    public static final int[] FOUR_GRADES = {72, 93, 87, 88};
    
    /** four weights */
    public static final int[] FOUR_WEIGHTS = {20, 20, 28, 32};
    
    /** one grade */
    public static final int[] ONE_GRADE = {97};
    
    /** one weight */
    public static final int[] ONE_WEIGHT = {100};
    
    /** four grades for one student */
    public static final int[][] FOUR_GRADES_ONE_STUDENT = {{72, 93, 87, 88}};
        
 
    /**
     * Test computing weighted average with 6 grades and weights
     */
    @Test
    public void testComputeWeightedAverage1() {
        assertEquals(90.9, Grader.computeWeightedAverage(SIX_GRADES, SIX_WEIGHTS), 
                     DELTA, "Grader.computeWeightedAverage(SIX_GRADES, SIX_WEIGHTS)");
    }
    
    /**
     * Test computing weighted average with 4 grades and weights
     */
    @Test
    public void testComputeWeightedAverage2() {
        assertEquals(85.52, Grader.computeWeightedAverage(FOUR_GRADES, FOUR_WEIGHTS), 
                     DELTA, "Grader.computeWeightedAverage(FOUR_GRADES, FOUR_WEIGHTS)");
    }
    
    /**
     * Test computing weighted average with 1 grade and weight
     */
    @Test
    public void testComputeWeightedAverage3() {
        assertEquals(97.0, Grader.computeWeightedAverage(ONE_GRADE, ONE_WEIGHT), 
                     DELTA, "Grader.computeWeightedAverage(ONE_GRADE, ONE_WEIGHT)");
    }
    
    /**
     * Test computing average for column 0
     */
    @Test
    public void testComputeAverage1() {
        assertEquals(93.0, Grader.computeAverage(GRADES, 0), 
                     DELTA, "Grader.computeWeightedAverage(GRADES, 0)");
    }
    
    /**
     * Test computing average for column 3
     */
    @Test
    public void testComputeAverage2() {
        assertEquals(86.0, Grader.computeAverage(GRADES, 3), 
                     DELTA, "Grader.computeWeightedAverage(GRADES, 3)");
    }
    
    /**
     * Test computing average for column 2 (FOUR_GRADES_ONE_STUDENT array)
     */
    @Test
    public void testComputeAverage3() {
        assertEquals(87.0, Grader.computeAverage(FOUR_GRADES_ONE_STUDENT, 2), 
                     DELTA, "Grader.computeWeightedAverage(FOUR_GRADES_ONE_STUDENT, 2)");
    }
    
    /**
     * Test get minimum for column 0
     */
    @Test
    public void testGetMinimum1() {
        assertEquals(89, Grader.getMinimum(GRADES, 0), 
                     "Grader.getMinimum(GRADES, 0)");
    }
    
    /**
     * Test get minimum for column 4
     */
    @Test
    public void testGetMinimum2() {
        assertEquals(97, Grader.getMinimum(GRADES,4), 
                     "Grader.getMinimum(GRADES, 4)");
    }
    
    /**
     * Test get minimum for column 3 (FOUR_GRADES_ONE_STUDENT array)
     */
    @Test
    public void testGetMinimum3() {
        assertEquals(88, Grader.getMinimum(FOUR_GRADES_ONE_STUDENT, 3), 
                     "Grader.getMinimum(FOUR_GRADES_ONE_STUDENT, 3)");
    }
    
    /**
     * Test get maximum for column 0
     */
    @Test
    public void testGetMaximum1() {
        assertEquals(100, Grader.getMaximum(GRADES, 0), 
                     "Grader.getMaximum(GRADES, 0)");
    }
    
    /**
     * Test get maximum for column 1
     */
    @Test
    public void testGetMaximum2() {
        assertEquals(90, Grader.getMaximum(GRADES, 1), 
                     "Grader.getMaximum(GRADES, 1)");
    }
    
    /**
     * Test get maximum for column 0 (FOUR_GRADES_ONE_STUDENT array)
     */
    @Test
    public void testGetMaximum3() {
        assertEquals(72, Grader.getMaximum(FOUR_GRADES_ONE_STUDENT, 0), 
                     "Grader.getMaximum(FOUR_GRADES_ONE_STUDENT, 0)");
    }
    
    /**
     * Test the Grader methods with invalid values
     */
    @Test
    public void testInvalidMethods() {

        // Invalid test cases are provided for you below - You do NOT
        // need to add additional invalid tests. Just make sure these
        // pass!
        
        int[] grades1 = { 90, 89};
        int[] grades2 = {90, 60, 80};
        int[] weights1 = {40, 50, 10};
        int[] weights2 = {40, 40, 10};
        int[][] jagged = {{10, 20, 30},
                          {40, 50, 60},
                          {70, 80},
                          {90, 100, 110}};
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.computeWeightedAverage(grades1, weights1), 
            "Grader.computeWeightedAverage(grades1, weights1)");
        assertEquals("Different array lengths", exception.getMessage(),
            "Testing Grader.computeWeightedAverage(grades1, weights1) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.computeWeightedAverage(grades2, weights2), 
            "Grader.computeWeightedAverage(grades2, weights2)");
        assertEquals("Invalid weights", exception.getMessage(),
            "Testing Grader.computeWeightedAverage(grades2, weights2) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.computeAverage(GRADES, -1), 
            "Grader.computeAverage(GRADES, -1)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.computeAverage(GRADES, -1) - exception message");
       
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.computeAverage(GRADES, 6), 
            "Grader.computeAverage(GRADES, 6)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.computeAverage(GRADES, 6) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.computeAverage(jagged, -1), 
            "Grader.computeAverage(jagged, -1)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.computeAverage(jagged, -1) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.computeAverage(jagged, 0), 
            "Grader.computeAverage(jagged, 0)");
        assertEquals("Jagged array", exception.getMessage(),
                     "Testing Grader.computeAverage(jagged, 0) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMinimum(GRADES, -1), 
            "Grader.getMinimum(GRADES, -1)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.getMinimum(GRADES, -1) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMinimum(GRADES, 6), 
            "Grader.getMinimum(GRADES, 6)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.getMinimum(GRADES, 6) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMinimum(jagged, -1), 
            "Grader.getMinimum(jagged, -1)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.getMinimum(jagged, -1) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMinimum(jagged, 0), 
            "Grader.getMinimum(jagged, 0)");
        assertEquals("Jagged array", exception.getMessage(),
                     "Testing Grader.getMinimum(jagged, 0) - exception message");
   
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMaximum(GRADES, -1), 
            "Grader.getMaximum(GRADES, -1)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.getMaximum(GRADES, -1) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMaximum(GRADES, 6), 
            "Grader.getMaximum(GRADES, 6)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.getMaximum(GRADES, 6) - exception message");

        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMaximum(jagged, -1), 
            "Grader.getMaximum(jagged, -1)");
        assertEquals("Invalid column", exception.getMessage(),
                     "Testing Grader.getMaximum(jagged, -1) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getMaximum(jagged, 0), 
            "Grader.getMaximum(jagged, 0)");
        assertEquals("Jagged array", exception.getMessage(),
            "Testing Grader.getMaximum(jagged, 0) - exception message");                     
    }
    
    /**
     * Tests getWeights 
     */
    @Test
    public void testGetWeights() {
        
        // You do NOT need to add additional getWeights tests. Just make sure these pass!
        
        int[] weights = {10, 10, 10, 20, 20, 30};
        String simulatedFile = "ID,Name,P1,P2,P3,T1,T2,FE\n" +
                               " , ,10,10,10,20,20,30\n" +
                               "1289,Alice Chang,90,86,92,78,100,95\n" +
                               "9087,Robert Jones,89,78,95,88,97,76\n" +
                               "8789,Jack Patel,100,90,87,92,98,80\n";
        assertArrayEquals(weights, 
                          Grader.getWeights(new Scanner(simulatedFile)), 
                          "Grader.getWeights(new Scanner(simulatedFile)");
        
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getWeights(null), 
            "Grader.getWeights(null)");
        assertEquals("Null file", exception.getMessage(),
                     "Testing Grader.getWeights(null) - exception message");
    }
    
    /**
     * Tests getGradeItemNames
     */
    @Test
    public void testGetGradeItemNames() {
        
        // You do NOT need to add additional getGradeItemNames tests. Just make sure these pass!
        
        String[] itemNames = {"P1","P2","P3","T1","T2","FE"};
        String simulatedFile = "ID,Name,P1,P2,P3,T1,T2,FE\n" +
                               " , ,10,10,10,20,20,30\n" +
                               "1289,Alice Chang,90,86,92,78,100,95\n" +
                               "9087,Robert Jones,89,78,95,88,97,76\n" +
                               "8789,Jack Patel,100,90,87,92,98,80\n";
        assertArrayEquals(itemNames, 
                          Grader.getGradeItemNames(new Scanner(simulatedFile)), 
                          "Grader.getGradeItemNames(new Scanner(simulatedFile)");
        
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getGradeItemNames(null), 
            "Grader.getGradeItemNames(null)");
        assertEquals("Null file", exception.getMessage(),
                     "Testing Grader.getGradeItemNames(null) - exception message");
    }    
    
    /**
     * Tests getNumberOfLines
     */
    @Test
    public void testGetNumberOfLines() {
        
        // You do NOT need to add additional getNumberOfLines tests. Just make sure these pass!
        
        String simulatedFile = "ID,Name,P1,P2,P3,T1,T2,FE\n" +
                               " , ,10,10,10,20,20,30\n" +
                               "1289,Alice Chang,90,86,92,78,100,95\n" +
                               "9087,Robert Jones,89,78,95,88,97,76\n" +
                               "8789,Jack Patel,100,90,87,92,98,80\n";
        assertEquals(5, Grader.getNumberOfLines(new Scanner(simulatedFile)), 
                     "Grader.getNumberOfLines(new Scanner(simulatedFile)");
        
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getNumberOfLines(null), 
            "Grader.getNumberOfLines(null)");
        assertEquals("Null file", exception.getMessage(),
                     "Testing Grader.getNumberOfLines(null) - exception message");
    }
    
    /**
     * Tests getAllGrades
     */
    @Test
    public void testGetAllGrades() {
        
        // You do NOT need to add additional getAllGrades tests. Just make sure these pass!
        
        int[][] grades = {{90,86,92,78,100,95},
                          {89,78,95,88,97,76},
                          {100,90,87,92,98,80}};
        
        String simulatedFile = "ID,Name,P1,P2,P3,T1,T2,FE\n" +
                               " , ,10,10,10,20,20,30\n" +
                               "1289,Alice Chang,90,86,92,78,100,95\n" +
                               "9087,Robert Jones,89,78,95,88,97,76\n" +
                               "8789,Jack Patel,100,90,87,92,98,80\n";
        
        assertArrayEquals(grades, Grader.getAllGrades(new Scanner(simulatedFile), 3, 6 ), 
                     "Grader.getAllGrades(3, 6, new Scanner(simulatedFile)");
        
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getAllGrades(null, 2, 3), 
            "Grader.getAllGrades(null, 2, 3)");
        assertEquals("Null file", exception.getMessage(),
                     "Testing Grader.getAllGrades(null, 2, 3) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getAllGrades(new Scanner(simulatedFile), 0, 3), 
            "Grader.getAllGrades(new Scanner(simulatedFile), 0, 3)");
        assertEquals("Invalid rows", exception.getMessage(),
            "Testing Grader.getAllGrades(new Scanner(simulatedFile), 0, 3) - exception message");
                     
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.getAllGrades(new Scanner(simulatedFile), 3, -1), 
            "Grader.getAllGrades(new Scanner(simulatedFile), 3, -1)");
        assertEquals("Invalid columns", exception.getMessage(),
            "Testing Grader.getAllGrades(new Scanner(simulatedFile), 3, -1) - exception message");
    }
    
    /**
     * Tests outputOverallGrades
     * @throws FileNotFoundException if unable to construct PrintWriter
     */
    @Test
    public void testOutputOverallGrades() throws FileNotFoundException {
        
        // You do NOT need to add additional outputOverallGrades tests. Just make sure these pass!
        
        String inputFile = "test-files/CSC101-003.txt";
        String expected = "test-files/exp_CSC101-003-Grades.txt";
        String actual = "test-files/act_unit_CSC101-003-Grades.txt";
        // Delete file if it already exists
        Path path = Path.of(actual);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // Nothing needs to be done
            e.printStackTrace();
        }
        PrintWriter out = new PrintWriter(new FileOutputStream(actual));
        Grader.outputOverallGrades(inputFile, out); 
        out.close();
        assertFilesEqual(expected, actual, "CSC101-003");
        
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.outputOverallGrades(inputFile, null), 
            "null output");
        assertEquals("Null PrintWriter", exception.getMessage(),
                     "null output - exception message");
        
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.outputOverallGrades("test-files/does-not-exist.txt", out), 
            "Input file does not exist");
        assertEquals("Unable to access input file: test-files/does-not-exist.txt", 
                     exception.getMessage(),
                     "Input file does not exist - exception message");
    }
    
    /**
     * Tests outputGradeSummary
     * @throws FileNotFoundException if unable to construct PrintWriter
     */
    @Test
    public void testOutputGradeSummary() throws FileNotFoundException {
        
        // You do NOT need to add additional outputGradeSummary tests. Just make sure these pass!
        
        String inputFile = "test-files/CSC101-003.txt";
        String expected = "test-files/exp_CSC101-003-Summary.txt";
        String actual = "test-files/act_unit_CSC101-003-Summary.txt";
        // Delete file if it already exists
        Path path = Path.of(actual);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            // Nothing needs to be done
            e.printStackTrace();
        }
        PrintWriter out = new PrintWriter(new FileOutputStream(actual));
        Grader.outputGradeSummary(inputFile, out); 
        out.close();
        assertFilesEqual(expected, actual, "CSC101-003");
        
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.outputGradeSummary(inputFile, null), 
            "null output");
        assertEquals("Null PrintWriter", exception.getMessage(),
                     "null output - exception message");
        
        exception = assertThrows(IllegalArgumentException.class,
            () -> Grader.outputGradeSummary("test-files/does-not-exist.txt", out), 
            "Input file does not exist");
        assertEquals("Unable to access input file: test-files/does-not-exist.txt", 
                     exception.getMessage(),
                     "Input file does not exist - exception message");
    }
    
    /**
     * Tests whether files contain the same contents
     * 
     * @param pathToExpected path to file with expected contents
     * @param pathToActual path to file with actual content
     * @param message message for test
     * @throws FileNotFoundException if Scanner cannot be constructed with file
     */
    private void assertFilesEqual(String pathToExpected, String pathToActual, String message)
            throws FileNotFoundException {
        try (Scanner expected = new Scanner(new FileInputStream(pathToExpected));
                Scanner actual = new Scanner(new FileInputStream(pathToActual));) {
            while (expected.hasNextLine()) {
                if (!actual.hasNextLine()) { // checks that actual has line as well
                    fail(message + ": Actual missing line(s)");
                } else { // both have another line
                    assertEquals(expected.nextLine(), actual.nextLine(),
                            message + ": Checking line equality");
                }
            }

            if (actual.hasNextLine()) {
                fail(message + ": Actual has extra line(s)");
            }
        }
    }

}