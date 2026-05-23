#!/bin/bash
# MotorPH Payroll System - Linux/macOS Startup Script

# Check if build directory exists and compile if needed
if [ ! -d "build/classes" ]; then
    echo "Building the application..."
    mkdir -p build/classes
    javac -d build/classes src/motorph/payroll/*.java
    if [ $? -ne 0 ]; then
        echo "Build failed!"
        exit 1
    fi
fi

echo "Starting MotorPH Payroll System..."
java -cp build/classes motorph.payroll.MainMotorPH

if [ $? -ne 0 ]; then
    echo "Application failed to start!"
    exit 1
fi
