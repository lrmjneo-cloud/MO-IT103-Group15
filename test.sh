#!/bin/bash
# MotorPH Quick Test Script

echo "=== MotorPH Payroll System - Quick Test ==="
echo ""
echo "Checking Java installation..."
java -version 2>&1 | head -1

echo ""
echo "Checking compiled files..."
FILE_COUNT=$(find build/classes/motorph/payroll -name "*.class" 2>/dev/null | wc -l)
echo "Found $FILE_COUNT compiled class files"

if [ "$FILE_COUNT" -lt 6 ]; then
    echo "ERROR: Missing class files. Recompiling..."
    mkdir -p build/classes
    javac -d build/classes src/motorph/payroll/*.java
    if [ $? -ne 0 ]; then
        echo "FAILED: Compilation error"
        exit 1
    fi
fi

echo ""
echo "=== Launching MotorPH Payroll System ==="
echo "Default Credentials:"
echo "  Payroll Staff: payroll_staff / 12345"
echo "  Employee: employee / 12345"
echo ""
echo "Starting application..."
java -cp build/classes motorph.payroll.MainMotorPH

exit $?
