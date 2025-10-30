import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerXTurn = true;
    private JLabel statusLabel;
    private JButton resetButton;
    private int moves = 0;

    public TicTacToe() {
        setTitle("Tic Tac Toe ❌⭕");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        // Board Panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 40);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(buttonFont);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        // Reset button
        resetButton = new JButton("Restart 🔁");
        resetButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) return; // already filled

        clicked.setText(playerXTurn ? "X" : "O");
        clicked.setForeground(playerXTurn ? Color.RED : Color.BLUE);
        moves++;

        if (checkWinner()) {
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + " Wins! 🏆");
            disableAllButtons();
            JOptionPane.showMessageDialog(this, "🎉 Player " + (playerXTurn ? "X" : "O") + " Wins!");
        } else if (moves == 9) {
            statusLabel.setText("It's a Draw! 🤝");
            JOptionPane.showMessageDialog(this, "🤝 It's a Draw!");
        } else {
            playerXTurn = !playerXTurn;
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            // Rows
            if (!buttons[i][0].getText().isEmpty() &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][1].getText().equals(buttons[i][2].getText())) {
                return true;
            }
            // Columns
            if (!buttons[0][i].getText().isEmpty() &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[1][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }
        // Diagonals
        if (!buttons[0][0].getText().isEmpty() &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText())) {
            return true;
        }
        if (!buttons[0][2].getText().isEmpty() &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][0].getText())) {
            return true;
        }
        return false;
    }

    private void disableAllButtons() {
        for (JButton[] row : buttons)
            for (JButton b : row)
                b.setEnabled(false);
    }

    private void resetGame() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                b.setText("");
                b.setEnabled(true);
            }
        }
        playerXTurn = true;
        moves = 0;
        statusLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
