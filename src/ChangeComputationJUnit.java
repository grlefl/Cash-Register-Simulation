// junit
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
// util
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Scanner;


/**
 * This class contains JUnit test methods for testing the functionality of class ChangeComputation.
 * @see ChangeComputation
 */
public class ChangeComputationJUnit {

    /**
     * An array representing various USD currencies.
     * Formatted to accommodate BigDecimal.
     */
    private static final String[] usdCurrency = ChangeComputation.getUsdCurrency();

    /**
     * An array representing various Euro currencies.
     * Formatted to accommodate BigDecimal.
     */
    private static final String[] euroCurrency = ChangeComputation.getEuroCurrency();

    /**
     * A descending comparator used for sorting currency values in descending order.
     */
    private static final Comparator<BigDecimal> descendingComparator = ChangeComputation.getDescendingComparator();


    /**
     * Test cases for the usdToEuros method of the ChangeComputation class.
     */
    @Test
    void testUsdToEuros() {
        BigDecimal expected1 = new BigDecimal("42.51");
        BigDecimal actual1 = ChangeComputation.usdToEuros(new BigDecimal("45"));
        assertEquals(expected1.toString(), actual1.toString());

        BigDecimal expected2 = new BigDecimal("29.07");
        BigDecimal actual2 = ChangeComputation.usdToEuros(new BigDecimal("30.78"));
        assertEquals(expected2.toString(), actual2.toString());

        BigDecimal expected3 = new BigDecimal("880.37");
        BigDecimal actual3 = ChangeComputation.usdToEuros(new BigDecimal("932"));
        assertEquals(expected3.toString(), actual3.toString());
    }


    /**
     * Test cases for the eurosToUSD method of the ChangeComputation class.
     */
    @Test
    void testEurosToUsd() {
        BigDecimal expected1 = new BigDecimal("31.76");
        BigDecimal actual1 = ChangeComputation.eurosToUSD(new BigDecimal("30"));
        assertEquals(expected1.toString(),actual1.toString());

        BigDecimal expected2 = new BigDecimal("3.05");
        BigDecimal actual2 = ChangeComputation.eurosToUSD(new BigDecimal("2.88"));
        assertEquals(expected2.toString(),actual2.toString());

        BigDecimal expected3 = new BigDecimal("8.86");
        BigDecimal actual3 = ChangeComputation.eurosToUSD(new BigDecimal("8.37"));
        assertEquals(expected3.toString(),actual3.toString());
    }


    /**
     * Test cases for the moneyInput method of the ChangeComputation class.
     */
    @Test
    void testMoneyInput() {
        TreeMap<BigDecimal, Integer> test1 = new TreeMap<>(descendingComparator);
        for (String currency : usdCurrency) {
            test1.put(new BigDecimal(currency),0);
        }
        String mockInput1 = "4\n0\n2\n1\n0\n9\n100\n5\n";
        Scanner mockScanner1 = new Scanner(mockInput1);
        Integer[] expected1 = {4,0,2,1,0,9,100,5};
        Integer[] actual1 = ChangeComputation.moneyInput(mockScanner1,test1);
        assertArrayEquals(expected1,actual1);
        mockScanner1.close();


        TreeMap<BigDecimal, Integer> test2 = new TreeMap<>(descendingComparator);
        for (String currency : usdCurrency) {
            test2.put(new BigDecimal(currency),0);
        }
        String mockInput2 = "0\n0\n45\n3\n0\n9\n1\n5\n";
        Scanner mockScanner2 = new Scanner(mockInput2);
        Integer[] expected2 = {0,0,45,3,0,9,1,5};
        Integer[] actual2 = ChangeComputation.moneyInput(mockScanner2,test2);
        assertArrayEquals(expected2,actual2);
        mockScanner2.close();


        TreeMap<BigDecimal, Integer> test3 = new TreeMap<>(descendingComparator);
        for (String currency : usdCurrency) {
            test3.put(new BigDecimal(currency),0);
        }
        String mockInput3 = "0\n0\n0\n0\n0\n0\n0\n0\n";
        Scanner mockScanner3 = new Scanner(mockInput3);
        Integer[] expected3 = {0,0,0,0,0,0,0,0};
        Integer[] actual3 = ChangeComputation.moneyInput(mockScanner3,test3);
        assertArrayEquals(expected3,actual3);
    }


