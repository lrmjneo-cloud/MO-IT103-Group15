@echo off
REM MotorPH Payroll System - Windows Startup Script

REM Check if build directory exists
if not exist build\classes (
    echo Building the application...
    javac -d build\classes src\motorph\payroll\*.java
    if errorlevel 1 (
        echo Build failed!
        pause
        exit /b 1
    )
)

echo Starting MotorPH Payroll System...
java -cp build\classes motorph.payroll.MainMotorPH

if errorlevel 1 (
    echo Application failed to start!
    pause
)
