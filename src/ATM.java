import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        //initialize scanner
        Scanner scanner=new Scanner(System.in);

        //initialize bank
        Bank theBank=new Bank("Bank of Knights");

        //add a user and also create a saving account
        User aUser=theBank.addUser("Joram","Kichesty","1234");

        //add a checking account for our user
        Account newAccount=new Account("Checking",aUser,theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){
            //stay in the login prompt until successful login
            curUser=ATM.mainMenuPrompt(theBank,scanner);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser,scanner);
        }
    }
    //printing the login menu
    public static User mainMenuPrompt(Bank theBank,Scanner scanner){
        //initialize
        String userId;
        String pin;
        User outhUser;

        //prompt user for id/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
            System.out.print("Enter User ID: ");
            userId=scanner.nextLine();
            System.out.print("Enter Pin: ");
            pin=scanner.nextLine();

            //try to get the user object corresponding to id and pin combo
            outhUser=theBank.userLogin(userId,pin);
            if (outhUser==null){
                System.out.println("Incorrect user ID/pin combination."+
                        "Please Re-Enter again.");
            }
        }while (outhUser==null);//continue looping until successful login
        return outhUser;
    }

    public static void printUserMenu(User theUser,Scanner scanner){
        //print of the summary of the user's accounts
         theUser.printAccountsSummary();

         //initials
        int choice;
        //user menu
        do{
            System.out.printf("Welcome %s ,What would you like to do?\n",
                    theUser.getFirstName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter your choice of operation: ");
            choice=scanner.nextInt();

            if (choice<1 || choice>5){
                System.out.println("Incorrect Choice."+
                        "Please Re-Enter again."+"Choose between 1-5");
            }
        }while (choice<1 || choice>5);

        //processing the choice
        switch (choice){

            case 1:
                ATM.showTransHistory(theUser,scanner);
                break;
            case 2:
                ATM.withdrawFunds(theUser,scanner);
                break;
            case 3:
                ATM.depositFunds(theUser,scanner);
                break;
            case 4:
                ATM.transferFunds(theUser,scanner);
                break;
            case 5:
                //gobble up the rest of previous input
                scanner.nextLine();
                break;
        }
        //redisplay this menu unless the user wants to quit--recursive method
        if (choice != 5){
            ATM.printUserMenu(theUser,scanner);
        }
    }

    //showing the transaction history for the accounts
    private static void showTransHistory(User theUser, Scanner scanner) {
        int theAcct;
        //get the account whose history is to be shown
        do {
            System.out.printf("Enter the number (1-%d) of the account \n" +
                    "whose transactions you want to see: ",theUser.numAccounts());
            theAcct =scanner.nextInt()-1;
            if (theAcct<0 || theAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again!!.");
            }
        }while (theAcct<0 || theAcct>=theUser.numAccounts());

        //print the transaction history
        theUser.printAcctTransHistory(theAcct);
    }

    private static void transferFunds(User theUser, Scanner scanner) {

        //initials
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //get the account to transfer from
        do {
            System.out.printf("Enter the Account number (1-%d) \n"
            +"of the account to transfer from", theUser.numAccounts());
            fromAcct=scanner.nextInt()-1;
            if (fromAcct<0 || fromAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again!!.");
            }
        }while (fromAcct<0 || fromAcct>=theUser.numAccounts());
        acctBal=theUser.getAcctBalance(fromAcct);

        //get the account to transfer to
        do {
            System.out.printf("Enter the Account number (1-%d) \n"
                    +"of the account to transfer to",theUser.numAccounts());
            toAcct=scanner.nextInt()-1;
            if (toAcct<0 || toAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again!!.");
            }
        }while (toAcct<0 || toAcct>=theUser.numAccounts());

        //get the amount to transfer
        do {
            System.out.printf("Enter the Amount (max $%.02f): $",
                    acctBal);
            amount=scanner.nextDouble();
            if (amount<0 ){
                System.out.println("Amount must be greater than zero.Please try again!!.");
            } else if (amount>acctBal) {
                System.out.printf("No sufficient funds to complete the transfer." +
                        "Please try again!!.");

            }
        }while (amount<0 || amount>acctBal);

        //finally do the transfer
        theUser.addAcctTransaction(fromAcct,-1*amount,String.format("Transfer to account %s",
                theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct,amount,String.format("Transfer to account %s",
                theUser.getAcctUUID(fromAcct)));
    }

    //process of funds withdrawing frm the account
    private static void withdrawFunds(User theUser, Scanner scanner) {
        //initials
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        //get the account to withdraw from
        do {
            System.out.printf("Enter the Account number (1-%d) \n"
                    +"of the account to withdraw from",theUser.numAccounts());
            fromAcct=scanner.nextInt()-1;
            if (fromAcct<0 || fromAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again!!.");
            }
        }while (fromAcct<0 || fromAcct>=theUser.numAccounts());
        acctBal=theUser.getAcctBalance(fromAcct);

        //get the amount to withdraw
        do {
            System.out.printf("Enter the Amount (max $%.02f): $",
                    acctBal);
            amount=scanner.nextDouble();
            if (amount<0 ){
                System.out.println("Amount must be greater than zero.Please try again!!.");
            } else if (amount>acctBal) {
                System.out.printf("No sufficient funds to complete the transfer." +
                        "Please try again!!.");

            }
        }while (amount<0 || amount>acctBal);

        //gobble up the rest of previous input
        scanner.nextLine();

        //get the memo
        System.out.println("Enter the memo: ");
        memo=scanner.nextLine();

        //do the withdrawal
        theUser.addAcctTransaction(fromAcct,-1*amount,memo);
    }

    //process to deposit funds
    private static void depositFunds(User theUser, Scanner scanner) {
        //initials
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        //get the account to deposit to
        do {
            System.out.printf("Enter the Account number (1-%d) \n"
                    +"of the account to deposit", theUser.numAccounts());
            toAcct=scanner.nextInt()-1;
            if (toAcct<0 || toAcct>=theUser.numAccounts()){
                System.out.println("Invalid account. Please try again!!.");
            }
        }while (toAcct<0 || toAcct>=theUser.numAccounts());
        acctBal=theUser.getAcctBalance(toAcct);

        //get the amount to deposit
        do {
            System.out.printf("Enter the Amount (max $%.02f): $",
                    acctBal);
            amount=scanner.nextDouble();
            if (amount<0 ){
                System.out.println("Amount must be greater than zero.Please try again!!.");
            }
        }while (amount<0);

        //gobble up the rest of previous input
        scanner.nextLine();

        //get the memo
        System.out.println("Enter the memo: ");
        memo=scanner.nextLine();

        //do the withdrawal
        theUser.addAcctTransaction(toAcct,amount,memo);
    }
}