package Pieces;

public class King extends Piece {
    // constructor
    public King(pieceColor color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    // Is move valid
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board, pieceColor pColor) {
        // look for check on target square
        // display message if it puts you in check
        // and return false

        // difference must be 1 max for either
        int changeX = Math.abs(endX - startX);
        int changeY = Math.abs(endY - startY);

        // Limit movement to one square per move and check color of target
        return (changeX <= 1 && changeY <= 1) &&
                (board[endX][endY] == null || board[endX][endY].color != this.color);
    }

    // Get unicode symbol
    @Override
    public String getSymbol() {
        return (color == pieceColor.WHITE) ? "\u2654" : "\u265A";    }
}
