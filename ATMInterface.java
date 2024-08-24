import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class Account {
    private String accountNumber;
    private double balance;
    private String password;

    public Account(String accountNumber, double initialBalance, String password) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}

class ATM {
    private Map<String, Account> accounts;

    public ATM() {
        accounts = new HashMap<>();
    }

    public void addAccount(String accountNumber, double initialBalance, String password) {
        accounts.put(accountNumber, new Account(accountNumber, initialBalance, password));
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean validateAccount(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }
}

public class ATMInterface {
    private ATM atm;
    private JFrame frame;
    private JTextField accountNumberField;
    private JPasswordField passwordField;
    private JTextField amountField;
    private JLabel balanceLabel;
    private JLabel titleLabel;

    public ATMInterface() {
        atm = new ATM();
        atm.addAccount("1234567890123456", 500.00, "1234"); // Example account with 16 digits and password

        frame = new JFrame("ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));  // Adding a border to the panel
        panel.setBackground(Color.LIGHT_GRAY);  // Setting background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel);

        placeComponents(panel, gbc);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, GridBagConstraints gbc) {
        titleLabel = new JLabel("ATM 24/7 Service");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);  // Setting font color for the title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel accountLabel = new JLabel("Enter Account No:-");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(accountLabel, gbc);

        accountNumberField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(accountNumberField, gbc);

        JLabel passwordLabel = new JLabel("Enter Password:-");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JLabel amountLabel = new JLabel("Enter Amount:-");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(amountLabel, gbc);

        amountField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        JButton depositButton = new JButton("Deposit");
        styleButton(depositButton);  // Applying styling to the button
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(depositButton, gbc);

        JButton withdrawButton = new JButton("Withdrawl");
        styleButton(withdrawButton);  // Applying styling to the button
        gbc.gridx = 1;
        panel.add(withdrawButton, gbc);

        // Swapped positions for Cancel and Clear buttons
        JButton clearButton = new JButton("Clear");
        styleButton(clearButton);  // Applying styling to the button
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(clearButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);  // Applying styling to the button
        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        JButton checkBalanceButton = new JButton("Check Balance");
        styleButton(checkBalanceButton);  // Applying styling to the button
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(checkBalanceButton, gbc);

        JButton exitButton = new JButton("To Exit Here →");
        styleButton(exitButton);  // Applying styling to the button
        gbc.gridx = 1;
        panel.add(exitButton, gbc);

        balanceLabel = new JLabel("Balance:- ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(balanceLabel, gbc);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleWithdraw();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBalance();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelOperation();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));  // Setting font size
        button.setBackground(Color.DARK_GRAY);  // Setting button background color
        button.setForeground(Color.WHITE);  // Setting button text color
        button.setFocusPainted(false);  // Removing focus border
    }

    private void handleDeposit() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());
        double amount = parseAmount(amountField.getText());

        if (atm.validateAccount(accountNumber)) {
            Account account = atm.getAccount(accountNumber);
            if (account.validatePassword(password)) {
                account.deposit(amount);
                JOptionPane.showMessageDialog(frame, "Deposit Successful");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Password");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Account Number");
        }
    }

    private void handleWithdraw() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());
        double amount = parseAmount(amountField.getText());

        if (atm.validateAccount(accountNumber)) {
            Account account = atm.getAccount(accountNumber);
            if (account.validatePassword(password)) {
                boolean success = account.withdraw(amount);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Withdrawal Successful");
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient Funds");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Password");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Account Number");
        }
    }

    private void handleCheckBalance() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());

        if (atm.validateAccount(accountNumber)) {
            Account account = atm.getAccount(accountNumber);
            if (account.validatePassword(password)) {
                double balance = account.getBalance();
                balanceLabel.setText("Balance: " + balance);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Password");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Account Number");
        }
    }

    private void clearFields() {
        accountNumberField.setText("");
        passwordField.setText("");
        amountField.setText("");
        balanceLabel.setText("Balance: ");
    }

    private void cancelOperation() {
        clearFields();
        JOptionPane.showMessageDialog(frame, "Operation Canceled");
    }

    private double parseAmount(String amountText) {
        try {
            return Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        new ATMInterface();
    }
}
