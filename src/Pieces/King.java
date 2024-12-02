package Pieces;
import Chess.*;

public class King extends Piece {
    // Constructor
    public King(Chess.PieceColor color) {
        // Super() to compensate for lack of constructor
        super(color);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        // Move one square
        int changeX = Math.abs(endX - startX);
        int changeY = Math.abs(endY - startY);
        
        // Limit movement to one square per move and check color of target
        return (changeX <= 1 && changeY <= 1) &&
                (board[endX][endY] == null || board[endX][endY].color != this.color);
    }

    @Override
    public String getSymbol() {
        return (color == Chess.PieceColor.WHITE) ? "\u2654" : "\u265A";
    }
}
