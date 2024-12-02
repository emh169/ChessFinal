package Pieces;
import Chess.*;

public class Rook extends Piece {
    // Constructor
    public Rook(Chess.PieceColor color) {
        // Super() to compensate for lack of constructor
        super(color);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        // Vertical and horizontal
        if (startX == endX || startY == endY) {
            // Get direction for movement
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
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return (color == Chess.PieceColor.WHITE) ? "\u2656" : "\u265C";
    }
}
