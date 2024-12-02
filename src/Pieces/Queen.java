package Pieces;
import Chess.*;

public class Queen extends Piece {
    // Constructor
    public Queen(Chess.PieceColor color) {
        // Super() to compensate for lack of constructor
        super(color);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
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
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return (color == Chess.PieceColor.WHITE) ? "\u2655" : "\u265B";
    }
}
