import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ATMOperations extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ATM";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "692120";

    private String loggedInUser;

    private JTextField accountNumberField;
    private JTextField pinField;
    private JTextField balanceField;
    private JTextField amountField;

    public ATMOperations(String user) {
        this.loggedInUser = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("ATM Operations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createBackgroundPanel() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(30, 144, 255));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("ATM Operations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 5, 20, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(titleLabel, constraints);

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setForeground(Color.WHITE);
        accountNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(accountNumberLabel, constraints);

        accountNumberField = new JTextField(20);
        accountNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        accountNumberField.setOpaque(false);
        accountNumberField.setBackground(new Color(255, 255, 255, 150));
        accountNumberField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        backgroundPanel.add(accountNumberField, constraints);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setForeground(Color.WHITE);
        pinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(pinLabel, constraints);

        pinField = new JTextField(20);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setOpaque(false);
        pinField.setBackground(new Color(255, 255, 255, 150));
        pinField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        backgroundPanel.add(pinField, constraints);

        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(balanceLabel, constraints);

        balanceField = new JTextField(20);
        balanceField.setFont(new Font("Arial", Font.PLAIN, 14));
        balanceField.setEditable(false);
        balanceField.setOpaque(false);
        balanceField.setBackground(new Color(255, 255, 255, 150));
        balanceField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        backgroundPanel.add(balanceField, constraints);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(checkBalanceButton, constraints);

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(amountLabel, constraints);

        amountField = new JTextField(20);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField.setOpaque(false);
        amountField.setBackground(new Color(255, 255, 255, 150));
        amountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        backgroundPanel.add(amountField, constraints);

        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(20, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(depositButton, constraints);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositAmount();
            }
        });

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.insets = new Insets(20, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(withdrawButton, constraints);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdrawAmount();
            }
        });

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(logoutButton, constraints);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginPage.createAndShowGUI();
            }
        });

        return backgroundPanel;
    }

    private void checkBalance() {
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();

        if (accountNumber.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter account number and PIN.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT balance FROM accounts WHERE account_number = ? AND pin = ? AND user = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, accountNumber);
                preparedStatement.setString(2, pin);
                preparedStatement.setString(3, loggedInUser);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double balance = resultSet.getDouble("balance");
                        balanceField.setText(String.valueOf(balance));
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid account number or PIN.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void depositAmount() {
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();
        String amountText = amountField.getText();

        if (accountNumber.isEmpty() || pin.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter account number, PIN, and amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ? AND pin = ? AND user = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setDouble(1, amount);
                    preparedStatement.setString(2, accountNumber);
                    preparedStatement.setString(3, pin);
                    preparedStatement.setString(4, loggedInUser);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Deposit successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        checkBalance();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid account number or PIN.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void withdrawAmount() {
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();
        String amountText = amountField.getText();

        if (accountNumber.isEmpty() || pin.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter account number, PIN, and amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND pin = ? AND user = ? AND balance >= ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setDouble(1, amount);
                    preparedStatement.setString(2, accountNumber);
                    preparedStatement.setString(3, pin);
                    preparedStatement.setString(4, loggedInUser);
                    preparedStatement.setDouble(5, amount);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Withdrawal successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        checkBalance();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid account number, PIN, or insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
