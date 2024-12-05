package Pieces;

public class Knight extends Piece {
    // constructor
    public Knight(pieceColor color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    // Is move valid
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board, pieceColor pColor) {
        // difference
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // Check if movement is valid positionally and according to target square
        return (dx == 2 && dy == 1 || dx == 1 && dy == 2) && (board[endX][endY] == null || board[endX][endY].color != this.color);
    }

    // Get unicode symbol
    @Override
    public String getSymbol() {
        return (color == pieceColor.WHITE) ? "\u2658" : "\u265E";
    }}
