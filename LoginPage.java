import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ATM";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "692120";

    private JTextField userField;
    private JPasswordField passwordField;

    public LoginPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("ATM Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
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

        JLabel titleLabel = new JLabel("ATM Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 5, 20, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(titleLabel, constraints);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(userLabel, constraints);

        userField = new JTextField(20);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setOpaque(false);
        userField.setBackground(new Color(255, 255, 255, 150));
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        backgroundPanel.add(userField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(255, 255, 255, 150));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        backgroundPanel.add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(loginButton, constraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        return backgroundPanel;
    }

    private void login() {
        String user = userField.getText();
        String password = new String(passwordField.getPassword());

        if (user.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM login WHERE user = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        dispose();
                        new ATMOperations(user);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }
}