    /**
     * Test cases for the paidInUSD method of the ChangeComputation class.
     */
    @Test
    void testPaidInUSD() {
        TreeMap<BigDecimal, Integer> test1 = new TreeMap<>(descendingComparator);
        test1.put(new BigDecimal("0.05"),1463);
        BigDecimal expected1 = new BigDecimal("73.15");
        BigDecimal actual1 = ChangeComputation.paidInUSD(test1);
        assertEquals(expected1,actual1);

        TreeMap<BigDecimal, Integer> test2 = new TreeMap<>(descendingComparator);
        test2.put(new BigDecimal("20.00"),32);
        test2.put(new BigDecimal("5.00"),43);
        test2.put(new BigDecimal("1.00"),756);
        test2.put(new BigDecimal("0.10"),98);
        test2.put(new BigDecimal("0.01"),2);
        BigDecimal expected2 = new BigDecimal("1620.82");
        BigDecimal actual2 = ChangeComputation.paidInUSD(test2);
        assertEquals(expected2,actual2);
    }


    /**
     * Test cases for the changeInEuros method of the ChangeComputation class.
     */
    @Test
    void testChangeInEuros() {
        BigDecimal expected1 = new BigDecimal("17.17");
        BigDecimal actual1 = ChangeComputation.changeInEuros(new BigDecimal("4.56"),new BigDecimal("23.00"));
        assertEquals(expected1,actual1);

        BigDecimal expected2 = new BigDecimal("79.69");
        BigDecimal actual2 = ChangeComputation.changeInEuros(new BigDecimal("456"),new BigDecimal("567.11"));
        assertEquals(expected2,actual2);
    }


    /**
     * Test cases for the moneyOutput method of the ChangeComputation class.
     */
    @Test
    void testMoneyOutput() {
        TreeMap<BigDecimal, Integer> test1 = new TreeMap<>(descendingComparator);
        for (String currency : euroCurrency) {
            test1.put(new BigDecimal(currency),0);
        }
        Integer[] expected1 = {4,1,0,0,0,0,0,0,0};
        Integer[] actual1 = ChangeComputation.moneyOutput(test1,new BigDecimal("90.00"));
        assertArrayEquals(expected1,actual1);


        TreeMap<BigDecimal, Integer> test2 = new TreeMap<>(descendingComparator);
        for (String currency : euroCurrency) {
            test2.put(new BigDecimal(currency),0);
        }
        Integer[] expected2 = {0,0,0,2,0,1,1,0,2};
        Integer[] actual2 = ChangeComputation.moneyOutput(test2,new BigDecimal("2.32"));
        assertArrayEquals(expected2,actual2);


        TreeMap<BigDecimal, Integer> test3 = new TreeMap<>(descendingComparator);
        for (String currency : euroCurrency) {
            test3.put(new BigDecimal(currency),0);
        }
        Integer[] expected3 = {9696,0,0,0,0,1,1,0,0};
        Integer[] actual3 = ChangeComputation.moneyOutput(test3,new BigDecimal("193920.30"));
        assertArrayEquals(expected3,actual3);


        TreeMap<BigDecimal, Integer> test4 = new TreeMap<>(descendingComparator);
        for (String currency : euroCurrency) {
            test4.put(new BigDecimal(currency),0);
        }
        Integer[] expected4 = {0,0,0,0,0,0,0,0,1};
        Integer[] actual4 = ChangeComputation.moneyOutput(test4,new BigDecimal("0.01"));
        assertArrayEquals(expected4,actual4);
    }
}