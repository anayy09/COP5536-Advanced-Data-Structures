# License Plate Management System

## Project Overview
This project implements a comprehensive License Plate Management System for vehicle registration and tracking. The system efficiently manages license plates using a custom-built Red-Black Tree data structure, providing fast operations for registration, lookup, and revenue management. This project is part of COP5536 **Advanced Data Structures** (Spring 2025)

## Tech Stack
- **Language**: Java (JDK 8+)
- **Data Structure**: Red-Black Tree (custom implementation)
- **Build System**: GNU Make
- **I/O**: File-based input/output processing
- **Collections**: HashSet for tracking custom plates
- **Testing**: Command-line based testing with sample files

## Core Features

### 🔧 License Plate Management
- **Custom Plate Registration**: Register user-specified 4-character license plates
- **Random Plate Generation**: Automatically generate unique random license plates
- **Plate Removal**: Remove existing license plates from the system
- **Duplicate Prevention**: Ensures no duplicate plates are registered

### 🔍 Advanced Search Operations
- **Existence Lookup**: Check if a specific license plate exists
- **Predecessor Search**: Find the lexicographically previous plate
- **Successor Search**: Find the lexicographically next plate  
- **Range Queries**: Retrieve all plates within a specified range (inclusive)

### 💰 Revenue Management
- **Dual Fee Structure**: Standard plates (4 Galleons) and custom plates (7 Galleons)
- **Real-time Revenue Calculation**: Track and calculate annual revenue
- **Custom Plate Tracking**: Accurate differentiation between standard and custom plates

### 📊 Performance Characteristics
- **O(log n)** time complexity for insert, delete, and search operations
- **O(log n + k)** time complexity for range queries (k = result size)
- **O(n)** space complexity for storing n license plates
- **Self-balancing** tree structure ensures optimal performance

## Quick Start Guide

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- GNU Make (for compilation)
- Text editor for creating input files

### Compilation & Execution

#### For Linux/Mac/WSL:
```bash
# Compile the project
make

# Run with input file
./plateMgmt test.txt
```

#### For Windows:
```batch
# Compile the project
make windows

# Run with input file
plateMgmt.bat test.txt
```

### Input Commands Format
Create a text file with commands (one per line):

```
addLicence(ABCD)     # Register custom license plate "ABCD"
addLicence()         # Generate and register random license plate
dropLicence(ABCD)    # Remove license plate "ABCD"
lookupLicence(ABCD)  # Check if plate "ABCD" exists
lookupPrev(ABCD)     # Find plate before "ABCD" lexicographically
lookupNext(ABCD)     # Find plate after "ABCD" lexicographically
lookupRange(AAA1,ZZZ9) # Find all plates between "AAA1" and "ZZZ9"
revenue()            # Calculate total annual revenue
quit()               # Exit the program
```

### Sample Test File
The included `test.txt` demonstrates all system features:
- Custom plate registration with duplicates handling
- Plate removal from existing and non-existing plates
- Various lookup operations
- Range queries and revenue calculation

## System Architecture

### Core Components

#### 1. plateMgmt.java (Main Controller)
- **Purpose**: Primary interface for the license plate management system
- **Key Responsibilities**:
  - Command parsing and execution
  - File I/O operations
  - Revenue tracking and calculation
  - Custom vs. standard plate differentiation
- **Data Management**: Uses HashSet for tracking custom plates to ensure accurate revenue calculation

#### 2. RBTree.java (Data Structure Engine)
- **Purpose**: Custom Red-Black Tree implementation optimized for string keys
- **Key Features**:
  - Self-balancing binary search tree
  - Maintains Red-Black Tree properties automatically
  - Supports efficient range queries
  - No external library dependencies

### Red-Black Tree Implementation Details

#### Tree Properties Maintained:
1. **Node Coloring**: Every node is either RED or BLACK
2. **Root Property**: Root is always BLACK
3. **Leaf Property**: All NIL nodes are BLACK
4. **Red Property**: RED nodes have only BLACK children
5. **Black Height**: All paths from root to leaves have equal BLACK node count

#### Key Algorithms:
- **Insertion**: Standard BST insert + RB-Tree rebalancing
- **Deletion**: Standard BST delete + RB-Tree rebalancing  
- **Rotations**: Left and right rotations for tree balancing
- **Fixup Operations**: Color adjustments and structural changes

### License Plate Specifications
- **Format**: Exactly 4 characters
- **Character Set**: 0-9, A-Z (36 possible characters per position)
- **Total Combinations**: 36⁴ = 1,679,616 possible plates
- **Storage**: Lexicographic ordering in the Red-Black Tree

## API Reference

### plateMgmt Class Methods

#### Core Operations
```java
// License plate registration and management
public void addLicence(String plateNum)           // Register custom plate
public void addRandomLicence()                    // Generate random plate
public void dropLicence(String plateNum)          // Remove existing plate

// Search and lookup operations  
public void lookupLicence(String plateNum)        // Check existence
public void lookupPrev(String plateNum)           // Find predecessor
public void lookupNext(String plateNum)           // Find successor
public void lookupRange(String lo, String hi)     // Range query

// Business operations
public void revenue()                              // Calculate revenue
public void processCommand(String command)        // Parse input commands
```

