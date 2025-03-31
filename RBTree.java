/**
 * Red-Black Tree Implementation for the Flying Broomstick Management System
 * This implementation doesn't use any built-in library structures
 */
public class RBTree {
    // Colors for Red-Black Tree nodes
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    
    // Root node of the tree
    private Node root;
    
    /**
     * Node class for the Red-Black Tree
     */
    private class Node {
        String key; // License plate number
        boolean color; // RED or BLACK
        Node left, right, parent;
        
        Node(String key, boolean color) {
            this.key = key;
            this.color = color;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    /**
     * Constructor for an empty Red-Black Tree
     */
    public RBTree() {
        root = null;
    }
    
    /**
     * Checks if the tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * Inserts a new license plate into the Red-Black Tree
     * @param key License plate number
     * @return true if inserted successfully, false if already exists
     */
    public boolean insert(String key) {
        // Check if the key already exists
        if (search(key)) {
            return false;
        }
        
        // Create new red node
        Node node = new Node(key, RED);
        
        // Standard BST insert
        if (root == null) {
            root = node;
        } else {
            Node current = root;
            Node parent = null;
            
            while (current != null) {
                parent = current;
                int cmp = key.compareTo(current.key);
                
                if (cmp < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            
            node.parent = parent;
            
            int cmp = key.compareTo(parent.key);
            if (cmp < 0) {
                parent.left = node;
            } else {
                parent.right = node;
            }
        }
        
        // Fix Red-Black properties
        fixAfterInsertion(node);
        
        return true;
    }
    
    /**
     * Checks if a license plate exists in the tree
     * @param key License plate number to search for
     * @return true if found, false otherwise
     */
    public boolean search(String key) {
        Node node = root;
        
        while (node != null) {
            int cmp = key.compareTo(node.key);
            
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return true; // Found the key
            }
        }
        
        return false; // Key not found
    }
    
    /**
     * Removes a license plate from the tree
     * @param key License plate number to remove
     * @return true if removed successfully, false if not found
     */
    public boolean delete(String key) {
        // If the tree is empty or key doesn't exist
        if (root == null || !search(key)) {
            return false;
        }
        
        deleteNode(key);
        return true;
    }
    
    /**
     * Internal method to delete a node
     */
    private void deleteNode(String key) {
        Node node = findNode(key);
        if (node == null) return;
        
        // Case 1: Node has two children
        if (node.left != null && node.right != null) {
            // Find the successor (smallest node in right subtree)
            Node successor = findMin(node.right);
            node.key = successor.key;
            node = successor; // Now delete the successor instead
        }
        
        // Case 2 & 3: Node has at most one child
        Node replacement = (node.left != null) ? node.left : node.right;
        
        if (replacement != null) {
            // Connect replacement to parent
            replacement.parent = node.parent;
            
            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
            
            // If we're removing a black node with a red child, make the child black
            if (node.color == BLACK) {
                if (replacement.color == RED) {
                    replacement.color = BLACK;
                } else {
                    fixAfterDeletion(replacement);
                }
            }
        } else if (node.parent == null) {
            // Case: No children and no parent (root)
            root = null;
        } else {
            // Case: No children, but has parent
            
            // If we're removing a black node, fix violations
            if (node.color == BLACK) {
                fixAfterDeletion(node);
            }
            
            // Remove node from parent
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        }
    }
    
    /**
     * Finds a node with the given key
     */
    private Node findNode(String key) {
        Node current = root;
        
        while (current != null) {
            int cmp = key.compareTo(current.key);
            
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current; // Found the node
            }
        }
        
        return null; // Node not found
    }
    
    /**
     * Finds the minimum node in a subtree
     */
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    /**
     * Finds the maximum node in a subtree
     */
    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    /**
     * Finds the predecessor (previous license plate in lexicographical order)
     * @param key License plate number to find predecessor for
     * @return Predecessor key, or null if no predecessor exists
     */
    public String predecessor(String key) {
        Node node = findNode(key);
        
        // If the key doesn't exist, find where it would be
        if (node == null) {
            node = findInsertionPoint(key);
            if (node == null) return null;
            
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                // Return the predecessor of the insertion point
                Node pred = predecessor(node);
                return pred != null ? pred.key : null;
            } else {
                // The insertion point is the key itself, so return it
                return node.key;
            }
        }
        
        // If left subtree exists, the predecessor is the rightmost node in left subtree
        if (node.left != null) {
            return findMax(node.left).key;
        }
        
        // Otherwise, find the nearest ancestor where node is in the right subtree
        Node parent = node.parent;
        while (parent != null && node == parent.left) {
            node = parent;
            parent = parent.parent;
        }
        
        return parent != null ? parent.key : null;
    }
    
