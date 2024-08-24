import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

class RoundedButton extends JButton {
    private int radius = 20;

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().brighter());
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        
    }
}

public class ATMInterface {
    private ATM atm;
    private JFrame frame;
    private JTextField accountNumberField;
    private JPasswordField passwordField;
    private JTextField amountField;
    private JLabel titleLabel;

    public ATMInterface() {
        atm = new ATM();
        atm.addAccount("1234567890123456", 500.00, "1234");  // Account No. and Password must be 16 and 4 digits respectively.

        frame = new JFrame("ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); 

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));  
        panel.setBackground(new Color(204, 204, 204)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); 
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel);

        placeComponents(panel, gbc);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, GridBagConstraints gbc) {
        titleLabel = new JLabel("ATM 24/7 Service");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32)); 
        titleLabel.setForeground(new Color(102, 0, 153));  
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel accountLabel = new JLabel("Enter Account No:-");
        accountLabel.setFont(new Font("Arial", Font.PLAIN, 24)); 
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(accountLabel, gbc);

        accountNumberField = new JTextField(20);
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 24)); 
        gbc.gridx = 1;
        panel.add(accountNumberField, gbc);

        JLabel passwordLabel = new JLabel("Enter Password:-");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 24)); 
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24)); 
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JLabel amountLabel = new JLabel("Enter Amount:-");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 24)); 
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(amountLabel, gbc);

        amountField = new JTextField(20);
        amountField.setFont(new Font("Arial", Font.PLAIN, 24)); 
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        RoundedButton depositButton = new RoundedButton("Deposit");
        styleButton(depositButton, new Color(0, 153, 76), new Color(0, 204, 102));  
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(depositButton, gbc);

        RoundedButton withdrawButton = new RoundedButton("Withdrawl");
        styleButton(withdrawButton, new Color(204, 51, 51), new Color(255, 102, 102));  
        gbc.gridx = 1;
        panel.add(withdrawButton, gbc);

        RoundedButton clearButton = new RoundedButton("Clear");
        styleButton(clearButton, new Color(255, 153, 51), new Color(255, 204, 102));  
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(clearButton, gbc);

        RoundedButton cancelButton = new RoundedButton("Cancel");
        styleButton(cancelButton, new Color(77, 77, 77), new Color(102, 102, 102));  
        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        RoundedButton checkBalanceButton = new RoundedButton("Check Balance");
        styleButton(checkBalanceButton, new Color(0, 102, 204), new Color(51, 153, 255)); 
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(checkBalanceButton, gbc);

        RoundedButton exitButton = new RoundedButton("To Exit Here →");
        styleButton(exitButton, new Color(0, 0, 0), new Color(102, 102, 102));  
        gbc.gridx = 1;
        panel.add(exitButton, gbc);

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

    private void styleButton(RoundedButton button, Color normalColor, Color hoverColor) {
        button.setFont(new Font("Arial", Font.PLAIN, 24));  // Increased font size
        button.setBackground(normalColor);  // Setting button background color
        button.setForeground(Color.WHITE);  // Setting button text color

        // Adding mouse listener for hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
                button.repaint();  // Force a repaint to ensure hover color is applied
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
                button.repaint();  // Force a repaint to revert to normal color
            }
        });
    }

    private void showCustomMessageDialog(String message, String title, Color backgroundColor) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(frame, title);
        dialog.setBackground(backgroundColor);
        dialog.getContentPane().setBackground(backgroundColor);
        dialog.setVisible(true);
    }

    private void handleDeposit() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());
        String amountStr = amountField.getText();
        if (atm.validateAccount(accountNumber)) {
            Account account = atm.getAccount(accountNumber);
            if (account.validatePassword(password)) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    account.deposit(amount);
                    showCustomMessageDialog("Deposit Successful!", "Success", Color.GREEN);
                } catch (NumberFormatException ex) {
                    showCustomMessageDialog("Invalid amount entered!", "Error", Color.RED);
                }
            } else {
                showCustomMessageDialog("Invalid password!", "Error", Color.RED);
            }
        } else {
            showCustomMessageDialog("Account not found!", "Error", Color.RED);
        }
    }

    private void handleWithdraw() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());
        String amountStr = amountField.getText();
        if (atm.validateAccount(accountNumber)) {
            Account account = atm.getAccount(accountNumber);
            if (account.validatePassword(password)) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (account.withdraw(amount)) {
                        showCustomMessageDialog("Withdrawal Successful!", "Success", Color.GREEN);
                    } else {
                        showCustomMessageDialog("Insufficient funds!", "Error", Color.RED);
                    }
                } catch (NumberFormatException ex) {
                    showCustomMessageDialog("Invalid amount entered!", "Error", Color.RED);
                }
            } else {
                showCustomMessageDialog("Invalid password!", "Error", Color.RED);
            }
        } else {
            showCustomMessageDialog("Account not found!", "Error", Color.RED);
        }
    }

    private void handleCheckBalance() {
        String accountNumber = accountNumberField.getText();
        String password = new String(passwordField.getPassword());
        if (atm.validateAccount(accountNumber)) {
            Account account = atm.getAccount(accountNumber);
            if (account.validatePassword(password)) {
                showCustomMessageDialog("Your balance is:- ₹ " + account.getBalance(), "Balance", Color.BLUE);
            } else {
                showCustomMessageDialog("Invalid password!", "Error", Color.RED);
            }
        } else {
            showCustomMessageDialog("Account not found!", "Error", Color.RED);
        }
    }

    private void cancelOperation() {
        clearFields();
        showCustomMessageDialog("Operation cancelled.", "Cancelled", Color.ORANGE);
    }

    private void clearFields() {
        accountNumberField.setText("");
        passwordField.setText("");
        amountField.setText("");
    }

    public static void main(String[] args) {
        new ATMInterface();
    }
}
