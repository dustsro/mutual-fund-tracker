/**
 * @author Dustin Do
 * CIS 36B
 */

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerInterface {
    public static void main(String[] args) throws IOException {
        DecimalFormat cashFormat = new DecimalFormat("$###,###,###.00");
        CustomerInterface ci = new CustomerInterface();
        Customer custom;
        String first, last, email, password, mutualName, ticker;
        double cash, sharePrice, numShares, fee;

        ArrayList<MutualFund> funds = new ArrayList<MutualFund>();
        ArrayList<Customer> customers = new ArrayList<Customer>();

        File file1 = new File("mutual_funds.txt");
        File file2 = new File("customers.txt");

        Scanner mutualScan = new Scanner(file1);

        while(mutualScan.hasNextLine()){
            mutualName = mutualScan.nextLine();
            ticker = mutualScan.next();
            sharePrice = mutualScan.nextDouble();
            fee = mutualScan.nextDouble();
            mutualScan.nextLine();
            funds.add(new MutualFund(mutualName, ticker, sharePrice, fee));
        }

        mutualScan.close();

        Scanner customerScan = new Scanner(file2);

        while(customerScan.hasNextLine()){
            first = customerScan.next();
            last = customerScan.next();
            email = customerScan.next();
            password = customerScan.next();
            cash = customerScan.nextDouble();
            int numFunds = customerScan.nextInt();

            ArrayList<MutualFundAccount> mfAccounts = new ArrayList<>();

            for (int i = 0; i < numFunds; i++) {
                ticker = customerScan.next();
                double shares = customerScan.nextDouble() *1.0;

                int fundIndex = binarySearch(funds, ticker);

                if (fundIndex >= 0) {
                    MutualFund mf = funds.get(fundIndex);
                    mfAccounts.add(new MutualFundAccount(shares, mf));
                } else {
                    System.out.println("Ticker " + ticker + " not found in mutual funds list.");
                }
            }

            customers.add(new Customer(first, last, email , password, cash, mfAccounts));

        }

        customerScan.close();

        Scanner input = new Scanner(System.in);

        System.out.print("Welcome to Mutual Fund InvestorTrack!\n\nEnter your email address: ");

        email = input.next();

        System.out.println("Enter your password: ");

        password = input.next();

        int index = ci.linearSearch(email, password, customers);

        if(index != -1){
            System.out.println("Hi, " + customers.get(index).getFirstName() + " " +
                    customers.get(index).getLastName() + "!");

            System.out.println();

            System.out.println("We have the following account information on file for you:");

            System.out.println();

            System.out.println(customers.get(index).toString());


        }else{
            System.out.println("We don't have your account on file.\nLet's create a new account for you...");
            Customer newCustom = ci.createAccount(email, password, input);
            customers.add(newCustom);
            index = customers.size()-1;
        }

        String menuOption = "p";

        while (!(menuOption.equals("X"))){
            System.out.println("Please select from the following options:");
            System.out.println("A. Add Cash");
            System.out.println("B. Buy a New Fund");
            System.out.println("C. Sell a Fund");
            System.out.println("X. Exit");
            System.out.println("Enter your choice: ");
            if (input.hasNext()) {
                menuOption = input.next();
            } else {
                menuOption = "X";
            }
            if (menuOption.equals("A")) {
                System.out.println("You currently have the following cash balance: "
                        + cashFormat.format(customers.get(index).getCash()));

                System.out.println("Enter the dollar amount you wish to add: $");

                double addedCash = input.nextDouble();

                customers.get(index).updateCash(addedCash);

                System.out.println();

                System.out.println("New cash balance: " + cashFormat.format(customers.get(index).getCash()));

            } else if (menuOption.equals("B")) {
                System.out.print("Enter the ticker symbol for the fund to purchase: ");

                String tickerNew = input.next();

                int mutualIndex = binarySearch(funds, tickerNew);

                MutualFund tempMutual = funds.get(mutualIndex);

                System.out.println(tempMutual.getFundName() + " sells for "
                        + cashFormat.format(tempMutual.getPricePerShare()) + " per share and has a fee of "
                        + tempMutual.getTradingFee() + "%\n");

                System.out.print("Enter the number of shares to purchase: ");

                double wantedShares = input.nextDouble();

                boolean authorization = customers.get(index).addFund(wantedShares, tempMutual);

                if(authorization){
                    Customer customer = customers.get(index);
                    ArrayList<MutualFundAccount> fundament = customer.getFunds();

                    boolean fundFound = false;
                    for (MutualFundAccount account : fundament) {
                        if (account.getMf().getTicker().equals(tempMutual.getTicker())) {
                            account.updateShares(wantedShares);
                            fundFound = true;
                        }
                    }

                    if (!fundFound) {
                        // Add a new MutualFundAccount if this fund is not already owned
                        fundament.add(new MutualFundAccount(wantedShares, tempMutual));
                    }
                    System.out.println("This fund has been added!");
                    System.out.println();
                    System.out.println("Your updated cash balance: "
                            + cashFormat.format(customers.get(index).getCash()));
                    System.out.println();
                    System.out.println("Here are your current funds:\n");
                    ci.printAccounts(customers.get(index).getFunds(), customers.get(index).getAccountNum());


                }else{
                    System.out.println("You don't have enough cash for this transaction."
                            + "\nPlease make a cash deposit to try again.");
                }


            } else if (menuOption.equals("C")) {
                System.out.println("Here are your current funds:\n");
                ci.printAccounts(customers.get(index).getFunds(), customers.get(index).getAccountNum());
                System.out.println("Enter the last digit of the account number for the fund to sell:");
                int indexForSale = input.nextInt();
                if(indexForSale >= 0 && indexForSale < customers.get(index).getFunds().size()){
                    indexForSale -= 1;
                    customers.get(index).sellFund(indexForSale);
                    System.out.println("Your updated cash balance: "
                            + cashFormat.format(customers.get(index).getCash()));
                    System.out.println();
                    System.out.println("Here are your current funds:\n");
                    ci.printAccounts(customers.get(index).getFunds(), customers.get(index).getAccountNum());
                }else{
                    System.out.println("Invalid account number. Please try again.");
                }



            } else if (menuOption.equals("X")) {

            } else {
                System.out.println("Invalid menu option. Please enter A-C or X to exit.");
            }
        }



    }



    /**
     * @param email the email of the person trying to get in
     * @param password the password of the person trying to get in
     * @param users the array list of customers the person is trying to search through
     * @return the index of the person
     */
    public int linearSearch(String email, String password, ArrayList<Customer> users){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getEmail().equals(email) && users.get(i).passwordMatch(password)){
                return i;
            }
        }

        return -1;
    }
    /**
     * Prints each MutualFundAccount in the list with the associated customer account number.
     *
     * @param mfas the list of MutualFundAccount objects
     * @param account the customer account number
     */
    public void printAccounts(ArrayList<MutualFundAccount> mfas, String account) {
        for (int i = 0; i < mfas.size(); i++) {
            MutualFundAccount mfa = mfas.get(i);
            int index = i + 1;
            System.out.println(mfa.getMf().toString());
            System.out.println(mfa.toString());
            System.out.println("Account Number:" + account + "-" + index + "\n");
        }
    }
    /**
     * @param the email of the person
     * @param the password of the person
     * @param the scanner inpu of the original file
     * @return the new customer of the person
     */
    public Customer createAccount(String email, String password, Scanner input){
        input.nextLine();
        System.out.println("Enter your first name: ");
        String firstName = input.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = input.next();
        System.out.println("Enter your starting cash deposit: ");
        double cash = input.nextDouble();

        ArrayList<MutualFundAccount> funds = new ArrayList<>();

        return new Customer(firstName, lastName, email, password, cash, funds);

    }


    /**
     * Binary search for a ticker in a sorted ArrayList of MutualFund objects.
     * @param funds Sorted ArrayList of MutualFund by ticker.
     * @param ticker The ticker symbol to search for.
     * @return Index of the matching MutualFund, or -1 if not found.
     */
    public static int binarySearch(ArrayList<MutualFund> al, String ticker) {
        int left = 0;
        int right = al.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midTicker = al.get(mid).getTicker();

            int cmp = midTicker.compareTo(ticker);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Not found
    }


}