    /**
     * Finds the successor (next license plate in lexicographical order)
     * @param key License plate number to find successor for
     * @return Successor key, or null if no successor exists
     */
    public String successor(String key) {
        Node node = findNode(key);
        
        // If the key doesn't exist, find where it would be
        if (node == null) {
            node = findInsertionPoint(key);
            if (node == null) return null;
            
            int cmp = key.compareTo(node.key);
            if (cmp > 0) {
                // Return the successor of the insertion point
                Node succ = successor(node);
                return succ != null ? succ.key : null;
            } else {
                // The insertion point is the key itself, so return it
                return node.key;
            }
        }
        
        // If right subtree exists, the successor is the leftmost node in right subtree
        if (node.right != null) {
            return findMin(node.right).key;
        }
        
        // Otherwise, find the nearest ancestor where node is in the left subtree
        Node parent = node.parent;
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        
        return parent != null ? parent.key : null;
    }
    
    /**
     * Internal method to find successor of a node
     */
    private Node successor(Node node) {
        // If right subtree exists, the successor is the leftmost node in right subtree
        if (node.right != null) {
            return findMin(node.right);
        }
        
        // Otherwise, find the nearest ancestor where node is in the left subtree
        Node parent = node.parent;
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        
        return parent;
    }
    
    /**
     * Internal method to find predecessor of a node
     */
    private Node predecessor(Node node) {
        // If left subtree exists, the predecessor is the rightmost node in left subtree
        if (node.left != null) {
            return findMax(node.left);
        }
        
        // Otherwise, find the nearest ancestor where node is in the right subtree
        Node parent = node.parent;
        while (parent != null && node == parent.left) {
            node = parent;
            parent = parent.parent;
        }
        
        return parent;
    }
    
    /**
     * Finds where a node would be inserted
     */
    private Node findInsertionPoint(String key) {
        if (root == null) return null;
        
        Node current = root;
        Node parent = null;
        
        while (current != null) {
            parent = current;
            int cmp = key.compareTo(current.key);
            
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current; // Key found
            }
        }
        
        return parent; // This would be the parent of the new node
    }
    
    /**
     * Finds all license plates in a given range (inclusive)
     * @param lo Lower bound
     * @param hi Upper bound
     * @return Array of license plates in the range
     */
    public String[] range(String lo, String hi) {
        StringBuilder result = new StringBuilder();
        rangeSearch(root, lo, hi, result);
        
        // If no results, return empty array
        if (result.length() == 0) {
            return new String[0];
        }
        
        // Convert result to array (removing last comma)
        String resultStr = result.toString();
        return resultStr.substring(0, resultStr.length() - 1).split(",");
    }
    
    /**
     * Internal method for range search
     */
    private void rangeSearch(Node node, String lo, String hi, StringBuilder result) {
        if (node == null) return;
        
        int cmpLo = lo.compareTo(node.key);
        int cmpHi = hi.compareTo(node.key);
        
        // Search left subtree if lo is less than current node
        if (cmpLo < 0) {
            rangeSearch(node.left, lo, hi, result);
        }
        
        // Include current node if within range
        if (cmpLo <= 0 && cmpHi >= 0) {
            result.append(node.key).append(",");
        }
        
        // Search right subtree if hi is greater than current node
        if (cmpHi > 0) {
            rangeSearch(node.right, lo, hi, result);
        }
    }
    
