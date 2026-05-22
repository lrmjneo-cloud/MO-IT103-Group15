package motorph.payroll.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    public static String empFile = "resources/EmployeeData.csv";
    public static String attendanceFile = "resources/AttendanceRecord.csv";
    public static Map<String, List<String>> employeeData = new HashMap<>();
    public static Map<String, List<String[]>> attendanceData = new HashMap<>();

    public static void initData() {
        employeeData.clear();
        attendanceData.clear();
        loadEmployeeData();
        loadAttendanceData();
    }

    private static void loadEmployeeData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(empFile))) {
            reader.readLine(); // Skip header row
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = splitCsvLine(line);
                List<String> details = new ArrayList<>();
                for (String p : parts) details.add(p.trim());
                employeeData.put(parts[0].trim(), details);
            }
        } catch (IOException e) { 
            System.out.println("Error Loading Employees: " + e.getMessage()); 
        }
    }

    private static void loadAttendanceData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(attendanceFile))) {
            reader.readLine(); // Skip header row
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = splitCsvLine(line);
                String id = p[0].trim();
                if (employeeData.containsKey(id)) {
                    attendanceData.computeIfAbsent(id, k -> new ArrayList<>())
                                  .add(new String[] {p[3].trim(), p[4].trim(), p[5].trim()});
                }
            }
        } catch (IOException e) { 
            System.out.println("Error Loading Attendance: " + e.getMessage()); 
        }
    }

    private static String[] splitCsvLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    public static String[] findEmployeeRecord(String empNum) {
        List<String> data = employeeData.get(empNum);
        return (data == null) ? null : data.toArray(new String[0]);
    }

    public static boolean verifyCredentials(String username, String password) {
        return ("employee".equals(username) || "payroll_staff".equals(username)) && "12345".equals(password);
    }
}