package Pieces;


public abstract class Piece {
    // x and y positions
    public int xPos;
    public int yPos;
    public pieceColor color;

    // constructor
    public Piece(pieceColor color, int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
    };

    // check if move is valid
    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board, pieceColor pColor);

    // get UNICODE symbol
    public abstract String getSymbol();

    // color enum
    public enum pieceColor {
        WHITE, BLACK
    }

    public void setXPos(int x) {
        this.xPos = x;
    }

    public void setYPos(int y) {
        this.yPos = y;
    }

}
