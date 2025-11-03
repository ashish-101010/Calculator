import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class calculator extends JFrame {
    private final JTextField display = new JTextField("0");
    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 420);
        setLocationRelativeTo(null);

        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 28));
        display.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(display, BorderLayout.NORTH);

        String[] buttons = {
            "C", "←", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", "0", ".", "=" 
        };

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        addButton(panel, "C", e -> clearAll());
        addButton(panel, "←", e -> backspace());
        addButton(panel, "/", e -> setOperator("/"));
        addButton(panel, "*", e -> setOperator("*"));

        addButton(panel, "7", e -> appendDigit("7"));
        addButton(panel, "8", e -> appendDigit("8"));
        addButton(panel, "9", e -> appendDigit("9"));
        addButton(panel, "-", e -> setOperator("-"));

        addButton(panel, "4", e -> appendDigit("4"));
        addButton(panel, "5", e -> appendDigit("5"));
        addButton(panel, "6", e -> appendDigit("6"));
        addButton(panel, "+", e -> setOperator("+"));

        addButton(panel, "1", e -> appendDigit("1"));
        addButton(panel, "2", e -> appendDigit("2"));
        addButton(panel, "3", e -> appendDigit("3"));
        addButton(panel, "=", e -> calculate());

        JButton zero = new JButton("0");
        zero.setFont(new Font("SansSerif", Font.BOLD, 20));
        zero.addActionListener(e -> appendDigit("0"));
        panel.add(zero);

        panel.add(new JLabel(""));

        addButton(panel, ".", e -> appendDecimal());
        addButton(panel, "=", e -> calculate()); 

        add(panel, BorderLayout.CENTER);
        setResizable(false);
    }

    private void addButton(JPanel panel, String text, ActionListener al) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn.addActionListener(al);
        panel.add(btn);
    }

    private void appendDigit(String d) {
        if (startNewNumber) {
            display.setText(d.equals("0") ? "0" : d);
            startNewNumber = d.equals("0");
            if (!startNewNumber) startNewNumber = false;
            if (!d.equals("0")) startNewNumber = false;
        } else {
            String cur = display.getText();
            if (cur.equals("0")) display.setText(d);
            else display.setText(cur + d);
        }
        startNewNumber = false;
    }

    private void appendDecimal() {
        String cur = display.getText();
        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
            return;
        }
        if (!cur.contains(".")) {
            display.setText(cur + ".");
        }
    }

    private void setOperator(String op) {
        try {
            firstOperand = Double.parseDouble(display.getText());
        } catch (NumberFormatException ex) {
            display.setText("Error");
            return;
        }
        operator = op;
        startNewNumber = true;
    }

    private void calculate() {
        if (operator.isEmpty()) return;
        double second;
        try {
            second = Double.parseDouble(display.getText());
        } catch (NumberFormatException ex) {
            display.setText("Error");
            return;
        }
        double result = 0;
        switch (operator) {
            case "+" -> result = firstOperand + second;
            case "-" -> result = firstOperand - second;
            case "*" -> result = firstOperand * second;
            case "/" -> {
                if (second == 0) {
                    display.setText("Cannot divide by 0");
                    operator = "";
                    startNewNumber = true;
                    return;
                } else {
                    result = firstOperand / second;
                }
            }
        }
        if (result == (long) result) display.setText(String.valueOf((long) result));
        else display.setText(String.valueOf(result));
        operator = "";
        startNewNumber = true;
    }

    private void clearAll() {
        display.setText("0");
        firstOperand = 0;
        operator = "";
        startNewNumber = true;
    }

    private void backspace() {
        if (startNewNumber) return;
        String cur = display.getText();
        if (cur.length() <= 1) {
            display.setText("0");
            startNewNumber = true;
        } else {
            display.setText(cur.substring(0, cur.length() - 1));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            calculator calc = new calculator();
            calc.setVisible(true);
        });
    }
}
