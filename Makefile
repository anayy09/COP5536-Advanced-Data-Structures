# Makefile for Flying Broomstick Management System

# Java compiler
JC = javac

# Source directory
SRC_DIR = src

# Main class
MAIN_CLASS = plateMgmt

# Default target - creates executable named plateMgmt
all: clean
	# Compile to the current directory instead of src
	$(JC) -d . $(SRC_DIR)/*.java
	echo "#!/bin/sh" > plateMgmt
	echo "java $(MAIN_CLASS) \$$@" >> plateMgmt
	chmod +x plateMgmt

# Clean generated files
clean:
	rm -f *.class
	rm -f plateMgmt
	# Don't try to remove files from src directory
	# rm -f $(SRC_DIR)/*.class

.PHONY: all clean