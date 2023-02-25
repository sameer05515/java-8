package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class Test6 {

    public static void main(String[] args) {
        List<Transaction> list=TransactionRepo.getAllTransactions();

        // Map , group by key currency, value list of txns
        Map<String,List<Transaction>> map=
                list.stream().filter(t->t.getAmount()>900).collect(groupingBy(Transaction::getCurrency));
        map.forEach((k,v)->{
                    System.out.println(k+" "+v);
                });

        // Map, group by key currency,value Map group by state, value Transactions
        Map<String, Map<String,List<Transaction>>> map1=
                list.stream().filter(t->t.getAmount()>900).collect(groupingBy(Transaction::getCurrency,groupingBy(Transaction::getState)));

        map1.forEach((k,v)->{
            System.out.println(k);
            v.forEach((k1,v1)->{
                System.out.println(k1+" "+v1);
            });
        });
    }
}

class TransactionRepo{
    public static List<Transaction> getAllTransactions(){
        List<Transaction> list=new ArrayList<>();
        String[] currency={"INR","USD","RBL","XYZ"};
        String[] states={"Delhi","Uttar Pradesh","Bihar","West Bengal"};

        Random random = new Random();

        for (String cur: currency) {
            for (String state: states) {
                for (int i = 0; i < 10; i++) {
                    list.add(new Transaction(random.nextInt(1000), cur, state));
                }
            }
        }
        return list;
    }
}

class Transaction{
    private int amount;
    private String currency;

    public Transaction(int amount, String currency, String state) {
        this.amount = amount;
        this.currency = currency;
        this.state = state;
    }

    private String state;


    public Transaction() {
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

//    public Transaction(int amount, String currency) {
//        this.amount = amount;
//        this.currency = currency;
//    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
