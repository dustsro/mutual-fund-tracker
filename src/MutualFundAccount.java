/**
 * @author Dustin Do
 * CIS 36B
 */

import java.text.DecimalFormat;

public class MutualFundAccount {
    private double numShares;
    private MutualFund mf;
    private static final int SEED = 100000000;
    private static int accountSeed = SEED;

    /** CONSTRUCTORS */

    /**
     * Constructs a MutualFundAccount with a given mutual fund and zero shares.
     * @param mf The mutual fund to associate with this account.
     */
    public MutualFundAccount(MutualFund mf) {
        this.mf = mf;
    }

    /**
     * Constructs a MutualFundAccount with a specific number of shares and mutual fund.
     * @param numShares The number of shares held.
     * @param mf The mutual fund associated with the account.
     */
    public MutualFundAccount(double numShares, MutualFund mf) {
        this.numShares = numShares;
        this.mf = mf;
    }

    /** ACCESSORS */

    /**
     * @return The number of shares held in the account.
     */
    public double getNumShares() {
        return numShares;
    }

    /**
     * @return The MutualFund associated with the account.
     */
    public MutualFund getMf() {
        return mf;
    }

    /**
     * Generates and returns a unique account seed value.
     * @return The incremented account seed value.
     */
    public static int getAccountSeed() {
        accountSeed++;
        return accountSeed;
    }

    /** MUTATORS */

    /**
     * Updates the number of shares by adding the specified amount.
     * @param numShares The number of shares to add (can be negative to subtract).
     */
    public void updateShares(double numShares) {
        this.numShares += numShares;
    }

    /** ADDITIONAL OPERATIONS */

    /**
     * @return A formatted string showing the total number of shares.
     */
    @Override
    public String toString() {
        DecimalFormat shares = new DecimalFormat("0.0");
        return "Total Shares: " + shares.format(numShares);
    }
}
