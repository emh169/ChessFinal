package Pieces;
import Chess.*;

public abstract class Piece {
    // Pieces.Piece color using enum in Chess.Chess
    public Chess.PieceColor color;

    // Constructor
    public Piece(Chess.PieceColor color) {
        this.color = color;
    }

    // Get color
    public Chess.PieceColor getColor() {
        return color;
    }

    // Is move valid
    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board);

    // Get UNICODE symbol
    public abstract String getSymbol();
}
