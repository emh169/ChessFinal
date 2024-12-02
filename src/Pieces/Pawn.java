package Pieces;
import Chess.*;

public class Pawn extends Piece {
    // Constructor
    public Pawn(Chess.PieceColor color) {
        // Super() to compensate for lack of constructor
        super(color);
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board) {
        // ADD FUNCTIONALITY
        return true;
    }

    @Override
    public String getSymbol() {
        return (color == Chess.PieceColor.WHITE) ? "\u2659" : "\u265F";
    }
}
