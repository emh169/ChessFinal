package Main;

import Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chess extends JFrame {
    // board size dimensions
    private static final int BOARD_SIZE = 8;

    // current turn
    private static Piece.pieceColor currentTurn = Piece.pieceColor.WHITE;

    // board of pieces and board of buttons
    private static Piece[][] pieceBoard = new Piece[BOARD_SIZE][BOARD_SIZE];
    private static JButton[][] buttonBoard = new JButton[BOARD_SIZE][BOARD_SIZE];

    // both kings pos (initialized at starting position)
    private Point wKingPos = new Point(0, 3); // White King initial position
    private Point bKingPos = new Point(7, 3); // Black King initial position

    // selected point
    private Point selected = null;

    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Chess();
        });
    }

    // constructor
    public Chess() {
        // setup window
        setTitle("Chess \u2658");
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup piece board
        setupPieceBoard();

        // setup button board using elements on piece board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton();
                button.setFont(new Font("", Font.BOLD, 36));
                button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                button.setOpaque(true);

                Piece piece = pieceBoard[row][col];
                if (piece != null) {
                    button.setText(piece.getSymbol());
                }

                button.addActionListener(new ClickListener(row, col));
                buttonBoard[row][col] = button;
                add(button);
            }
        }

        // set frame as visible
        setVisible(true);
    }

    // listen for clicks on button board
    private class ClickListener implements ActionListener {
        // row and column of clicked button
        private int row;
        private int col;

        // constructor
        public ClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Piece clickedPiece = pieceBoard[row][col];

            //
            if (selected == null) {
                if (clickedPiece != null && clickedPiece.color == currentTurn) {
                    selected = new Point(row, col);
                    buttonBoard[row][col].setBackground(Color.GREEN);
                    highlightValidMoves(clickedPiece, row, col);
                }
            } else {
                int initialX = selected.x;
                int initialY = selected.y;
                Piece selectedPiece = pieceBoard[initialX][initialY];

                // reset highlighted squares
                clearHighlights();

                // check if piece is null and legal move
                if (selectedPiece != null && selectedPiece.isValidMove(initialX, initialY, row, col,
                        pieceBoard, clickedPiece != null ? clickedPiece.color : null)) {
                    // use temp array to simulate
                    Piece tempBoard = pieceBoard[row][col];
                    pieceBoard[row][col] = selectedPiece;
                    pieceBoard[initialX][initialY] = null;

                    // if updating king, store original pos to it can be reset
                    Point originalKingPos = null;
                    if (selectedPiece instanceof King) {
                        if (currentTurn == Piece.pieceColor.WHITE) {
                            //store original
                            originalKingPos = new Point(wKingPos);
                            wKingPos.setLocation(row, col);
                        } else {
                            // store original
                            originalKingPos = new Point(bKingPos);
                            bKingPos.setLocation(row, col);
                        }
                    }

                    // king still in check
                    boolean stillInCheck = isKingInCheck(currentTurn);

                    // undo movement if still in check
                    if (stillInCheck) {
                        pieceBoard[initialX][initialY] = selectedPiece;
                        pieceBoard[row][col] = tempBoard;

                        if (selectedPiece instanceof King) {
                            if (currentTurn == Piece.pieceColor.WHITE) {
                                wKingPos.setLocation(originalKingPos);
                            } else {
                                bKingPos.setLocation(originalKingPos);
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Invalid: King remains in check.");
                        selected = null;
                        return;
                    }

                    // exchange symbols
                    buttonBoard[row][col].setText(selectedPiece.getSymbol());
                    buttonBoard[initialX][initialY].setText("");

                    // check and checkmate
                    Piece.pieceColor opponentColor = (currentTurn == Piece.pieceColor.WHITE) ? Piece.pieceColor.BLACK : Piece.pieceColor.WHITE;
                    boolean inCheck = isKingInCheck(opponentColor);
                    boolean checkmate = inCheck && isCheckmate(opponentColor);

                    if (checkmate) {
                        JOptionPane.showMessageDialog(null, " Checkmate: " + currentTurn + " wins!");
                        disableBoard();
                        return; // End the game
                    } else if (inCheck) {
                        JOptionPane.showMessageDialog(null, "Check!");
                    }

                    // change turn
                    currentTurn = opponentColor;
                } else {
                    // invalid move
                    System.out.println("Invalid move.");
                }

                selected = null;
            }
        }

    }

    // highlight valid squares
    private void highlightValidMoves(Piece selectedPiece, int initialX, int initialY) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (selectedPiece.isValidMove(initialX, initialY, i, j, pieceBoard, selectedPiece.color)) {
                    // change background to yellow
                    buttonBoard[i][j].setBackground(Color.YELLOW);
                }
            }
        }
    }

    // reverse square highlights
    private void clearHighlights() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // determine original color and replace
                buttonBoard[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
            }
        }
    }

    // look for check
    private boolean isKingInCheck(Piece.pieceColor color) {
        // get pos of king under attach
        Point kingPos = (color == Piece.pieceColor.WHITE) ? wKingPos : bKingPos;

        // iterate through board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // get piece (or null) at square
                Piece piece = pieceBoard[row][col];
                
                // check for null and color
                if (piece != null && piece.color != color) {
                    // check if king pos is under attack
                    if (piece.isValidMove(row, col, kingPos.x, kingPos.y, pieceBoard, color)) {
                        return true;
                    }
                }
            }
        }
        
        // not under attack
        return false;
    }

    // look for checkmate
    private boolean isCheckmate(Piece.pieceColor color) {
        // look for check. can't be checkmate without check
        if (!isKingInCheck(color)) {
            return false;
        }

        // iterate through defending pieces
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // get piece
                Piece piece = pieceBoard[row][col];
                
                if (piece != null && piece.color == color) {
                    // check all possible moves
                    for (int i = 0; i < BOARD_SIZE; i++) {
                        for (int j = 0; j < BOARD_SIZE; j++) {
                            if (piece.isValidMove(row, col, i, j, pieceBoard, piece.color)) {
                                // simulate move with temp array
                                Piece tempBoard = pieceBoard[i][j];
                                pieceBoard[i][j] = piece;
                                pieceBoard[row][col] = null;

                                // update king position
                                Point originalKingPos = null;
                                if (piece instanceof King) {
                                    originalKingPos = color == Piece.pieceColor.WHITE ? new Point(wKingPos) : new Point(bKingPos);
                                    if (color == Piece.pieceColor.WHITE) {
                                        wKingPos.setLocation(i, j);
                                    } else {
                                        bKingPos.setLocation(i, j);
                                    }
                                }

                                boolean stillInCheck = isKingInCheck(color);

                                // reverse move
                                pieceBoard[row][col] = piece;
                                pieceBoard[i][j] = tempBoard;

                                // reset king position
                                if (piece instanceof King && originalKingPos != null) {
                                    if (color == Piece.pieceColor.WHITE) {
                                        wKingPos.setLocation(originalKingPos);
                                    } else {
                                        bKingPos.setLocation(originalKingPos);
                                    }
                                }

                                // not checkmate, gets out of check
                                if (!stillInCheck) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        // no valid moves
        return true;
    }

    // clear board to end play
    private void disableBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // disables swing component, dims color
                buttonBoard[row][col].setEnabled(false);
            }
        }
    }

    // setup board with pieces
    private void setupPieceBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            pieceBoard[1][i] = new Pawn(Piece.pieceColor.WHITE, 1, i);
        }

        pieceBoard[0][0] = new Rook(Piece.pieceColor.WHITE, 0, 0);
        pieceBoard[0][1] = new Knight(Piece.pieceColor.WHITE, 0, 1);
        pieceBoard[0][2] = new Bishop(Piece.pieceColor.WHITE, 0, 2);
        pieceBoard[0][4] = new Queen(Piece.pieceColor.WHITE, 0, 4);
        pieceBoard[0][3] = new King(Piece.pieceColor.WHITE, 0, 3);
        pieceBoard[0][5] = new Bishop(Piece.pieceColor.WHITE, 0, 5);
        pieceBoard[0][6] = new Knight(Piece.pieceColor.WHITE, 0, 6);
        pieceBoard[0][7] = new Rook(Piece.pieceColor.WHITE, 0, 7);

        for (int i = 0; i < BOARD_SIZE; i++) {
            pieceBoard[6][i] = new Pawn(Piece.pieceColor.BLACK, 6, i);
        }

        pieceBoard[7][0] = new Rook(Piece.pieceColor.BLACK, 7, 0);
        pieceBoard[7][1] = new Knight(Piece.pieceColor.BLACK, 7, 1);
        pieceBoard[7][2] = new Bishop(Piece.pieceColor.BLACK, 7, 2);
        pieceBoard[7][4] = new Queen(Piece.pieceColor.BLACK, 7, 4);
        pieceBoard[7][3] = new King(Piece.pieceColor.BLACK, 7, 3);
        pieceBoard[7][5] = new Bishop(Piece.pieceColor.BLACK, 7, 5);
        pieceBoard[7][6] = new Knight(Piece.pieceColor.BLACK, 7, 6);
        pieceBoard[7][7] = new Rook(Piece.pieceColor.BLACK, 7, 7);
    }
}
