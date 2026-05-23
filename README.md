g# MotorPH Payroll System

## Overview
The MotorPH Payroll System is a Java-based desktop payroll application that processes employee attendance and salary records. It features a user-friendly interface with role-based access for payroll staff and employees.

## Team Details
- **Group:** IT103 - Group 15
- **Developer:** Martinn Jhon Neo

## Features

### System Capabilities
- **Login System** - Role-based access control for different user types
- **Employee Portal** - View employee information and profile details
- **Payroll Management** - Process individual or batch payroll calculations
- **Automated Calculations** - Calculates total hours worked and monthly payroll
- **Government Deductions** - Automatically computes:
  - SSS (Social Security System)
  - PhilHealth
  - Pag-IBIG (Home Development Mutual Fund)
  - Withholding Tax (BIR)
- **Dual Cutoff Periods** - Processes salary for 1-15 and 16-end of month periods

### User Roles

#### Payroll Staff
- Process payroll for a single employee
- Process payroll for all employees in batch
- View detailed payroll calculations and deductions

#### Employee
- View personal employee profile
- Access basic employment information
- View employment details (ID, name, date of birth, hourly rate)

## System Requirements

### Minimum Requirements
- **Java:** Version 11 or higher (Tested with Java 25)
- **Memory:** 512 MB RAM minimum
- **Storage:** 50 MB free space
- **Operating System:** Windows, macOS, or Linux with Java installed

### Data Files
The application requires two CSV data files in the `resources/` folder:
- `EmployeeData.csv` - Employee information and payroll details
- `AttendanceRecord.csv` - Employee attendance and time tracking records

## Installation & Setup

### Option 1: Using Gradle (Recommended)

1. **Clone/Download the Project**
   ```bash
   cd MO-IT103-Group15
   ```

2. **Build the Project**
   ```bash
   # On Windows
   gradlew build
   
   # On macOS/Linux
   ./gradlew build
   ```

3. **Run the Application**
   ```bash
   # On Windows
   gradlew runApp
   
   # On macOS/Linux
   ./gradlew runApp
   ```

### Option 2: Using Java Command Line

1. **Compile the Project**
   ```bash
   javac -d build/classes src/motorph/payroll/*.java
   ```

2. **Run the Application**
   ```bash
   java -cp build/classes motorph.payroll.MainMotorPH
   ```

### Option 3: Using an IDE

#### NetBeans
1. Open the project directory
2. Right-click on the project and select "Run Project" (F6)
3. The application will compile and launch automatically

#### IntelliJ IDEA
1. Import the project as a Java project
2. Configure the project structure (Project Settings > SDK)
3. Right-click `MainMotorPH.java` and select "Run"

#### Eclipse
1. Create a new Java project
2. Import the source files from `src/` folder
3. Create a new Run Configuration with main class as `motorph.payroll.MainMotorPH`
4. Run the configuration

#### VS Code
1. Install the "Extension Pack for Java" extension
2. Open the folder containing the project
3. Right-click `MainMotorPH.java` and select "Run"

## Default Login Credentials

### Payroll Staff Account
- **Username:** `payroll_staff`
- **Password:** `12345`

### Employee Account
- **Username:** `employee`
- **Password:** `12345`

## File Structure

```
MO-IT103-Group15/
├── build.gradle                    # Gradle build configuration
├── gradlew                         # Gradle wrapper script (macOS/Linux)
├── gradlew.bat                     # Gradle wrapper script (Windows)
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   └── motorph/payroll/
│       ├── MainMotorPH.java        # Application entry point & UI setup
│       ├── PayrollService.java     # Business logic & UI components
│       └── Account.java            # Data management & file I/O
├── resources/
│   ├── EmployeeData.csv            # Employee database
│   └── AttendanceRecord.csv        # Attendance logs
└── README.md                       # This file
```

## Data File Formats

### EmployeeData.csv
```
EmpID,LastName,FirstName,BirthDate,PhoneNumber,Address,...,[HourlyRate]
```

### AttendanceRecord.csv
```
EmpID,AttendanceDate,Status,Date,TimeIn,TimeOut
```

## Payroll Calculation Details

### Work Hours Calculation
- **Work Day:** 8:00 AM to 5:00 PM (8 hours standard)
- **Grace Period:** 10 minutes (if login before 8:10 AM, counted as 8:00 AM)
- **Lunch Break:** 1 hour unpaid (automatically deducted)
- **Formula:** Total minutes worked ÷ 60 = Hours

### Deduction Rules
- **Deductions Applied:** Only on the 2nd cutoff period (16-31)
- **First Cutoff (1-15):** Advance release of 100% gross pay
- **Second Cutoff (16-31):** Net salary after deductions

### Government Deduction Rates (2024)
- **SSS:** Based on salary brackets (135 - 1,125 PHP depending on gross salary)
- **PhilHealth:** 3% of gross salary (50% from employee, 50% from employer) - minimum 150 PHP
- **Pag-IBIG:** 1-2% of gross salary (capped at 100 PHP)
- **Withholding Tax:** Progressive rates based on taxable income

## Troubleshooting

### "Error Loading Employees" or "Error Loading Attendance"
- **Issue:** Data files not found or incorrect path
- **Solution:** Ensure `EmployeeData.csv` and `AttendanceRecord.csv` are in the `resources/` folder

### Application Won't Start
- **Issue:** Java not installed or not in PATH
- **Solution:** Install Java JDK 11+ and ensure JAVA_HOME environment variable is set

### Login Fails
- **Issue:** Invalid credentials
- **Solution:** Use the default credentials listed above (username and password are case-sensitive)

### UI Text Not Visible
- **Issue:** Display scaling or font rendering issues
- **Solution:** The application supports system-level font settings; adjust in your OS display settings

### Cannot Find Main Class Error
- **Issue:** Class not found during compilation
- **Solution:** Ensure source files are in `src/motorph/payroll/` directory and compile from project root

## Building Standalone JAR

To create an executable JAR file:

```bash
# Using Gradle
gradlew build

# JAR will be located at: build/libs/MO-IT103-Group15-1.0.0.jar

# Run the JAR
java -jar build/libs/MO-IT103-Group15-1.0.0.jar
```

## Development Notes

### Technology Stack
- **Language:** Java 25
- **UI Framework:** Java Swing
- **Build System:** Gradle 8.5
- **Data Format:** CSV (Comma-Separated Values)

### Key Classes

#### MainMotorPH.java
- Entry point for the application
- Manages login screen and dashboard panel switching
- Handles UI frame setup and wave background rendering

#### PayrollService.java
- Creates employee and payroll panel UI
- Handles business logic for payroll calculations
- Manages deduction calculations (SSS, PhilHealth, Pag-IBIG, Tax)

#### Account.java
- Loads and manages employee and attendance data from CSV files
- Authenticates user credentials
- Provides employee record lookup functionality

## Future Enhancements
- Database integration (MySQL/PostgreSQL) instead of CSV files
- Export payroll reports to PDF/Excel
- Email notifications for payroll processing
- User permission levels and advanced role management
- Data backup and recovery features
- Month and year selection for flexible payroll processing

## Support & Issues

For issues or questions:
1. Verify data files are in correct format and location
2. Check Java version compatibility (11+)
3. Review error messages in console output
4. Ensure proper folder structure is maintained

## License

This project was developed as part of IT103 coursework and is provided as-is.

## Project Link
[Google Sheets Project Plan](https://docs.google.com/spreadsheets/d/1JXhp_raVGFsWsSFoJpqA9-lMbULKNWR81NZtGzjDAu8/edit?gid=2134013708#gid=2134013708)

