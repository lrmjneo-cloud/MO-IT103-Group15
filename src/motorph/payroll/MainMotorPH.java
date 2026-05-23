package motorph.payroll;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.geom.Path2D;

public class MainMotorPH {
    private static CardLayout cardLayout;
    private static JPanel mainCardPanel;
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JTabbedPane tabbedPane;

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "MotorPH");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MotorPH");

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("MotorPH Payroll System");
        frame.setSize(850, 620);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Vector wave background panel container
        WaveBackgroundPanel rootContainer = new WaveBackgroundPanel();
        rootContainer.setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainCardPanel = new JPanel(cardLayout);
        mainCardPanel.setOpaque(false);

        mainCardPanel.add(createLoginPanel(), "LoginScreen");
        mainCardPanel.add(createDashboardPanel(), "DashboardScreen");

        rootContainer.add(mainCardPanel, BorderLayout.CENTER);
        frame.setContentPane(rootContainer);

        cardLayout.show(mainCardPanel, "LoginScreen");
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel() {
        JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setOpaque(false);

        JPanel borderWrapperPanel = new JPanel(new BorderLayout());
        borderWrapperPanel.setOpaque(false);
        borderWrapperPanel.setBackground(new Color(255, 255, 255, 35));
        borderWrapperPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 195));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(40, 45, 40, 45));

        JLabel titleLabel = new JLabel("MotorPH Portal");
        titleLabel.setFont(PayrollService.FONT_TITLE);
        titleLabel.setForeground(PayrollService.MAC_ACCENT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sign in with your corporate account credentials");
        subtitleLabel.setFont(PayrollService.FONT_BODY);
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(PayrollService.FONT_BOLD);
        userLabel.setForeground(PayrollService.MAC_TEXT_DARK);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(320, 36));
        usernameField.setPreferredSize(new Dimension(320, 36));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PayrollService.MAC_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(PayrollService.FONT_BOLD);
        passLabel.setForeground(PayrollService.MAC_TEXT_DARK);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(320, 36));
        passwordField.setPreferredSize(new Dimension(320, 36));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PayrollService.MAC_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));

        JButton loginButton = new JButton("Sign In");
        loginButton.setFont(PayrollService.FONT_BOLD);
        loginButton.setBackground(PayrollService.MAC_ACCENT);
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setMaximumSize(new Dimension(320, 40));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        cardPanel.add(titleLabel);
        cardPanel.add(Box.createVerticalStrut(4));
        cardPanel.add(subtitleLabel);
        cardPanel.add(Box.createVerticalStrut(28));

        cardPanel.add(userLabel);
        cardPanel.add(Box.createVerticalStrut(6));
        cardPanel.add(usernameField);

        cardPanel.add(Box.createVerticalStrut(18));

        cardPanel.add(passLabel);
        cardPanel.add(Box.createVerticalStrut(6));
        cardPanel.add(passwordField);

        cardPanel.add(Box.createVerticalStrut(32));
        cardPanel.add(loginButton);

        borderWrapperPanel.add(cardPanel, BorderLayout.CENTER);
        basePanel.add(borderWrapperPanel);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(basePanel, "Please fill out all credentials.", "Incomplete Fields",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (Account.verifyCredentials(user, pass)) {
                Account.initData();
                tabbedPane.removeAll();

                if ("employee".equals(user)) {
                    tabbedPane.addTab("Employee Portal", PayrollService.createEmployeePanel());
                } else {
                    tabbedPane.addTab("Payroll Management", PayrollService.createPayrollPanel());
                }
                cardLayout.show(mainCardPanel, "DashboardScreen");
            } else {
                JOptionPane.showMessageDialog(basePanel, "Invalid login credentials detected.", "Auth Fault",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return basePanel;
    }

    private static JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(PayrollService.FONT_BOLD);
        tabbedPane.setOpaque(false);

        // FIXED: Forcefully intercept the Windows System UI pipeline to clear the
        // background panel color
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
                    boolean isSelected) {
                // Do nothing here to keep tab headers transparent
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                // Do nothing here to completely clear the massive white panel border body block
            }
        });

        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    private static class WaveBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            Color deepNavy = new Color(15, 32, 67);
            Color steelBlue = new Color(41, 74, 122);
            Color brightBlue = new Color(0, 122, 255);

            g2d.setColor(deepNavy);
            g2d.fillRect(0, 0, w, h);

            GradientPaint midGradient = new GradientPaint(0, h / 2, steelBlue, w, h, deepNavy);
            g2d.setPaint(midGradient);

            Path2D.Double wave1 = new Path2D.Double();
            wave1.moveTo(0, h * 0.4);
            wave1.curveTo(w * 0.3, h * 0.2, w * 0.6, h * 0.7, w, h * 0.5);
            wave1.lineTo(w, h);
            wave1.lineTo(0, h);
            wave1.closePath();
            g2d.fill(wave1);

            GradientPaint topGradient = new GradientPaint(0, h, brightBlue, w, h * 0.6f, steelBlue);
            g2d.setPaint(topGradient);

            Path2D.Double wave2 = new Path2D.Double();
            wave2.moveTo(0, h * 0.65);
            wave2.curveTo(w * 0.35, h * 0.85, w * 0.65, h * 0.45, w, h * 0.7);
            wave2.lineTo(w, h);
            wave2.lineTo(0, h);
            wave2.closePath();
            g2d.fill(wave2);

            g2d.dispose();
        }
    }
}