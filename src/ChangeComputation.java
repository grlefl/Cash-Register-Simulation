// util
import java.util.TreeMap;
import java.util.Comparator;
import java.util.Scanner;
// math
import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * This class simulates a cash register with the capability to process USD in a foreign country that uses Euros.
 * It receives the amount of each USD involved in purchasing an item.
 * It then calculates the correct amount of change in Euros and displays the amount of each Euro currency to return.
 * BigDecimal is used extensively to maintain precision while working with currency .
 * The executable file is ChangeComputation test.
 * @see ChangeComputationTest
 * JUnit testing is also provided.
 * @see ChangeComputationJUnit
 *
 * @version 1.0
 * @since 09/29/23
 */
public class ChangeComputation {

    /**
     * The exchange rate for converting USD to Euros.
     * As of 09/29/23, the exchange rate is 0.9446.
     */
    private static final BigDecimal exchangeRate = new BigDecimal("0.9446");

    /**
     * An array representing various USD currencies.
     * Formatted to accommodate BigDecimal.
     */
    private static final String[] usdCurrency = {"20.00", "10.00","5.00","1.00","0.25","0.10","0.05","0.01"};

    /**
     * An array representing various Euro currencies.
     * Formatted to accommodate BigDecimal.
     */
    private static final String[] euroCurrency = {"20.00","10.00","5.00","1.00","0.50","0.20","0.10","0.05","0.01"};

    /**
     * A descending comparator used for sorting currency values in descending order.
     */
    private static final Comparator<BigDecimal> descendingComparator = Comparator.reverseOrder();



    /**
     * Constructs a ChangeComputation object where the cash register simulation takes place.
     * If the user does not put in enough money the first time, the simulation will keep repeating until the balance is paid.
     * Invalid user input has not been accounted for.
     */
    public ChangeComputation() {

        // TreeMap for storing the amount of USD currencies given to the cash register
        TreeMap<BigDecimal, Integer> itemizedUSD = new TreeMap<>(descendingComparator);
        for (String currency : usdCurrency) {
            itemizedUSD.put(new BigDecimal(currency),0);
        }

        // TreeMap for storing amount of Euro currencies to be returned in change
        TreeMap<BigDecimal, Integer> itemizedEuros = new TreeMap<>(descendingComparator);
        for (String currency : euroCurrency) {
            itemizedEuros.put(new BigDecimal(currency),0);
        }

        // initialize scanner object
        Scanner scanner = new Scanner(System.in);

        // user inputs price of item in Euros
        System.out.print("Price of item in Euros: ");
        BigDecimal balanceInEuros = scanner.nextBigDecimal(); // will throw InputMismatchException if not a valid BigDecimal
        BigDecimal balanceInUSD = eurosToUSD(balanceInEuros);

        // loop user input until they put in enough money
        do {
            System.out.println("\nYour balance is $" + balanceInUSD.subtract(paidInUSD(itemizedUSD)) + "."); // display current balance
            moneyInput(scanner, itemizedUSD);
        } while(paidInUSD(itemizedUSD).compareTo(balanceInUSD) < 0);

        // calculate and display total amount paid in USD
        BigDecimal paidInUSD = paidInUSD(itemizedUSD);
        System.out.println("\nYou paid a total of $" + paidInUSD + ".");

        // calculate and display total change in Euros
        BigDecimal changeInEuros = changeInEuros(balanceInEuros,paidInUSD);
        System.out.println("\nYour total change in Euros is € " + changeInEuros + ":");

        // calculate and display amount of each Euro currency in change
        moneyOutput(itemizedEuros, changeInEuros);

        // close scanner
        scanner.close();
    }


    /**
     * Retrieves the array of USD currencies.
     * @return The array of USD currencies.
     */
    public static String[] getUsdCurrency() {
        return usdCurrency;
    }

    /**
     * Retrieves the array of Euro currencies.
     * @return The array of Euro currencies.
     */
    public static String[] getEuroCurrency() {
        return euroCurrency;
    }

    /**
     * Retrieves the comparator to sort currencies in descending order.
     * @return The comparator to sort currencies in descending order.
     */
    public static Comparator<BigDecimal> getDescendingComparator() {
        return descendingComparator;
    }


