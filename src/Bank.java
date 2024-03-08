import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User>users;
    private ArrayList<Account>accounts;

    public Bank(String name){
        this.name=name;
        this.users=new ArrayList<User>();
        this.accounts=new ArrayList<Account>();
    }
    //creating bank object with empty lists of users and accounts
    public String getNewUserUUID(){//generating new id for user
        //initializing
        String uuid;
        Random rng =new Random();
        int len=6;
        boolean nonUnique;

        // continue generating ids until we have a unique one
        do {
            //generating the number
            uuid="";
            for (int c=0; c<len; c++){
                uuid+=((Integer)rng.nextInt(10)).toString();
            }

            //check to make sure its unique
            nonUnique = false;
            for (User u:this.users){
                if (uuid.compareTo(u.getUUID())==0){
                    nonUnique=true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;
    }

    // continue generating new ids gor the accounts until we have a unique one
    public String getNewAccountUUID(){
    //initializing
    String uuid;
    Random rng =new Random();
    int len=10;
    boolean nonUnique;

    // continue generating ids until we have a unique one
    do {
        //generating the number
        uuid="";
        for (int c=0; c<len; c++){
            uuid+=((Integer)rng.nextInt(10)).toString();
        }

        //check to make sure its unique
        nonUnique=false;
        for (Account a:this.accounts){
            if (uuid.compareTo(a.getUUID())==0){
                nonUnique=true;
                break;
            }
        }

    }while (nonUnique);

    return uuid;

    }

    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public User addUser(String firstName,String lastName, String pin){
        //create a new user to add to our list
        User newUser =new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        //creating savings accounts for user
        Account newAccount =new Account("Savings", newUser,this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    //getting and validating user details
    public User userLogin(String userID,String pin){
        //search through the user list
        for (User u:this.users){
            //check user id is correct
            if (u.getUUID().compareTo(userID)==0 && u.validatePin(pin)){
                return u;
            }
        }
        //if we haven't found the user or the pin is invalid
        return null;
    }

    public String getName(){
        return this.name;
    }
}
