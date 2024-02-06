package checkers;

public interface IMoveBehavior {

    boolean isValidOrdinaryMove(Square square, int x, int y);

    Piece getCapturedPiece(Square square, int x, int y);
}
