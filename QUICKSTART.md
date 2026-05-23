# Quick Start Guide - MotorPH Payroll System

## Fastest Way to Run

### Windows
Simply double-click `run.bat` in the project folder.

```bash
run.bat
```

### macOS / Linux
```bash
chmod +x run.sh
./run.sh
```

## Manual Startup

### Windows (Command Prompt)
```cmd
cd "path\to\MO-IT103-Group15"
javac -d build/classes src/motorph/payroll/*.java
java -cp build/classes motorph.payroll.MainMotorPH
```

### macOS / Linux (Terminal)
```bash
cd path/to/MO-IT103-Group15
javac -d build/classes src/motorph/payroll/*.java
java -cp build/classes motorph.payroll.MainMotorPH
```

## Default Credentials

```
Payroll Staff:
  Username: payroll_staff
  Password: 12345

Employee:
  Username: employee
  Password: 12345
```

## Requirements

- Java 11 or higher (recommend Java 25)
- CSV data files in `resources/` folder:
  - `EmployeeData.csv`
  - `AttendanceRecord.csv`

## Troubleshooting

**"Java command not found"**
- Install Java: https://www.java.com/download

**"Employee data not loading"**
- Check `resources/` folder has the CSV files
- Verify file names are correct

**"GUI not appearing"**
- Application is running in the background
- Check taskbar/dock for window

For detailed documentation, see `README.md`
