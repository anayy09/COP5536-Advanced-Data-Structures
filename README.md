# Flying Broomstick Management System
## COP5536 Advanced Data Structures - Spring 2025

### Student Information
**Name:** Anay Sinhal  
**UFID:** 68789243
**UF Email:** sinhal.anay@ufl.edu

## Project Overview
This project implements a Flying Broomstick Management System for the Office of Transportation, Ministry of Magic. The system manages license plates for flying broomsticks using a Red-Black Tree as the underlying data structure.

### Key Features
- Registration of customized and random license plates
- Removal of license plates from the system
- Lookup operations for existing plates
- Finding lexicographically previous and next plates
- Range searches for plates between specified bounds
- Revenue calculations for standard and customized plates

## How to Use This Project

### Compilation Instructions
1. Make sure you have Java JDK installed on your system
2. Open a terminal or command prompt
3. Navigate to the project directory
4. Compile using the included Makefile:
   ```
   make
   ```
   For Windows:
   ```
   make windows
   ```

### Running the Program
The program accepts an input file containing commands to execute:
```
# On Linux/Mac
./plateMgmt input.txt

# On Windows
plateMgmt.bat input.txt
```

### Input File Format
The input file should contain one command per line, using the following format:
```
addLicence(ABCD)     // Add custom license plate
addLicence()         // Add random license plate
dropLicence(ABCD)    // Remove license plate
lookupLicence(ABCD)  // Check if plate exists
lookupPrev(ABCD)     // Find previous plate
lookupNext(ABCD)     // Find next plate
lookupRange(AAA1,ZZZ9) // Find plates in range
revenue()            // Calculate revenue
quit()               // Exit program
```

### Output
The program will generate an output file named `input.txt_output.txt` containing the results of each command.

## Technical Implementation

### Data Structure
The system uses a Red-Black Tree implemented from scratch (without built-in libraries) to efficiently manage license plate data with O(log n) time complexity for most operations.

A Red-Black Tree is a self-balancing binary search tree with the following properties:
1. Every node is either red or black
2. The root is black
3. Every leaf (NIL) is black
4. If a node is red, then both its children are black
5. All simple paths from a node to descendant leaves contain the same number of black nodes

This data structure was chosen for its efficient search, insert, and delete operations, which are all O(log n) in the worst case.

### Project Structure

#### Files
- `plateMgmt.java`: Main class implementing the license plate management system
- `RBTree.java`: Red-Black Tree implementation for storing and managing license plates
- `Makefile`: For compiling the project and creating the executable
- `test.txt`: Sample test cases for verifying functionality

#### Class Diagram
```
+-------------------------+         +------------------------+
|       plateMgmt         |         |        RBTree          |
+-------------------------+         +------------------------+
| -licenseTree: RBTree    |<------->| -root: Node            |
| -standardPlateCount: int|         | -RED/BLACK: boolean    |
| -customPlateCount: int  |         +------------------------+
| -outputWriter: Writer   |         | +insert(key): boolean  |
+-------------------------+         | +delete(key): boolean  |
| +addLicence(plateNum)   |         | +search(key): boolean  |
| +addLicence()           |         | +predecessor(key): str |
| +dropLicence(plateNum)  |         | +successor(key): str   |
| +lookupLicence(plateNum)|         | +range(lo, hi): str[]  |
| +lookupPrev(plateNum)   |         +------------------------+
| +lookupNext(plateNum)   |                     ^
| +lookupRange(lo, hi)    |                     |
| +revenue()              |                     |
+-------------------------+                     |
                                      +------------------+
                                      |       Node       |
                                      +------------------+
                                      | -key: String     |
                                      | -color: boolean  |
                                      | -left: Node      |
                                      | -right: Node     |
                                      | -parent: Node    |
                                      +------------------+
```

## Function Prototypes and Explanations

### plateMgmt Class

#### Main Operations
```java
// Register new customized license plate
public void addLicence(String plateNum)

// Generate and register a random license plate
public void addRandomLicence()

// Remove license plate from the system
public void dropLicence(String plateNum)

// Check if license plate exists
public void lookupLicence(String plateNum)

// Find lexicographically previous plate
public void lookupPrev(String plateNum)

// Find lexicographically next plate
public void lookupNext(String plateNum)

// Find all plates in a given range
public void lookupRange(String lo, String hi)

// Calculate and report annual revenue
public void revenue()
```

#### Utility Functions
```java
// Initialize output writer
public void initOutput(String outputFile)

// Close output writer
public void closeOutput()

// Process a command from input
public void processCommand(String command)

// Entry point for the program
public static void main(String[] args)
```

### RBTree Class

#### Public Interface
```java
// Insert a new license plate into the tree
public boolean insert(String key)

// Delete a license plate from the tree
public boolean delete(String key)

// Search for a license plate
public boolean search(String key)

// Find the predecessor of a license plate
public String predecessor(String key)

// Find the successor of a license plate
public String successor(String key)

// Find all license plates in a given range
public String[] range(String lo, String hi)
```

#### Tree Operations
```java
// Fix Red-Black Tree properties after insertion
private void fixAfterInsertion(Node node)

// Fix Red-Black Tree properties after deletion
private void fixAfterDeletion(Node x)

// Left rotate operation
private void rotateLeft(Node x)

// Right rotate operation
private void rotateRight(Node x)
```

## Implementation Details

### License Plate Format
Each license plate consists of 4 characters, where each character can be a number (0-9) or a capital letter (A-Z).

### Fee Structure
- Standard license plates: 4 Galleons annually
- Customized license plates: 7 Galleons annually (4 + 3 extra)

### File I/O
The system reads commands from an input file and writes results to an output file with the name format: `inputFileName output.txt`.

### Red-Black Tree Implementation
The Red-Black Tree implementation handles all the necessary balancing operations to maintain the tree's properties after insertions and deletions. Key operations include:

1. **Insertion**: Standard BST insertion followed by RB-Tree property fixes
2. **Deletion**: Standard BST deletion followed by RB-Tree property fixes
3. **Rotations**: Left and right rotations to maintain balance
4. **Property Fixes**: Recoloring nodes and restructuring to maintain RB-Tree properties

## Algorithm Analysis

### Time Complexity
- Search, Insert, Delete: O(log n) where n is the number of license plates
- Range search: O(log n + k) where k is the number of plates in the range

### Space Complexity
- O(n) for storing n license plates in the Red-Black Tree

## Conclusion
The Flying Broomstick Management System provides an efficient solution for managing license plates using a Red-Black Tree data structure. The implementation meets all the requirements specified in the assignment, including proper handling of customized and random license plates, various lookup operations, and revenue calculations.

## References
1. Cormen, T. H., Leiserson, C. E., Rivest, R. L., & Stein, C. (2009). Introduction to Algorithms (3rd ed.). MIT Press.
2. Sedgewick, R., & Wayne, K. (2011). Algorithms (4th ed.). Addison-Wesley.
3. Goodrich, M. T., Tamassia, R., & Goldwasser, M. H. (2014). Data Structures and Algorithms in Java (6th ed.). Wiley.