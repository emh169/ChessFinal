package Pieces;

public class Rook extends Piece {
    // constructor
    public Rook(pieceColor color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    // Is move valid
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board, pieceColor color) {

        // if start or end match, meaning it goes one direction (horizontal or vertical only)
        if (startX == endX || startY == endY) {
            // get direction
            int moveX = Integer.compare(endX, startX);
            int moveY = Integer.compare(endY, startY);

            // iterate starting from start position
            int iterX = startX + moveX;
            int iterY = startY + moveY;

            // increment to end while checking path
            while (iterX != endX || iterY != endY) {
                // if piece is in the way
                if (board[iterX][iterY] != null) {
                    return false;
                }

                // iterate
                iterX += moveX;
                iterY += moveY;
            }

            // if valid target square
            return board[endX][endY] == null || board[endX][endY].color != this.color;
        }

        // default
        return false;
    }

    // Get unicode symbol
    @Override
    public String getSymbol() {
        return (color == pieceColor.WHITE) ? "\u2656" : "\u265C";
    }
}
