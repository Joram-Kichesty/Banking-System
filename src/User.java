import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private final String firstName;
    private final String lastName;
    private final String uuid;//id of the user
    private byte[] pinHash;
    private final ArrayList<Account> accounts;

    public User(String firstName,String lastName,String pin,Bank theBank){
        //set user's names
        this.firstName=firstName;
        this.lastName=lastName;

        //Hashing the pin(storing the pin in hash rather than value for security)
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            this.pinHash= md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error!!!!!!!");
            e.printStackTrace();
            System.exit(1);
         }

        //getting new unique universal Id for user
        this.uuid=theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts=new ArrayList<Account>();

        //print log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName,firstName,this.uuid);

    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatePin(String aPin){

        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash );
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error!!!!!!!");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    //print summaries for the accounts of the user
    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary\n",this.firstName);
        for (int a=0;a<this.accounts.size();a++){
            System.out.printf("  %d) %s\n",a+1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    //get the number of account of the user returns the number of accounts
    public int numAccounts() {
        return this.accounts.size();
    }

    //print the transaction history for a particular account
    public void printAcctTransHistory(int acctIdx) {
       this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    //get uuid of a particular account
    public String  getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    //adding the account transactions
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount,memo);
    }
}
