package logic;

public class wallet {
    // Instance duy nhất
    private static wallet instance;
    private double balance;

    private wallet() {
        this.balance = 1000.0; // Tiền khởi đầu
    }

    // Phương thức truy cập instance duy nhất
    public static synchronized wallet getInstance() {
        if (instance == null) {
            instance = new wallet();
        }
        return instance;
    }

    public boolean deduct(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void add(double amount) {
        balance += amount;
    }

    public double getBalance() { return balance; }
}