package Pieces;

public class Pawn extends Piece {
    // Constructor
    public Pawn(pieceColor color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    // Is move valid
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board, pieceColor pColor) {
        // direction
        int direction = (this.color == pieceColor.WHITE) ? 1 : -1; // White moves "down", Black moves "up"

        // one square move
        if (endX == startX + direction && startY == endY && board[endX][endY] == null) {
            return true;
        }

        // two square move
        if (startX == (this.color == pieceColor.WHITE ? 1 : 6) &&
                endX == startX + 2 * direction && startY == endY &&
                board[startX + direction][endY] == null && board[endX][endY] == null) {
            return true;
        }

        // diagonal capture
        if (endX == startX + direction && Math.abs(endY - startY) == 1 &&
                board[endX][endY] != null && board[endX][endY].color != this.color) {
            return true;
        }

        // invalid
        return false;
    }

    // Get unicode symbol
    @Override
    public String getSymbol() {
        return (color == pieceColor.WHITE) ? "\u2659" : "\u265F";
    }
}