    /**
     * Fixes Red-Black Tree properties after insertion
     */
    private void fixAfterInsertion(Node node) {
        node.color = RED;
        
        while (node != null && node != root && node.parent.color == RED) {
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                Node uncle = rightOf(parentOf(parentOf(node)));
                
                if (colorOf(uncle) == RED) {
                    // Case 1: Uncle is red
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == rightOf(parentOf(node))) {
                        // Case 2: Uncle is black, node is a right child
                        node = parentOf(node);
                        rotateLeft(node);
                    }
                    
                    // Case 3: Uncle is black, node is a left child
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rotateRight(parentOf(parentOf(node)));
                }
            } else {
                Node uncle = leftOf(parentOf(parentOf(node)));
                
                if (colorOf(uncle) == RED) {
                    // Case 1: Uncle is red
                    setColor(parentOf(node), BLACK);
                    setColor(uncle, BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == leftOf(parentOf(node))) {
                        // Case 2: Uncle is black, node is a left child
                        node = parentOf(node);
                        rotateRight(node);
                    }
                    
                    // Case 3: Uncle is black, node is a right child
                    setColor(parentOf(node), BLACK);
                    setColor(parentOf(parentOf(node)), RED);
                    rotateLeft(parentOf(parentOf(node)));
                }
            }
        }
        
        // Ensure root is black
        root.color = BLACK;
    }
    
    /**
     * Fixes Red-Black Tree properties after deletion
     */
    private void fixAfterDeletion(Node x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                Node sibling = rightOf(parentOf(x));
                
                if (colorOf(sibling) == RED) {
                    // Case 1: Sibling is red
                    setColor(sibling, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sibling = rightOf(parentOf(x));
                }
                
                if (colorOf(leftOf(sibling)) == BLACK && colorOf(rightOf(sibling)) == BLACK) {
                    // Case 2: Sibling is black, both of sibling's children are black
                    setColor(sibling, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sibling)) == BLACK) {
                        // Case 3: Sibling is black, left child is red, right child is black
                        setColor(leftOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rotateRight(sibling);
                        sibling = rightOf(parentOf(x));
                    }
                    
                    // Case 4: Sibling is black, right child is red
                    setColor(sibling, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sibling), BLACK);
                    rotateLeft(parentOf(x));
                    x = root; // End loop
                }
            } else {
                Node sibling = leftOf(parentOf(x));
                
                if (colorOf(sibling) == RED) {
                    // Case 1: Sibling is red
                    setColor(sibling, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sibling = leftOf(parentOf(x));
                }
                
                if (colorOf(rightOf(sibling)) == BLACK && colorOf(leftOf(sibling)) == BLACK) {
                    // Case 2: Sibling is black, both of sibling's children are black
                    setColor(sibling, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sibling)) == BLACK) {
                        // Case 3: Sibling is black, right child is red, left child is black
                        setColor(rightOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rotateLeft(sibling);
                        sibling = leftOf(parentOf(x));
                    }
                    
                    // Case 4: Sibling is black, left child is red
                    setColor(sibling, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sibling), BLACK);
                    rotateRight(parentOf(x));
                    x = root; // End loop
                }
            }
        }
        
        // Ensure the fixed node is black
        if (x != null) {
            x.color = BLACK;
        }
    }
    
    /**
     * Left rotate operation for Red-Black Tree
     */
    private void rotateLeft(Node x) {
        if (x == null) return;
        
        Node y = x.right;
        x.right = y.left;
        
        if (y.left != null) {
            y.left.parent = x;
        }
        
        y.parent = x.parent;
        
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        
        y.left = x;
        x.parent = y;
    }
    
    /**
     * Right rotate operation for Red-Black Tree
     */
    private void rotateRight(Node x) {
        if (x == null) return;
        
        Node y = x.left;
        x.left = y.right;
        
        if (y.right != null) {
            y.right.parent = x;
        }
        
        y.parent = x.parent;
        
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        
        y.right = x;
        x.parent = y;
    }
    
    /**
     * Helper methods for accessing node properties with null checks
     */
    private boolean colorOf(Node node) {
        return node == null ? BLACK : node.color;
    }
    
    private Node parentOf(Node node) {
        return node == null ? null : node.parent;
    }
    
    private Node leftOf(Node node) {
        return node == null ? null : node.left;
    }
    
    private Node rightOf(Node node) {
        return node == null ? null : node.right;
    }
    
    private void setColor(Node node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }
}