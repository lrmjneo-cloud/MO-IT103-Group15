package motorph.payroll.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMotorPH {
    private static CardLayout cardLayout;
    private static JPanel mainCardPanel;
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JTabbedPane tabbedPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("MotorPH Payroll System");
        frame.setSize(800, 580);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainCardPanel = new JPanel(cardLayout);
        mainCardPanel.setBackground(PayrollService.MAC_BG);

        mainCardPanel.add(createLoginPanel(), "LoginScreen");
        mainCardPanel.add(createDashboardPanel(), "DashboardScreen");

        frame.add(mainCardPanel);
        cardLayout.show(mainCardPanel, "LoginScreen");
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel() {
        JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setBackground(PayrollService.MAC_BG);

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(PayrollService.MAC_CARD_BG);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PayrollService.MAC_BORDER, 1), new EmptyBorder(35, 45, 35, 45)));

        JLabel titleLabel = new JLabel("MotorPH Portal");
        titleLabel.setFont(PayrollService.FONT_TITLE); titleLabel.setForeground(PayrollService.MAC_ACCENT); titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Sign in with your corporate account credentials");
        subtitleLabel.setFont(PayrollService.FONT_BODY); subtitleLabel.setForeground(PayrollService.MAC_TEXT_MUTED); subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField(); usernameField.setMaximumSize(new Dimension(320, 35));
        passwordField = new JPasswordField(); passwordField.setMaximumSize(new Dimension(320, 35));

        JButton loginButton = new JButton("Sign In");
        loginButton.setFont(PayrollService.FONT_BOLD); loginButton.setBackground(PayrollService.MAC_ACCENT); loginButton.setForeground(Color.WHITE); loginButton.setOpaque(true); loginButton.setBorderPainted(false);
        loginButton.setMaximumSize(new Dimension(320, 40)); loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(titleLabel); cardPanel.add(Box.createVerticalStrut(6)); cardPanel.add(subtitleLabel); cardPanel.add(Box.createVerticalStrut(30));
        cardPanel.add(usernameField); cardPanel.add(Box.createVerticalStrut(16)); cardPanel.add(passwordField); cardPanel.add(Box.createVerticalStrut(28));
        cardPanel.add(loginButton); basePanel.add(cardPanel);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(basePanel, "Please fill out all credentials.", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Cross-checking access control checks strictly via Account layer
            if (Account.verifyCredentials(user, pass)) {
                Account.initData(); 
                tabbedPane.removeAll();
                
                // Fetching UI layers cleanly from the independent PayrollService generator
                if ("employee".equals(user)) {
                    tabbedPane.addTab("Employee Portal", PayrollService.createEmployeePanel()); 
                } else {
                    tabbedPane.addTab("Payroll Management", PayrollService.createPayrollPanel());
                }
                cardLayout.show(mainCardPanel, "DashboardScreen");
            } else {
                JOptionPane.showMessageDialog(basePanel, "Invalid login credentials detected.", "Auth Fault", JOptionPane.ERROR_MESSAGE);
            }
        });

        return basePanel;
    }

    private static JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PayrollService.MAC_BG);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(PayrollService.FONT_BOLD);
        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }
}