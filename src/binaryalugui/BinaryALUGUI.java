
package binaryalugui;

import javax.swing.*;
import java.awt.*;

public class BinaryALUGUI {
    private JFrame frame;
    private JTextField inputA, inputB, resultField;
    private JLabel zeroFlagLabel, carryFlagLabel, overflowFlagLabel, negativeFlagLabel;
    private StatusRegister statusRegister;

    public BinaryALUGUI() {
        statusRegister = new StatusRegister();

        // Frame setup
        frame = new JFrame("Status Register - Final Version");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        // Title
        JLabel title = new JLabel("Status register Of Binary Arithmetic Logic Unit (ALU)", JLabel.CENTER);
        title.setFont(new Font("Roboto", Font.BOLD, 20));
        title.setForeground(Color.CYAN);
        frame.add(title, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(new Color(45, 45, 45));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelA = createStyledLabel("Input A (Binary):");
        JLabel labelB = createStyledLabel("Input B (Binary):");

        inputA = createStyledTextField();
        inputB = createStyledTextField();

        inputPanel.add(labelA);
        inputPanel.add(inputA);
        inputPanel.add(labelB);
        inputPanel.add(inputB);
        frame.add(inputPanel, BorderLayout.WEST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        buttonPanel.setBackground(new Color(50, 50, 50));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = createStyledButton("ADD");
        JButton subButton = createStyledButton("SUB");
        JButton andButton = createStyledButton("AND");
        JButton orButton = createStyledButton("OR");
        JButton xorButton = createStyledButton("XOR");
        JButton notButton = createStyledButton("NOT");
        JButton mulButton = createStyledButton("MUL");
        JButton divButton = createStyledButton("DIV");
        JButton clearButton = createStyledButton("CLEAR");

        buttonPanel.add(addButton);
        buttonPanel.add(subButton);
        buttonPanel.add(andButton);
        buttonPanel.add(orButton);
        buttonPanel.add(xorButton);
        buttonPanel.add(notButton);
        buttonPanel.add(mulButton);
        buttonPanel.add(divButton);
        buttonPanel.add(clearButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Output Panel
        JPanel outputPanel = new JPanel(new GridLayout(2, 1));
        outputPanel.setBackground(new Color(30, 30, 30));
        outputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel resultLabel = createStyledLabel("Result:");
        resultField = createStyledTextField();
        resultField.setEditable(false);
        resultField.setBackground(new Color(50, 50, 50));
        resultField.setForeground(Color.GREEN);
// Set preferred size to make the result field larger
resultField.setPreferredSize(new Dimension(100, 50));

        outputPanel.add(resultLabel);
        outputPanel.add(resultField);
        frame.add(outputPanel, BorderLayout.EAST);

        // Flags Panel
        JPanel flagPanel = new JPanel(new GridLayout(1, 4));
        flagPanel.setBackground(new Color(30, 30, 30));
        flagPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN, 1), "Flags", 0, 0, new Font("Roboto", Font.BOLD, 14), Color.CYAN));

        zeroFlagLabel = createFlagLabel("Zero");
        carryFlagLabel = createFlagLabel("Carry");
        overflowFlagLabel = createFlagLabel("Overflow");
        negativeFlagLabel = createFlagLabel("Negative");

        flagPanel.add(zeroFlagLabel);
        flagPanel.add(carryFlagLabel);
        flagPanel.add(overflowFlagLabel);
        flagPanel.add(negativeFlagLabel);
        frame.add(flagPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> performOperation("ADD"));
        subButton.addActionListener(e -> performOperation("SUB"));
        andButton.addActionListener(e -> performOperation("AND"));
        orButton.addActionListener(e -> performOperation("OR"));
        xorButton.addActionListener(e -> performOperation("XOR"));
        notButton.addActionListener(e -> performOperation("NOT"));
        mulButton.addActionListener(e -> performOperation("MUL"));
        divButton.addActionListener(e -> performOperation("DIV"));
        clearButton.addActionListener(e -> clearInputs());

        frame.setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Roboto", Font.PLAIN, 14));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setForeground(Color.GREEN);
        textField.setBackground(new Color(50, 50, 50));
        textField.setCaretColor(Color.CYAN);
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.CYAN);
        button.setFocusPainted(false);
        return button;
    }

    private JLabel createFlagLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.RED);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Roboto", Font.BOLD, 14));
        return label;
    }

    private void performOperation(String operation) {
        try {
            String binaryA = inputA.getText();
            String binaryB = inputB.getText();

            // Validation for binary input
            if (!isBinary(binaryA) || (!binaryB.isEmpty() && !isBinary(binaryB))) {
                JOptionPane.showMessageDialog(frame, "Error: Please enter valid binary numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Handle NOT operation separately
            if (operation.equals("NOT") && !binaryB.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "For NOT operation, only Input A is required.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String result = switch (operation) {
                case "ADD" -> statusRegister.add(binaryA, binaryB);
                case "SUB" -> statusRegister.subtract(binaryA, binaryB);
                case "AND" -> statusRegister.and(binaryA, binaryB);
                case "OR" -> statusRegister.or(binaryA, binaryB);
                case "XOR" -> statusRegister.xor(binaryA, binaryB);
                case "NOT" -> statusRegister.not(binaryA);
                case "MUL" -> statusRegister.multiply(binaryA, binaryB);
                case "DIV" -> statusRegister.divide(binaryA, binaryB);
                default -> "Invalid Operation";
            };

            resultField.setText(result);
            updateFlags();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isBinary(String input) {
        return input.matches("[01]*");
    }

    private void updateFlags() {
        zeroFlagLabel.setBackground(statusRegister.getZeroFlag() ? Color.GREEN : Color.RED);
        carryFlagLabel.setBackground(statusRegister.getCarryFlag() ? Color.GREEN : Color.RED);
        overflowFlagLabel.setBackground(statusRegister.getOverflowFlag() ? Color.GREEN : Color.RED);
        negativeFlagLabel.setBackground(statusRegister.getNegativeFlag() ? Color.GREEN : Color.RED);
    }

    private void clearInputs() {
        inputA.setText("");
        inputB.setText("");
        resultField.setText("");
        statusRegister.resetFlags();
        updateFlags();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BinaryALUGUI::new);
    }
}

// Ensure the StatusRegister class is up-to-date and includes XOR, MUL, DIV methods.


// StatusRegister remains unchanged, but add the new methods for XOR, multiplication, and division.

class StatusRegister {
    private boolean zeroFlag;
    private boolean carryFlag;
    private boolean overflowFlag;
    private boolean negativeFlag;

    public StatusRegister() {
        resetFlags();
    }

    // Helper method to convert binary string to signed integer based on bit length
    private int toSignedInt(String binary) {
        int bits = binary.length();
        if (binary.charAt(0) == '1') { // Check if MSB is 1 (negative number in 2's complement)
            // Convert from 2's complement by subtracting 2^bits to get the negative value
            return Integer.parseInt(binary, 2) - (1 << bits);
        }
        return Integer.parseInt(binary, 2); // Positive number, directly convert
    }

    // Helper method to convert signed integer to binary string of specified bit length
    private String toBinaryString(int value, int bits) {
        String binary = Integer.toBinaryString(value & ((1 << bits) - 1)); // Mask to the specified bit length
        return String.format("%" + bits + "s", binary).replace(' ', '0'); // Ensure correct bit length
    }

    // Addition operation (with dynamic bit length)
    public String add(String a, String b) {
        resetFlags();
        int bits = Math.max(a.length(), b.length()); // Determine the bit length based on input
        int numA = toSignedInt(a);
        int numB = toSignedInt(b);
        int sum = numA + numB;

        // Update flags
        carryFlag = (sum >= (1 << (bits - 1)) || sum < -(1 << (bits - 1))); // Handle overflow
        zeroFlag = (sum == 0);
        negativeFlag = (sum < 0); // Negative if less than 0
        overflowFlag = ((numA > 0 && numB > 0 && sum < 0) || (numA < 0 && numB < 0 && sum > 0));

        return toBinaryString(sum, bits); // Return result as binary with the correct bit length
    }

    // Subtraction operation (with dynamic bit length)
    public String subtract(String a, String b) {
        resetFlags();
        int bits = Math.max(a.length(), b.length()); // Determine the bit length based on input
        int numA = toSignedInt(a);
        int numB = toSignedInt(b);
        int diff = numA - numB;

        // Update flags
        carryFlag = (numA < numB); // Borrow occurred
        zeroFlag = (diff == 0);
        negativeFlag = (diff < 0);
        overflowFlag = ((numA > 0 && numB < 0 && diff < 0) || (numA < 0 && numB > 0 && diff > 0));

        return toBinaryString(diff, bits);
    }

    // AND operation (with dynamic bit length)
    public String and(String a, String b) {
        resetFlags();
        int bits = Math.max(a.length(), b.length());
        int numA = toSignedInt(a);
        int numB = toSignedInt(b);
        int result = numA & numB;

        // Update flags
        zeroFlag = (result == 0);
        negativeFlag = (result < 0);

        return toBinaryString(result, bits);
    }

    // OR operation (with dynamic bit length)
    public String or(String a, String b) {
        resetFlags();
        int bits = Math.max(a.length(), b.length());
        int numA = toSignedInt(a);
        int numB = toSignedInt(b);
        int result = numA | numB;

        // Update flags
        zeroFlag = (result == 0);
        negativeFlag = (result < 0);

        return toBinaryString(result, bits);
    }

    // XOR operation (with dynamic bit length)
    public String xor(String a, String b) {
        resetFlags();
        int bits = Math.max(a.length(), b.length());
        int numA = toSignedInt(a);
        int numB = toSignedInt(b);
        int result = numA ^ numB;

        // Update flags
        zeroFlag = (result == 0);
        negativeFlag = (result < 0);

        return toBinaryString(result, bits);
    }

    // NOT operation (with dynamic bit length)
    public String not(String a) {
        resetFlags();
        int bits = a.length();
        int numA = toSignedInt(a);
        int result = ~numA & ((1 << bits) - 1); // Apply mask to ensure bit length

        // Update flags
        zeroFlag = (result == 0);
        negativeFlag = (result < 0);

        return toBinaryString(result, bits);
    }

    // Multiply operation (with dynamic bit length)
    public String multiply(String a, String b) {
        resetFlags();
        int bits = Math.max(a.length(), b.length());
        int numA = toSignedInt(a);
        int numB = toSignedInt(b);
        int product = numA * numB;

        // Update flags
        zeroFlag = (product == 0);
        negativeFlag = (product < 0);
        overflowFlag = (Math.abs(product) >= (1 << bits)); // Handle overflow for multiplication

        return toBinaryString(product, bits+bits);
    }

    // Divide operation (with dynamic bit length)
    public String divide(String a, String b) {
        resetFlags();
        int bits = Math.max(a.length(), b.length());
        int numA = toSignedInt(a);
        int numB = toSignedInt(b);

        if (numB == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }

        int quotient = numA / numB;

        // Update flags
        zeroFlag = (quotient == 0);
        negativeFlag = (quotient < 0);

        return toBinaryString(quotient, bits);
    }

    // Reset all flags
    public void resetFlags() {
        zeroFlag = false;
        carryFlag = false;
        overflowFlag = false;
        negativeFlag = false;
    }

    // Getters for flags
    public boolean getZeroFlag() {
        return zeroFlag;
    }

    public boolean getCarryFlag() {
        return carryFlag;
    }

    public boolean getOverflowFlag() {
        return overflowFlag;
    }

    public boolean getNegativeFlag() {
        return negativeFlag;
    }
}

