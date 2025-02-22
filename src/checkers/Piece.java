//Billy York, SWE 2410-111

package checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * Represents a movable piece on the board.
 */
public class Piece {
    public enum Type {
        RED, BLACK
    }
    private final Type type;

    /**
     * The position of the piece on the board, where (0,0) is the top left corner
     * position, (BoardController.BOARD_WIDTH-1,0) is the top right corner
     * and (BOARD_WIDTH-1,BOARD_WIDTH-1) is the bottom right corner.
     */
    private int x, y;
    private final Ellipse ellipse;
    private final Ellipse crown;

    private IMoveBehavior moveBehavior;

    private boolean king;

    public Piece(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        ellipse = createEllipse();
        crown = createEllipse();
        crown.setVisible(false);
        this.king = false;
        this.moveBehavior = this.type.equals(Type.BLACK) ? new BlackMoveBehavior() : new RedMoveBehavior();
        setActive(false);
        reposition();
        BoardController.getSquare(x,y).placePiece(this);
    }

    private Ellipse createEllipse() {
        final Ellipse ellipse;
        ellipse = new Ellipse();
        ellipse.setRadiusX(25.0f);
        ellipse.setRadiusY(12.0f);
        ellipse.setStroke(Color.WHITE);
        if(this.type == Type.RED) {
            ellipse.setFill(Color.RED);
        } else if(this.type == Type.BLACK) {
            ellipse.setFill(Color.BLACK);
        } else {
            throw new IllegalArgumentException("Unknown type:"+type);
        }
        ellipse.setOnMouseClicked(event -> trySetActive());
        BoardController.addChild(ellipse);
        return ellipse;
    }

    public String toString() {
        return "Piece at "+x+", "+y;
    }

    public Type getType() {
        return type;
    }

    private void reposition() {
        double layoutX = this.x * BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2;
        double layoutY = this.y * BoardController.SQUARE_SIZE + BoardController.SQUARE_SIZE/2;
        ellipse.setLayoutX(layoutX);
        ellipse.setLayoutY(layoutY);
        if (king) {
            crown.setLayoutX(layoutX);
            crown.setLayoutY(layoutY - 10);
        }
    }

    private void trySetActive() {
        BoardController.trySetActive(this);
    }

    public void setActive(boolean isActive) {
        if(isActive) {
            ellipse.setStrokeWidth(3);
            crown.setStrokeWidth(3);
        } else {
            ellipse.setStrokeWidth(1);
            crown.setStrokeWidth(1);
        }
    }

    /**
     * If possible, perform either an ordinary or capture move to the given square.
     *
     * (Report the problem to the user if not possible)
     * @param square the square to which the move will be made if possible.
     */
    public void tryMove(Square square) {
        if(square.getPiece() != null) {
            BoardController.setMessage("That location is already occupied!\n" +
                    "Please select a different location or piece.");
        } else {
            if (isValidOrdinaryMove(square)) {
                move(square);
            } else if (isValidCapture(square)) {
                captureMoveTo(square);
            } else {
                BoardController.setMessage("The piece can neither move nor capture to that position" +
                        ".\nPlease try a " +
                        "different " +
                        "square.");
            }
        }
    }

    /**
     * Perform an ordinary move.  Move this piece to the new position and switch turns.
     *
     * Preconditions:
     * The move must be valid -- a valid unoccupied square must be provided.
     *
     * @param square the position to which this piece will be moved.
     */
    private void move(Square square) {
        BoardController.getSquare(x,y).removePiece();
        placeOnSquare(square);
        BoardController.switchTurns();
        setActive(false);
        if(type.equals(Type.BLACK) && y == 0) {
            king = true;
            this.moveBehavior = new KingMoveBehavior(true);
            crown.setVisible(true);
            reposition();
        } else if (type.equals(Type.RED) && y == BoardController.BOARD_WIDTH - 1) {
            king = true;
            this.moveBehavior = new KingMoveBehavior(false);
            crown.setVisible(true);
            reposition();
        }

    }

    private void placeOnSquare(Square square) {
        this.x = square.getX();
        this.y = square.getY();
        BoardController.getSquare(x,y).placePiece(this);
        reposition();
    }

    /**
     * Perform a capture move.  Identify the piece to be captured
     * and move to that square. Remove the captured piece.
     *
     * Preconditions:
     * The move must be valid -- the place moved to must exist and there must be a piece to capture.
     *
     * @param square A square to which this piece is able to move and capture at the same time.
     * @throws  IllegalArgumentException If no capture is made by moving to the square.
     */
    private void captureMoveTo(Square square) {
        Piece captured = getCapturedPiece(square);
        if(captured == null) {
            throw new IllegalArgumentException("Cannot capture by moving to "+square);
        }
        captured.removeSelf();
        move(square);
    }

    /**
     * Removes this piece from the board.
     */
    public void removeSelf() {
        BoardController.getSquare(x,y).removePiece();
        BoardController.removeChild(ellipse);
        BoardController.removeChild(crown);
    }

    /**
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square and capture another
     *    piece at the same time.
     */
    public boolean isValidOrdinaryMove(Square square) {
        return this.moveBehavior.isValidOrdinaryMove(square, this.x, this.y);
    }

    /**
     * @param square The square to which this piece will move
     * @return true if this piece can move to that square and capture another
     *    piece at the same time.
     */
    private boolean isValidCapture(Square square) {
        return getCapturedPiece(square) != null;
    }

    /**
     * Find the piece that would be captured by moving this piece to a given square.
     *
     * The piece is not actually captured when calling this method.
     * It is simply identified by calling this method.
     *
     * @param square The square to which a move will be mode
     * @return null if the move cannot be made.
     *      Otherwise, return the piece that would be removed by moving to that square.
     */
    public Piece getCapturedPiece(Square square) {
        return this.moveBehavior.getCapturedPiece(square, this.x, this.y);
    }
}

