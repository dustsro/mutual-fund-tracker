/**
 * @author Dustin Do
 * CIS 36B
 */

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String accountNum;
    private double cash;
    private ArrayList<MutualFundAccount> funds;

    /** CONSTRUCTOR */
    /**
     * Constructs a Customer with provided personal and financial information.
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param email The customer's email address.
     * @param password The customer's password.
     * @param cash The initial cash balance.
     * @param funds The list of MutualFundAccounts the customer owns.
     */
    public Customer(String firstName, String lastName, String email, String password, double cash,
                    ArrayList<MutualFundAccount> funds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.cash = cash;
        this.funds = new ArrayList<>(funds);
        this.accountNum = " " + MutualFundAccount.getAccountSeed();
    }

    /** ACCESSORS */
    /**
     * @return The customer's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return The customer's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return The customer's account number.
     */
    public String getAccountNum() {
        return accountNum;
    }

    /**
     * @return The customer's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Checks if the provided password matches the customer's password.
     * @param anotherPassword The password to check.
     * @return True if the passwords match; false otherwise.
     */
    public boolean passwordMatch(String anotherPassword) {
        return anotherPassword.equals(password);
    }

    /**
     * Gets the MutualFund at a given index.
     * @param fund The index of the fund.
     * @return The MutualFund object at that index.
     */
    public MutualFund getFund(int fund) {
        return this.funds.get(fund).getMf();
    }

    /**
     * @return The amount of cash in the account.
     */
    public double getCash() {
        return cash;
    }

    /**
     * @return The list of MutualFundAccounts.
     */
    public ArrayList<MutualFundAccount> getFunds() {
        return funds;
    }

    /** MUTATORS */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Updates the cash balance by adding the specified amount.
     * @param cash The amount to add (can be negative to subtract).
     */
    public void updateCash(double cash) {
        this.cash += cash;
    }

    /**
     * Attempts to buy a mutual fund using the provided amount of shares.
     * @param shares The number of shares to buy.
     * @param mf The MutualFund to buy.
     * @return True if the purchase is successful; false otherwise.
     */
    public boolean addFund(double shares, MutualFund mf) {
        final double HUNDRED = 100.0;
        double baseCost = shares * mf.getPricePerShare();
        double feeAmount = baseCost * mf.getTradingFee() / HUNDRED;
        double totalCost = baseCost + feeAmount;

        if (cash < baseCost) {
            return false;
        }

        cash -= baseCost;
        return true;
    }

    /**
     * Sells the mutual fund at the given index and adds the proceeds to cash.
     * @param index The index of the fund to sell.
     */
    public void sellFund(int index) {
        if (index < 0 || index >= funds.size()) {
            return;
        }

        final double HUNDRED = 100.0;
        MutualFundAccount account = funds.get(index);
        MutualFund mf = account.getMf();
        double shares = account.getNumShares();
        double pricePerShare = mf.getPricePerShare();
        double tradingFee = mf.getTradingFee();

        double proceeds = shares * pricePerShare * (1 - tradingFee / HUNDRED);
        cash += proceeds;
        funds.remove(index);
    }

    /** ADDITIONAL OPERATIONS */
    /**
     * @return A formatted string of the customer's full account summary.
     */
    @Override
    public String toString() {
        DecimalFormat cashFormat = new DecimalFormat("$###,###,###.00");
        String info = "Name: " + firstName + " " + lastName
                + "\nEmail: " + email + "\nAccount Number: " + accountNum
                + "\nTotal Cash: " + cashFormat.format(cash) + "\n\nCurrent Funds:\n";

        for (int i = 0; i < funds.size(); i++) {
            MutualFundAccount account = funds.get(i);
            info += account.getMf().toString() + "\n";
            info += account.toString() + "\n";
            info += "Account Number: " + accountNum + "-" + (i + 1) + "\n\n";
        }

        return info;
    }
}
