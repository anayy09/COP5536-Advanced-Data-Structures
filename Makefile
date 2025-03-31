# Makefile for Flying Broomstick Management System

# Java compiler
JC = javac

# Main class
MAIN_CLASS = plateMgmt

# Default target - creates executable named plateMgmt
all: check-java clean
	# Compile to the current directory
	$(JC) *.java
	echo "#!/bin/sh" > plateMgmt
	echo "java $(MAIN_CLASS) \$$@" >> plateMgmt
	chmod +x plateMgmt

# Check if Java is installed
check-java:
	@command -v javac >/dev/null 2>&1 || { \
		echo "Error: Java Development Kit (JDK) not found."; \
		echo "Please ensure Java is installed and in your PATH."; \
		echo "On WSL/Linux: sudo apt update && sudo apt install default-jdk"; \
		echo "On Windows: Use cmd.exe and run your commands there instead of WSL."; \
		exit 1; \
	}

# Clean generated files
clean:
	rm -f *.class
	rm -f plateMgmt

# Windows-specific makefile target
windows:
	@echo "Compiling for Windows environment..."
	javac *.java
	echo @echo off > plateMgmt.bat
	echo java $(MAIN_CLASS) %%* >> plateMgmt.bat

.PHONY: all clean check-java windows