package Chess;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Chess extends JFrame {
    // final board dimension value (for x and y)
    private static final int BOARD_SIZE = 8;

    // Pieces.Piece color
    public enum PieceColor {
        WHITE, BLACK
    }

    // Boards
    private Piece[][] PieceBoard = new Piece[BOARD_SIZE][BOARD_SIZE];
    private JButton[][] board = new JButton[BOARD_SIZE][BOARD_SIZE];

    // Turn control
    private PieceColor currentTurn = PieceColor.WHITE;

    // Selected position coordinates
    private Coords selected = null;

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Chess chessGame = new Chess();
        });
    }

    // Default constructor
    public Chess() {
        setTitle("Chess.Chess \u2658");
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize board state
        setupInitialBoard();

        // Initialize GUI board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton();

                // Font, background color, and opacity
                button.setFont(new Font("", Font.BOLD, 36));
                button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                button.setOpaque(true);

                // Display piece using UNICODE characters
                Piece piece = PieceBoard[row][col];
                if (piece != null) {
                    button.setText(piece.getSymbol());
                }

                // Add action listener
                button.addActionListener(new ClickListener(row, col));
                board[row][col] = button;
                add(button);
            }
        }

        setVisible(true);
    }

    // Setup board pieces
    private void setupInitialBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            PieceBoard[1][i] = new Pawn(PieceColor.WHITE);
        }

        PieceBoard[0][0] = new Rook(PieceColor.WHITE);
        PieceBoard[0][1] = new Knight(PieceColor.WHITE);
        PieceBoard[0][2] = new Bishop(PieceColor.WHITE);
        PieceBoard[0][3] = new Queen(PieceColor.WHITE);
        PieceBoard[0][4] = new King(PieceColor.WHITE);
        PieceBoard[0][5] = new Bishop(PieceColor.WHITE);
        PieceBoard[0][6] = new Knight(PieceColor.WHITE);
        PieceBoard[0][7] = new Rook(PieceColor.WHITE);

        for (int i = 0; i < BOARD_SIZE; i++) {
            PieceBoard[6][i] = new Pawn(PieceColor.BLACK);
        }

        PieceBoard[7][0] = new Rook(PieceColor.BLACK);
        PieceBoard[7][1] = new Knight(PieceColor.BLACK);
        PieceBoard[7][2] = new Bishop(PieceColor.BLACK);
        PieceBoard[7][3] = new Queen(PieceColor.BLACK);
        PieceBoard[7][4] = new King(PieceColor.BLACK);
        PieceBoard[7][5] = new Bishop(PieceColor.BLACK);
        PieceBoard[7][6] = new Knight(PieceColor.BLACK);
        PieceBoard[7][7] = new Rook(PieceColor.BLACK);
    }

    // Click listener
    private class ClickListener implements ActionListener {
        private int row;
        private int col;

        public ClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Piece clickedPiece = PieceBoard[row][col];

            if (selected == null) {
                if (clickedPiece != null && clickedPiece.color == currentTurn) {
                    selected = new Coords(row, col);
                    board[row][col].setBackground(Color.RED);
                }
            } else {
                int initX = selected.x, initY = selected.y;
                Piece selectedPiece = PieceBoard[initX][initY];

                // Reset background color
                board[initX][initY].setBackground((initX + initY) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);

                if (selectedPiece.isValidMove(initX, initY, row, col, PieceBoard)) {
                    // Valid
                    PieceBoard[row][col] = selectedPiece;
                    PieceBoard[initX][initY] = null;

                    board[row][col].setText(selectedPiece.getSymbol());
                    board[initX][initY].setText("");

                    board[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);

                    // Change turn
                    currentTurn = (currentTurn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
                } else {
                    // Invalid
                    JOptionPane.showMessageDialog(null, "Invalid move!");

                    // Reset background
                    board[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                }

                // Reset
                selected = null;
            }
        }
    }


    // Coordinates
    private class Coords {
        int x;
        int y;

        Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
