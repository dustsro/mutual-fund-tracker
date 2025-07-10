/**
 * @author Dustin Do
 * CIS 36B
 */

import java.text.DecimalFormat;

public class MutualFund {
    private final String FUND_NAME;
    private final String TICKER;
    private double pricePerShare;
    private double tradingFee;

    /** CONSTRUCTORS */

    /**
     * Constructs a MutualFund with all required attributes.
     *
     * @param fundName      The name of the mutual fund.
     * @param ticker        The ticker symbol for the fund.
     * @param pricePerShare The current price per share.
     * @param tradingFee    The trading fee as a percentage (e.g., 0.5 means 0.5%).
     */
    public MutualFund(String fundName, String ticker, double pricePerShare, double tradingFee) {
        this.FUND_NAME = fundName;
        this.TICKER = ticker;
        this.pricePerShare = pricePerShare;
        this.tradingFee = tradingFee;
    }

    /** ACCESSORS */

    /**
     * @return The name of the fund.
     */
    public String getFundName() {
        return FUND_NAME;
    }

    /**
     * @return The ticker symbol of the fund.
     */
    public String getTicker() {
        return TICKER;
    }

    /**
     * @return The current price per share.
     */
    public double getPricePerShare() {
        return pricePerShare;
    }

    /**
     * @return The trading fee percentage.
     */
    public double getTradingFee() {
        return tradingFee;
    }

    /** MUTATORS */

    /**
     * Updates the price per share.
     *
     * @param pricePerShare The new price per share.
     */
    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    /**
     * Updates the trading fee percentage.
     *
     * @param tradingFee The new trading fee.
     */
    public void setTradingFee(double tradingFee) {
        this.tradingFee = tradingFee;
    }

    /** ADDITIONAL OPERATIONS */

    /**
     * Returns a formatted string representation of the mutual fund.
     *
     * @return A formatted string with fund details.
     */
    @Override
    public String toString() {
        DecimalFormat perShareFormat = new DecimalFormat("$0.00");
        return FUND_NAME + "\n" + TICKER + "\nShare Price: " + perShareFormat.format(pricePerShare)
                + "\nTrading Fee: " + tradingFee + "%";
    }
}
