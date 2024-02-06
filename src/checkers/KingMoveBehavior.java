package checkers;

public class KingMoveBehavior implements IMoveBehavior {

    private final boolean isBlack;

    public KingMoveBehavior(boolean isBlack) {
        this.isBlack = isBlack;
    }

    @Override
    public boolean isValidOrdinaryMove(Square square, int x, int y) {
        return Math.abs(square.getX()-x) == 1 && Math.abs(square.getY()-y) == 1;
    }

    @Override
    public Piece getCapturedPiece(Square square, int x, int y) {
        Piece capturedPiece = BoardController.getSquare((square.getX() + x)/2, (square.getY()+y)/2).getPiece();
        boolean correctX = Math.abs(square.getX() - x) == 2;
        boolean correctY = Math.abs(square.getY() - y) == 2;
        boolean correctColor = (capturedPiece.getType().equals(Piece.Type.BLACK) && !isBlack)
                || (capturedPiece.getType().equals(Piece.Type.RED) && isBlack);
        if(correctX && correctY && correctColor) {
                return capturedPiece;
        }
        return null;
    }
}
