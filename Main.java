import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        BufferedImage bgImage = null;
        try {
            URL imageUrl = new URL("https://png.pngtree.com/thumb_back/fh260/background/20230617/pngtree-mobile-banking-in-action-3d-rendered-smartphone-and-atm-machine-with-image_3639915.jpg");
            bgImage = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Login Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));

        BufferedImage finalBgImage = bgImage;
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalBgImage != null) {
                    g.drawImage(finalBgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Welcome to ATM Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(10, 5, 20, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(titleLabel, constraints);

        JLabel userLabel = new JLabel("User:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(userLabel, constraints);

        JTextField userField = new JTextField(20);
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

        userField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                userField.setBackground(new Color(255, 255, 255, 200));
                userField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                userField.setBackground(new Color(255, 255, 255, 150));
                userField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        });
        userField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                userField.setOpaque(true);
            }
        });

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        backgroundPanel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
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

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBackground(new Color(255, 255, 255, 200));
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBackground(new Color(255, 255, 255, 150));
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                passwordField.setOpaque(true);
            }
        });

        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(70, 130, 180));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g.setColor(Color.WHITE);
                g.drawString(getText(), getWidth() / 2 - g.getFontMetrics().stringWidth(getText()) / 2, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
            }
        };
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(20, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(loginButton, constraints);

        frame.add(backgroundPanel, BorderLayout.CENTER);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        frame.add(messageLabel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String user = userField.getText();
                final String password = new String(passwordField.getPassword());

                if (user.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Please enter both username and password.");
                    return;
                }

                final String url = "jdbc:mysql://localhost:3306/ATM";
                final String dbUser = "root";
                final String dbPassword = "692120";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                    String query = "SELECT * FROM login WHERE user = ? AND password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, user);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        messageLabel.setText("Login successful!");
                        messageLabel.setForeground(Color.GREEN);
                        userField.setText("");
                        passwordField.setText("");
                    } else {
                        messageLabel.setText("Invalid username or password.");
                        messageLabel.setForeground(Color.RED);
                    }

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (ClassNotFoundException ex) {
                    messageLabel.setText("MySQL JDBC Driver not found.");
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    messageLabel.setText("Connection failed: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }
}
