import javax.swing.*;
import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;//the account which the transaction was performed

    public Transaction(double amount, Account inAccount){
        this.amount=amount;
        this.inAccount=inAccount;
        this.timestamp=new Date();
        this.memo="";
    }
    public Transaction(double amount,String memo, Account inAccount){
        //call the two.arg constructor first -this means one update reflects on both
        this(amount, inAccount);

        //set memo
        this.memo=memo;
    }

    //get the amount of the transactions
    public double getAmount(){
        return this.amount;
    }

    public String getSummaryLine()  {
        if(this.amount>=0){
            return String.format("%s : $%.02f : %s",this.timestamp.toString(),
                    this.amount,this.memo);
        }else {
            return String.format("%s : $(%.02f) : %s",this.timestamp.toString(),
                    -this.amount,this.memo);
        }
    }
}
