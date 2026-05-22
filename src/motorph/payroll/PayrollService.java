package motorph.payroll.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PayrollService {
    // Shared UI Aesthetics Configuration
    public static final Color MAC_BG = new Color(245, 245, 247);
    public static final Color MAC_CARD_BG = Color.WHITE;
    public static final Color MAC_ACCENT = new Color(0, 122, 255);
    public static final Color MAC_TEXT_MAIN = new Color(29, 29, 31);
    public static final Color MAC_TEXT_MUTED = new Color(134, 134, 139);
    public static final Color MAC_BORDER = new Color(210, 210, 215);

    public static final Font FONT_TITLE = new Font(".AppleSystemUIFont", Font.BOLD, 22);
    public static final Font FONT_BODY = new Font(".AppleSystemUIFont", Font.PLAIN, 13);
    public static final Font FONT_BOLD = new Font(".AppleSystemUIFont", Font.BOLD, 13);
    
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");

    public static JPanel createEmployeePanel() {
        JPanel basePanel = new JPanel(new BorderLayout());
        basePanel.setBackground(MAC_BG);
        basePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel empTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        empTopPanel.setBackground(MAC_BG);
        
        JLabel inputLabel = new JLabel("Enter Employee #:");
        inputLabel.setFont(FONT_BOLD);
        inputLabel.setForeground(MAC_TEXT_MAIN);
        
        JTextField empNumField = new JTextField(12);
        empNumField.setFont(FONT_BODY);
        empNumField.setPreferredSize(new Dimension(140, 32));
        empNumField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAC_BORDER, 1), new EmptyBorder(4, 8, 4, 8)));
        
        JButton searchButton = new JButton("View Profile");
        searchButton.setFont(FONT_BOLD);
        searchButton.setBackground(MAC_ACCENT);
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);
        searchButton.setPreferredSize(new Dimension(120, 32));
        
        empTopPanel.add(inputLabel);
        empTopPanel.add(empNumField);
        empTopPanel.add(searchButton);
        basePanel.add(empTopPanel, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 20));
        centerWrapper.setBackground(MAC_BG);

        JPanel profileCard = new JPanel();
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBackground(MAC_CARD_BG);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MAC_BORDER, 1), new EmptyBorder(25, 30, 25, 30)));
        profileCard.setPreferredSize(new Dimension(550, 240));
        profileCard.setVisible(false);

        JLabel cardHeader = new JLabel("Employee Profile Record");
        cardHeader.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 16));
        cardHeader.setForeground(MAC_TEXT_MAIN);

        JPanel gridDataPanel = new JPanel(new GridLayout(4, 2, 10, 14));
        gridDataPanel.setBackground(MAC_CARD_BG);

        JLabel valEmpNum = new JLabel(); valEmpNum.setFont(FONT_BODY);
        JLabel valFullName = new JLabel(); valFullName.setFont(FONT_BODY);
        JLabel valBirth = new JLabel(); valBirth.setFont(FONT_BODY);
        JLabel valRate = new JLabel(); valRate.setFont(FONT_BOLD); valRate.setForeground(MAC_ACCENT);

        gridDataPanel.add(createMacFieldLabel("Employee Number")); gridDataPanel.add(valEmpNum);
        gridDataPanel.add(createMacFieldLabel("Full Name"));       gridDataPanel.add(valFullName);
        gridDataPanel.add(createMacFieldLabel("Date of Birth"));   gridDataPanel.add(valBirth);
        gridDataPanel.add(createMacFieldLabel("Hourly Rate Base")); gridDataPanel.add(valRate);

        profileCard.add(cardHeader);
        profileCard.add(Box.createVerticalStrut(16));
        profileCard.add(gridDataPanel);
        centerWrapper.add(profileCard);
        basePanel.add(centerWrapper, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String empNum = empNumField.getText().trim();
            if (empNum.isEmpty() || !empNum.matches("\\d+")) {
                JOptionPane.showMessageDialog(basePanel, "Please specify a valid numeric ID.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String[] empData = Account.findEmployeeRecord(empNum);
            if (empData != null) {
                valEmpNum.setText(empData[0]);
                valFullName.setText(empData[2] + " " + empData[1]);
                valBirth.setText(empData[3]);
                valRate.setText("PHP " + empData[18]);
                profileCard.setVisible(true);
            } else {
                profileCard.setVisible(false);
                JOptionPane.showMessageDialog(basePanel, "No record matching ID: " + empNum, "Absent File", JOptionPane.INFORMATION_MESSAGE);
            }
            basePanel.revalidate();
            basePanel.repaint();
        });

        return basePanel;
    }

    public static JPanel createPayrollPanel() {
        JPanel basePanel = new JPanel(new BorderLayout());
        basePanel.setBackground(MAC_BG);
        basePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel payrollTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        payrollTopPanel.setBackground(MAC_BG);
        
        JTextField payrollEmpField = new JTextField(8);
        payrollEmpField.setPreferredSize(new Dimension(100, 32));
        payrollEmpField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAC_BORDER, 1), new EmptyBorder(4, 8, 4, 8)));
        
        JButton processSingleButton = new JButton("Process Single");
        processSingleButton.setBackground(MAC_ACCENT); processSingleButton.setForeground(Color.WHITE); processSingleButton.setOpaque(true); processSingleButton.setBorderPainted(false);
        processSingleButton.setPreferredSize(new Dimension(130, 32));
        
        JButton processAllButton = new JButton("Process All Batches");
        processAllButton.setBackground(Color.WHITE); processAllButton.setForeground(MAC_TEXT_MAIN); processAllButton.setBorder(BorderFactory.createLineBorder(MAC_BORDER, 1));
        processAllButton.setPreferredSize(new Dimension(160, 32));
        
        payrollTopPanel.add(new JLabel("Employee ID:")); payrollTopPanel.add(payrollEmpField);
        payrollTopPanel.add(processSingleButton); payrollTopPanel.add(processAllButton);
        basePanel.add(payrollTopPanel, BorderLayout.NORTH);

        JPanel scrollContentEngine = new JPanel();
        scrollContentEngine.setLayout(new BoxLayout(scrollContentEngine, BoxLayout.Y_AXIS));
        scrollContentEngine.setBackground(MAC_BG);

        JScrollPane scrollPane = new JScrollPane(scrollContentEngine);
        scrollPane.setBorder(null);
        basePanel.add(scrollPane, BorderLayout.CENTER);

        processSingleButton.addActionListener(e -> {
            String empNum = payrollEmpField.getText().trim();
            if (empNum.isEmpty() || !empNum.matches("\\d+")) {
                JOptionPane.showMessageDialog(basePanel, "Requires a valid corporate ID entry.", "Validation Fault", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] empData = Account.findEmployeeRecord(empNum);
            if (empData == null) {
                JOptionPane.showMessageDialog(basePanel, "No record identified.", "Missing Target", JOptionPane.ERROR_MESSAGE);
                return;
            }
            scrollContentEngine.removeAll();
            generateMacPayrollCards(empNum, scrollContentEngine);
            scrollContentEngine.revalidate(); scrollContentEngine.repaint();
        });

        processAllButton.addActionListener(e -> {
            if (Account.employeeData.isEmpty()) return;
            scrollContentEngine.removeAll();
            for (String empNum : Account.employeeData.keySet()) {
                generateMacPayrollCards(empNum, scrollContentEngine);
            }
            scrollContentEngine.revalidate(); scrollContentEngine.repaint();
        });

        return basePanel;
    }

    private static void generateMacPayrollCards(String empNum, JPanel targetContainer) {
        String[] empData = Account.findEmployeeRecord(empNum);
        if (empData == null) return;
        double hourlyRate = Double.parseDouble(empData[18]);
        String[] months = {"06", "07", "08", "09", "10", "11", "12"};

        for (String month : months) {
            targetContainer.add(buildPayCardUI(empData[0], empData[2] + " " + empData[1], month, 1, 15, hourlyRate, false));
            targetContainer.add(Box.createVerticalStrut(15));
            targetContainer.add(buildPayCardUI(empData[0], empData[2] + " " + empData[1], month, 16, 31, hourlyRate, true));
            targetContainer.add(Box.createVerticalStrut(15));
        }
    }

    private static JPanel buildPayCardUI(String empNum, String fullName, String month, int start, int end, double rate, boolean isSecondHalf) {
        double hours = getTotalHoursForPeriod(empNum, month, start, end);
        double gross = hours * rate;

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(MAC_CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(MAC_BORDER, 1), new EmptyBorder(18, 20, 18, 20)));
        card.setMaximumSize(new Dimension(680, 160));

        JLabel periodTitle = new JLabel(String.format("%s %d-%d  •  ID: %s (%s)", getMonthName(month), start, end, empNum, fullName));
        periodTitle.setFont(FONT_BOLD);

        JPanel metricsGrid = new JPanel(new GridLayout(1, 4, 10, 0));
        metricsGrid.setBackground(MAC_CARD_BG);
        metricsGrid.add(createMetricsSubBlock("Hours Worked", String.format("%.2f hrs", hours)));
        metricsGrid.add(createMetricsSubBlock("Gross Pay", String.format("PHP %.2f", gross)));

        if (isSecondHalf) {
            double firstHalfHours = getTotalHoursForPeriod(empNum, month, 1, 15);
            double monthlyGross = (firstHalfHours + hours) * rate;

            double sss = computeSSS(monthlyGross);
            double ph = computePhilHealth(monthlyGross);
            double pi = computePagIBIG(monthlyGross);
            double tax = computeIncomeTax(monthlyGross - (sss + ph + pi));
            double netSalary = gross - (sss + ph + pi + tax);

            metricsGrid.add(createMetricsSubBlock("Deductions", String.format("PHP %.2f", (sss + ph + pi + tax))));
            metricsGrid.add(createMetricsSubBlock("Net Take-Home", String.format("PHP %.2f", netSalary)));
        } else {
            metricsGrid.add(createMetricsSubBlock("Deductions", "Deferred to 2nd Half"));
            metricsGrid.add(createMetricsSubBlock("Advance Release", String.format("PHP %.2f", gross)));
        }

        card.add(periodTitle); card.add(Box.createVerticalStrut(10)); card.add(metricsGrid);
        return card;
    }

    private static double getTotalHoursForPeriod(String empNum, String month, int start, int end) {
        double total = 0;
        List<String[]> records = Account.attendanceData.get(empNum);
        if (records == null) return 0;

        for (String[] record : records) {
            String[] dateParts = record[0].split("/");
            if (dateParts.length < 2) continue;
            int dDay = Integer.parseInt(dateParts[1]);
            if (dateParts[0].equals(month) && dDay >= start && dDay <= end) {
                total += computeWorkHours(LocalTime.parse(record[1], timeFormat), LocalTime.parse(record[2], timeFormat));
            }
        }
        return total;
    }

    private static double computeWorkHours(LocalTime login, LocalTime logout) {
        LocalTime startLimit = LocalTime.of(8, 0);
        LocalTime endLimit = LocalTime.of(17, 0);
        if (login.isBefore(LocalTime.of(8, 10))) login = startLimit;
        if (logout.isAfter(endLimit)) logout = endLimit;
        if (logout.isBefore(login)) return 0;
        long mins = Duration.between(login, logout).toMinutes();
        return (mins > 60) ? (mins - 60) / 60.0 : mins / 60.0;
    }

    // Calculation Equations Block
    private static double computeSSS(double gross) {
        double[] limits = {0, 3250, 3750, 4250, 4750, 5250, 5750, 6250, 6750, 7250, 7750, 8250, 8750, 9250, 9750, 10250, 10750, 11250, 11750, 12250, 12750, 13250, 13750, 14250, 14750, 15250, 15750, 16250, 16750, 17250, 17750, 18250, 18750, 19250, 19750, 20250, 20750, 21250, 21750, 22250, 22750, 23250, 23750, 24250, 24750};
        double[] amounts = {135.0, 157.5, 180.0, 202.5, 225.0, 247.5, 270.0, 292.5, 315.0, 337.5, 360.0, 382.5, 405.0, 427.5, 450.0, 472.5, 495.0, 517.5, 540.0, 562.5, 585.0, 607.5, 630.0, 652.5, 675.0, 697.5, 720.0, 742.5, 765.0, 787.5, 810.0, 832.5, 855.0, 877.5, 900.0, 922.5, 945.0, 967.5, 990.0, 1012.5, 1035.0, 1057.5, 1080.0, 1102.5, 1125.0};
        for (int i = limits.length - 1; i >= 0; i--) { if (gross >= limits[i]) return amounts[i]; }
        return 135.0;
    }

    private static double computePhilHealth(double gross) {
        if (gross <= 10000) return 150.0;
        if (gross >= 60000) return 900.0;
        return (gross * 0.03) / 2;
    }

    private static double computePagIBIG(double gross) {
        return Math.min(gross * ((gross <= 1500) ? 0.01 : 0.02), 100.0);
    }

    private static double computeIncomeTax(double taxable) {
        if (taxable <= 20832) return 0;
        if (taxable <= 33332) return (taxable - 20833) * 0.20;
        if (taxable <= 66666) return 2500 + (taxable - 33333) * 0.25;
        if (taxable <= 166666) return 10833 + (taxable - 66667) * 0.30;
        if (taxable <= 666666) return 40833.33 + (taxable - 166667) * 0.32;
        return 200833.33 + (taxable - 666667) * 0.35;
    }

    private static String getMonthName(String m) {
        String[] n = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return n[Integer.parseInt(m)];
    }

    private static JLabel createMacFieldLabel(String title) {
        JLabel lbl = new JLabel(title); lbl.setFont(FONT_BOLD); lbl.setForeground(MAC_TEXT_MUTED);
        return lbl;
    }

    private static JPanel createMetricsSubBlock(String title, String value) {
        JPanel block = new JPanel(new GridLayout(2, 1)); block.setBackground(MAC_CARD_BG);
        JLabel lbl = new JLabel(title); lbl.setFont(FONT_BOLD); lbl.setForeground(MAC_TEXT_MUTED);
        JLabel val = new JLabel(value); val.setFont(FONT_BODY); val.setForeground(MAC_TEXT_MAIN);
        block.add(lbl); block.add(val);
        return block;
    }
}