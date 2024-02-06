package checkers;

public class BlackMoveBehavior implements IMoveBehavior {


    @Override
    public boolean isValidOrdinaryMove(Square square, int x, int y) {
        return Math.abs(square.getX()-x) == 1 && square.getY() == y - 1;
    }

    @Override
    public Piece getCapturedPiece(Square square, int x, int y) {
        Piece capturedPiece = BoardController.getSquare((square.getX() + x)/2, (square.getY()+y)/2).getPiece();
        boolean correctX = Math.abs(square.getX() - x) == 2;
        boolean correctY = square.getY() == y - 2;
        boolean correctColor = capturedPiece.getType().equals(Piece.Type.RED);
        if(correctX && correctY && correctColor) {
            return capturedPiece;
        } else {
            return null;
        }
    }
}
