package Pieces;
import Chess.*;

public class Knight extends Piece {
    // Constructor
    public Knight(Chess.PieceColor color) {
        // Super() to compensate for lack of constructor
        super(color);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        // L move
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // Check if movement is valid positionally and according to target square
        return (dx == 2 && dy == 1 || dx == 1 && dy == 2) && (board[endX][endY] == null || board[endX][endY].color != this.color);
    }

    @Override
    public String getSymbol() {
        return (color == Chess.PieceColor.WHITE) ? "\u2658" : "\u265E";
    }
}
