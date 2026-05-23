# Build Instructions

## Using Pre-built Scripts (Recommended)

### Windows
```bash
run.bat
```

### macOS/Linux
```bash
chmod +x run.sh
./run.sh
```

These scripts will automatically compile and run the application.

---

## Using Gradle

### First Time Setup (One-time)
If you want to use Gradle wrapper directly, you need to download the wrapper JAR first:

```bash
# Option 1: Using Java directly (No Gradle needed)
javac -d build/classes src/motorph/payroll/*.java
java -cp build/classes motorph.payroll.MainMotorPH
```

### Or Install Gradle Manually
```bash
# If you have Gradle installed globally
gradle build
gradle runApp
```

[Download Gradle](https://gradle.org/install/)

---

## Using Java Directly

### Compile
```bash
javac -d build/classes src/motorph/payroll/*.java
```

### Run
```bash
java -cp build/classes motorph.payroll.MainMotorPH
```

---

## Using IDEs

### NetBeans
1. Open the project folder
2. Right-click the project → Run Project (F6)

### IntelliJ IDEA
1. Import project as Java project
2. Configure project SDK to Java 25+
3. Right-click MainMotorPH.java → Run

### Eclipse
1. Create new Java project
2. Import sources from `src/` folder
3. Create Run Configuration with main class: `motorph.payroll.MainMotorPH`
4. Run Configuration

### VS Code
1. Install "Extension Pack for Java"
2. Open project folder
3. Right-click MainMotorPH.java → Run

---

## Creating Executable JAR

### Using Java Tools
```bash
# Compile
javac -d build/classes src/motorph/payroll/*.java

# Create JAR
jar cvfe build/MotorPH.jar motorph.payroll.MainMotorPH -C build/classes .

# Run JAR
java -jar build/MotorPH.jar
```

---

## Troubleshooting Build Issues

### Issue: "javac: command not found"
**Solution:** Install Java Development Kit (JDK) 11+
- https://www.oracle.com/java/technologies/downloads/

### Issue: "Cannot find symbol" errors
**Solution:** Ensure source files are in correct location:
```
src/motorph/payroll/
├── Account.java
├── MainMotorPH.java
└── PayrollService.java
```

### Issue: "EmployeeData.csv not found"
**Solution:** Ensure CSV files are in `resources/` folder:
```
resources/
├── EmployeeData.csv
└── AttendanceRecord.csv
```

### Issue: Gradle wrapper JAR missing
**Solution:** Use Java directly instead:
```bash
javac -d build/classes src/motorph/payroll/*.java
java -cp build/classes motorph.payroll.MainMotorPH
```

---

## System Requirements

- **Java:** Version 11+ (JDK, not just JRE)
- **RAM:** 512 MB minimum
- **Disk:** 50 MB free space
- **OS:** Windows, macOS, or Linux

## Verify Installation

```bash
# Check Java version
java -version
javac -version

# Both should show version 11 or higher
```