#### System Management
```java
public void initOutput(String outputFile)         // Initialize output writer
public void closeOutput()                         // Close output writer
public static void main(String[] args)            // Program entry point
```

### RBTree Class Methods

#### Public Interface
```java
public boolean insert(String key)                 // Insert new key
public boolean delete(String key)                 // Delete existing key
public boolean search(String key)                 // Search for key
public String predecessor(String key)             // Find predecessor
public String successor(String key)               // Find successor
public String[] range(String lo, String hi)       // Range search
public boolean isEmpty()                          // Check if empty
```

#### Internal Tree Operations
```java
private void fixAfterInsertion(Node node)         // Rebalance after insert
private void fixAfterDeletion(Node node)          // Rebalance after delete
private void rotateLeft(Node x)                   // Left rotation
private void rotateRight(Node x)                  // Right rotation
private Node findNode(String key)                 // Locate node by key
private Node findMin(Node node)                   // Find minimum in subtree
private Node findMax(Node node)                   // Find maximum in subtree
```

## Revenue Model

### Fee Structure
- **Standard Plates**: 4 Galleons annually
  - Generated via `addLicence()` (no parameters)
  - System generates random 4-character combination
- **Custom Plates**: 7 Galleons annually (4 + 3 premium)
  - Registered via `addLicence(plateNum)`
  - User specifies exact 4-character plate number

### Revenue Calculation
```
Total Annual Revenue = (Standard Plates × 4) + (Custom Plates × 7)
```

The system accurately tracks plate types using a HashSet to distinguish between standard and custom plates, ensuring precise revenue calculations.

## Output Format

### File Naming
- Input: `inputFileName.txt`
- Output: `inputFileName.txt_output.txt`

### Sample Output Messages
```
1111 registered successfully.
Failed to register 3333: already exists.
4444 removed successfully.
Failed to remove 1234: does not exist.
2222 exists.
1234 does not exist.
2222's prev is 1111.
3333's next is AAAA.
Plate numbers between 1234 and 3333: 2222, 3333.
Current annual revenue is 35 Galleons.
```

## Performance Analysis

### Time Complexity
| Operation | Red-Black Tree | Justification |
|-----------|---------------|---------------|
| Insert | O(log n) | Tree height is O(log n) due to balancing |
| Delete | O(log n) | Tree height is O(log n) due to balancing |
| Search | O(log n) | Tree height is O(log n) due to balancing |
| Predecessor/Successor | O(log n) | Tree traversal bounded by height |
| Range Query | O(log n + k) | O(log n) to find start + O(k) for k results |

### Space Complexity
- **Tree Storage**: O(n) for n license plates
- **Additional Tracking**: O(c) for c custom plates in HashSet
- **Total Space**: O(n) where n is total number of plates

### Scalability Analysis
The Red-Black Tree provides excellent scalability characteristics:
- Handles up to 1.67M possible license plates efficiently
- Guaranteed logarithmic performance even with worst-case input patterns
- Memory usage scales linearly with number of registered plates

## Project Files

```
├── plateMgmt.java          # Main system controller
├── RBTree.java             # Red-Black Tree implementation  
├── Makefile                # Build configuration
├── test.txt                # Sample test cases
├── README.md               # Project documentation
└── Project_Report.pdf      # Detailed technical report
```

## Testing & Validation

### Included Test Cases
The `test.txt` file provides comprehensive testing covering:
- ✅ Custom plate registration (valid and duplicate)
- ✅ Random plate generation  
- ✅ Plate removal (existing and non-existing)
- ✅ Existence lookups
- ✅ Predecessor/successor queries
- ✅ Range searches
- ✅ Revenue calculations

### Testing Your Own Data
Create custom test files following the command format. The system handles:
- Empty inputs gracefully
- Invalid plate formats
- Edge cases for range queries
- Large datasets efficiently

## Educational Value

This project demonstrates mastery of:
- **Advanced Data Structures**: Red-Black Tree implementation from scratch
- **Algorithm Design**: Self-balancing tree algorithms and rotations
- **Software Engineering**: Clean code architecture and separation of concerns
- **File Processing**: Robust input/output handling
- **Problem Solving**: Real-world application of theoretical concepts

## References & Resources

1. **Cormen, T. H., et al.** (2009). *Introduction to Algorithms (3rd ed.)*. MIT Press.
   - Chapters 12-13: Binary Search Trees and Red-Black Trees
2. **Sedgewick, R., & Wayne, K.** (2011). *Algorithms (4th ed.)*. Addison-Wesley.
   - Chapter 3.3: Balanced Search Trees  
3. **Goodrich, M. T., et al.** (2014). *Data Structures and Algorithms in Java (6th ed.)*. Wiley.
   - Chapter 11: Search Trees

---

*Developed as part of COP5536 Advanced Data Structures coursework, demonstrating practical application of theoretical computer science concepts in a real-world scenario.*