    /**
     * Converts USD currency to Euro currency.
     * @param usd Value of USD currency.
     * @return Value of USD currency in Euros. Rounded to 2 decimal places.
     */
    public static BigDecimal usdToEuros(BigDecimal usd) {
        return usd.multiply(exchangeRate).setScale(2,RoundingMode.HALF_UP);
    }

    /**
     * Converts Euro currency to USD currency.
     * @param euros Value of Euro currency.
     * @return Value of Euro currency in USD. Rounded to 2 decimal places.
     */
    public static BigDecimal eurosToUSD(BigDecimal euros) {
        return euros.multiply(BigDecimal.ONE.divide(exchangeRate,10,RoundingMode.HALF_UP)).setScale(2,RoundingMode.HALF_UP);
    }

    /**
     * Prompts the user to enter the amount of each USD currency paid to the cash register.
     * Dynamically tracks and updates this information in Treemap itemizedUSD.
     * @param scanner Scanner object to keep track of user input.
     * @param itemizedUSD TreeMap to store USD currencies and their respective amounts.
     * @return An array of each USD currency amount for easy JUnit testing.
     */
    public static Integer[] moneyInput(Scanner scanner, TreeMap<BigDecimal,Integer> itemizedUSD) {
        int userInput = 0;
        for (BigDecimal currency : itemizedUSD.keySet()) {
            if (currency.compareTo(BigDecimal.ONE) >= 0) {
                System.out.print("Enter the number of $" + String.format("%.0f", currency) + " bills given: ");
            } else if (currency.compareTo(BigDecimal.ONE) < 0) {
                System.out.print("Enter the number of ¢" + String.format("%.0f", currency.multiply(new BigDecimal("100"))) + " coins given: ");
            }
            userInput = scanner.nextInt();
            if (userInput > 0) {
                itemizedUSD.put(currency,userInput);
            }
        }
        return itemizedUSD.values().toArray(Integer[]::new);
    }

    /**
     * Sums up all the paid USD currency.
     * @param itemizedUSD TreeMap to store USD currencies and their respective amounts.
     * @return Value of paid USD currency. Rounded to 2 decimal places.
     */
    public static BigDecimal paidInUSD(TreeMap<BigDecimal,Integer> itemizedUSD) {
        BigDecimal totalPaidInUSD = new BigDecimal("0");
        for (BigDecimal currency : itemizedUSD.keySet()) {
            totalPaidInUSD = totalPaidInUSD.add(currency.multiply(BigDecimal.valueOf(itemizedUSD.get(currency))));
        }
        return totalPaidInUSD;
    }

    /**
     * Calculates the change in Euros to return to the user.
     * @param balanceInEuros The original price of the item in Euros.
     * @param paidInUSD Value of total amount paid in USD.
     * @return Value of the change in Euros.
     */
    public static BigDecimal changeInEuros(BigDecimal balanceInEuros, BigDecimal paidInUSD) {
        return usdToEuros(paidInUSD).subtract(balanceInEuros);
    }

    /**
     * This method gives an itemized list of the amount of each Euro currency to return in change to the user.
     * @param itemizedEuros Treemap to store Euro currencies and their respective amounts.
     * @param changeInEuros Value of the change in Euros.
     * @return An array of each Euro currency amount for easy JUnit testing.
     */
    public static Integer[] moneyOutput(TreeMap<BigDecimal,Integer> itemizedEuros, BigDecimal changeInEuros) {
        int multiplier;

        for (BigDecimal currency : itemizedEuros.keySet()) {
            multiplier = changeInEuros.divide(currency,0,RoundingMode.DOWN).intValue(); // divide total change by the current currency

            if (multiplier > 0) {
                itemizedEuros.put(currency,multiplier); // add multiplier if greater than 0, that means the amount of currency return

                // print statement for the amount of each currency being returned
                if (currency.compareTo(BigDecimal.ONE) >= 0) {
                    System.out.println(String.format("%.0f", currency) + "€ bills: " + multiplier);
                } else if (currency.compareTo(BigDecimal.ONE) < 0) {
                    System.out.println(String.format("%.0f", currency.multiply(new BigDecimal("100"))) + " Euro coins: " + multiplier);
                }

                // updating the total change to take away the currency that's already accounted for
                changeInEuros = changeInEuros.subtract(currency.multiply(BigDecimal.valueOf(multiplier)));
            }
        }
        return itemizedEuros.values().toArray(Integer[]::new);
    }
}
