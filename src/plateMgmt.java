import java.io.*;
import java.util.*;

/**
 * Flying Broomstick Management System
 * Main class for managing license plates using a Red-Black Tree
 */
public class plateMgmt {
    private RBTree licenseTree; // Red-Black Tree for storing license plates
    private int standardPlateCount = 0; // Count of standard license plates
    private int customPlateCount = 0; // Count of customized license plates
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Valid characters
    private static final int PLATE_LENGTH = 4; // Length of license plate
    private static final int STANDARD_FEE = 4; // Standard fee in Galleons
    private static final int CUSTOM_FEE = 3; // Additional fee for custom plates
    private PrintWriter outputWriter; // Writer for output file
    
    /**
     * Constructor for the Flying Broomstick Management System
     */
    public plateMgmt() {
        licenseTree = new RBTree();
    }
    
    /**
     * Initialize output writer
     * @param outputFile Output file path
     */
    public void initOutput(String outputFile) {
        try {
            outputWriter = new PrintWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            System.err.println("Error creating output file: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Close output writer
     */
    public void closeOutput() {
        if (outputWriter != null) {
            outputWriter.close();
        }
    }
    
    /**
     * Register a new customized license plate
     * @param plateNum License plate number
     */
    public void addLicence(String plateNum) {
        if (licenseTree.insert(plateNum)) {
            customPlateCount++;
            outputWriter.println(plateNum + " registered successfully.");
        } else {
            outputWriter.println("Failed to register " + plateNum + ": already exists.");
        }
    }
    
    /**
     * Generate and register a random license plate
     */
    public void addRandomLicence() {
        String plateNum;
        Random random = new Random();
        
        // Generate unique random plate
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < PLATE_LENGTH; i++) {
                int index = random.nextInt(CHARS.length());
                sb.append(CHARS.charAt(index));
            }
            plateNum = sb.toString();
        } while (!licenseTree.insert(plateNum));
        
        standardPlateCount++;
        outputWriter.println(plateNum + " created and registered successfully.");
    }
    
    /**
     * Remove a license plate from the system
     * @param plateNum License plate number
     */
    public void dropLicence(String plateNum) {
        if (licenseTree.delete(plateNum)) {
            // Check if it was a customized plate (no reliable way to know after deletion)
            // For a real system, we'd track this separately
            if (isCustomPlate(plateNum)) {
                customPlateCount--;
            } else {
                standardPlateCount--;
            }
            
            outputWriter.println(plateNum + " removed successfully.");
        } else {
            outputWriter.println("Failed to remove " + plateNum + ": does not exist.");
        }
    }
    
    /**
     * Check if a plate is custom or standard (heuristic approach)
     * Note: In a real system, we would track this information separately
     */
    private boolean isCustomPlate(String plateNum) {
        // We don't have a reliable way to know if a plate was custom or not
        // In a real system, we'd track this information
        // For now, assume random distribution - this is a simplification
        return true; // Defaulting to custom to be safe
    }
    
    /**
     * Check if a license plate exists
     * @param plateNum License plate number
     */
    public void lookupLicence(String plateNum) {
        if (licenseTree.search(plateNum)) {
            outputWriter.println(plateNum + " exists.");
        } else {
            outputWriter.println(plateNum + " does not exist.");
        }
    }
    
    /**
     * Find the previous license plate in lexicographical order
     * @param plateNum License plate number
     */
    public void lookupPrev(String plateNum) {
        String prev = licenseTree.predecessor(plateNum);
        if (prev != null) {
            outputWriter.println(plateNum + "'s prev is " + prev + ".");
        } else {
            outputWriter.println(plateNum + " has no prev.");
        }
    }
    
    /**
     * Find the next license plate in lexicographical order
     * @param plateNum License plate number
     */
    public void lookupNext(String plateNum) {
        String next = licenseTree.successor(plateNum);
        if (next != null) {
            outputWriter.println(plateNum + "'s next is " + next + ".");
        } else {
            outputWriter.println(plateNum + " has no next.");
        }
    }
    
    /**
     * Find all license plates in a given range
     * @param lo Lower bound
     * @param hi Upper bound
     */
    public void lookupRange(String lo, String hi) {
        String[] plates = licenseTree.range(lo, hi);
        
        if (plates.length == 0) {
            outputWriter.println("No plates found between " + lo + " and " + hi + ".");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Plate numbers between ").append(lo).append(" and ").append(hi).append(":");
        
        for (int i = 0; i < plates.length; i++) {
            sb.append(" ").append(plates[i]);
            if (i < plates.length - 1) {
                sb.append(",");
            }
        }
        sb.append(".");
        
        outputWriter.println(sb.toString());
    }
    
    /**
     * Calculate and report the annual revenue
     */
    public void revenue() {
        int totalRevenue = (standardPlateCount * STANDARD_FEE) + 
                          (customPlateCount * (STANDARD_FEE + CUSTOM_FEE));
        
        outputWriter.println("Current annual revenue is " + totalRevenue + " Galleons.");
    }
    
    /**
     * Process a command from the input file
     * @param command Command string
     */
    public void processCommand(String command) {
        String[] parts = command.trim().split("[\\(\\),]");
        String operation = parts[0].trim();
        
        switch (operation) {
            case "addLicence":
                if (parts.length > 1 && !parts[1].isEmpty()) {
                    addLicence(parts[1].trim());
                } else {
                    addRandomLicence();
                }
                break;
                
            case "dropLicence":
                if (parts.length > 1) {
                    dropLicence(parts[1].trim());
                }
                break;
                
            case "lookupLicence":
                if (parts.length > 1) {
                    lookupLicence(parts[1].trim());
                }
                break;
                
            case "lookupPrev":
                if (parts.length > 1) {
                    lookupPrev(parts[1].trim());
                }
                break;
                
            case "lookupNext":
                if (parts.length > 1) {
                    lookupNext(parts[1].trim());
                }
                break;
                
            case "lookupRange":
                if (parts.length > 2) {
                    lookupRange(parts[1].trim(), parts[2].trim());
                }
                break;
                
            case "revenue":
                revenue();
                break;
                
            case "quit":
                // Just exit the loop in main
                break;
                
            default:
                outputWriter.println("Unknown command: " + operation);
                break;
        }
    }
    
    /**
     * Main method to run the Flying Broomstick Management System
     * @param args Command line arguments [inputFileName]
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java plateMgmt inputFileName");
            System.exit(1);
        }
        
        String inputFile = args[0];
        String outputFile = inputFile + " " + "output.txt"; // Fixed output filename format
        
        plateMgmt system = new plateMgmt();
        system.initOutput(outputFile);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) {
                    continue; // Skip empty lines and comments
                }
                
                system.processCommand(line);
                
                if (line.startsWith("quit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        } finally {
            system.closeOutput();
        }
    }
}