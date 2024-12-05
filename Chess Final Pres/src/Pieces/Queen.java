package Pieces;

public class Queen extends Piece {
    // constructor
    public Queen(pieceColor color, int xPos, int yPos) {
        super(color, xPos, yPos);
    }

    // Is move valid
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board, pieceColor pColor) {
        // Vertical, horizontal, and diagonal movement
        if (startX == endX || startY == endY) {
            // Vertical and horizontal
            int moveX = Integer.compare(endX, startX);
            int moveY = Integer.compare(endY, startY);
            int x = startX + moveX;
            int y = startY + moveY;

            // Increment movement and check path
            while (x != endX || y != endY) {
                if (board[x][y] != null) {
                    return false;
                }
                x += moveX;
                y += moveY;
            }

            return board[endX][endY] == null || board[endX][endY].color != this.color;

        } else if (Math.abs(endX - startX) == Math.abs(endY - startY)) {
            // Get direction
            int moveX = Integer.compare(endX, startX);
            int moveY = Integer.compare(endY, startY);
            int x = startX + moveX;
            int y = startY + moveY;

            while (x != endX || y != endY) {
                if (board[x][y] != null) {
                    return false;
                }
                x += moveX;
                y += moveY;
            }
            return board[endX][endY] == null || board[endX][endY].color != this.color;
        } else {
            return false;
        }
    }

    // Get unicode symbol
    @Override
    public String getSymbol() {
        return (color == pieceColor.WHITE) ? "\u2655" : "\u265B";
    }
}
