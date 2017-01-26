/*
 * PrimeNumbersTest.java
 * JUnit based test
 *
 * Created on August 25, 2005, 12:28 PM
 */

package primenumbers;

import junit.framework.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 *
 * @author gs145266
 */
public class PrimeNumbersTest extends TestCase {
    
    public PrimeNumbersTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static class PrimeCalculatorTest extends TestCase {

        public PrimeCalculatorTest(java.lang.String testName) {

            super(testName);
        }

        protected void setUp() throws Exception {
        }

        protected void tearDown() throws Exception {
        }

        public static Test suite() {
            TestSuite suite = new TestSuite(PrimeCalculatorTest.class);
            
            return suite;
        }

        /**
         * Test of construct method, of class primenumbers.PrimeNumbers.PrimeCalculator.
         */
        public void testConstruct() {
            System.out.println("testConstruct");
            
            // TODO add your test code below by replacing the default call to fail.
            fail("The test case is empty.");
        }

        /**
         * Test of finished method, of class primenumbers.PrimeNumbers.PrimeCalculator.
         */
        public void testFinished() {
            System.out.println("testFinished");
            
            // TODO add your test code below by replacing the default call to fail.
            fail("The test case is empty.");
        }
    }


    public static Test suite() {
        TestSuite suite = new TestSuite(PrimeNumbersTest.class);
        
        return suite;
    }

    /**
     * Test of actionPerformed method, of class primenumbers.PrimeNumbers.
     */
    public void testActionPerformed() {
        System.out.println("testActionPerformed");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of main method, of class primenumbers.PrimeNumbers.
     */
    public void testMain() {
        System.out.println("testMain");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getTimer method, of class primenumbers.PrimeNumbers.
     */
    public void testGetTimer() {
        System.out.println("testGetTimer");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setTimer method, of class primenumbers.PrimeNumbers.
     */
    public void testSetTimer() {
        System.out.println("testSetTimer");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getResultColor method, of class primenumbers.PrimeNumbers.
     */
    public void testGetResultColor() {
        System.out.println("testGetResultColor");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of setResultColor method, of class primenumbers.PrimeNumbers.
     */
    public void testSetResultColor() {
        System.out.println("testSetResultColor");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }
    
}
