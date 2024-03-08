import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction>transactions;

    public Account(String name,User holder,Bank theBank){
        //set the account name and holder
        this.name=name;
        this.holder=holder;

        //get new account uuid
        this.uuid=theBank.getNewAccountUUID();

        //initiate transactions
        this.transactions=new ArrayList<Transaction>();

        //add to holder and bank list
        holder.addAccount(this);
        theBank.addAccount(this);
    }
    public String getUUID(){
        return this.uuid;
    }

    //get the summary line for the accounts
    public String getSummaryLine() {
        //get the balance
        double balance=this.getBalance();

        //format the summary line in case the account is negative
        if (balance>=0){
            return String.format("%s : $%.02f : %s",this.uuid,balance,this.name);
        }
        return null;
    }

    double getBalance() {
        double balance=0;
        for (Transaction t:this.transactions){
            balance+=t.getAmount();
        }
        return balance;
    }

    //print the transaction history of the account
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n",this.uuid);
        for (int t=this.transactions.size()-1;t>=0;t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    //adding new transactions in this account
    public void addTransaction(double amount, String memo) {
        //create new transaction object and add it to our list
        Transaction newTrans =new Transaction(amount,memo,this);
        this.transactions.add(newTrans);
    }
}
