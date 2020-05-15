package g49262.stratego.model;

import static g49262.stratego.model.PlayerColor.BLUE;
import static g49262.stratego.model.PlayerColor.RED;
import g49262.stratego.model.pieces.Bomb;
import g49262.stratego.model.pieces.Eclaireur;
import g49262.stratego.model.pieces.Flag;
import g49262.stratego.model.pieces.General;
import g49262.stratego.model.pieces.Marechal;
import g49262.stratego.model.pieces.Miner;
import g49262.stratego.model.pieces.Spy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The logic of the game stratego.
 *
 * @author Meihdi El Amouri
 */
public class Game implements Model {

    Board board;
    Player current;
    Player opponent;
    private boolean initialize;
    Position selected;

    /**
     * this is the constructor of Game .
     */
    public Game() {
        this.board = new Board();
        this.current = new Player(PlayerColor.RED);
        this.opponent = new Player(PlayerColor.BLUE);
        this.initialize = false;

    }

    /**
     * Allows to initialize the board with some pieces.
     */
    @Override
    public void initialize() {

        Piece piece1 = new Flag(RED);
        Piece piece2 = new General(RED);
        Piece piece3 = new Bomb(RED);
        Piece piece4 = new Miner(RED);
        Piece piece5 = new Flag(BLUE);
        Piece piece6 = new General(BLUE);
        Piece piece7 = new Bomb(BLUE);
        Piece piece8 = new Miner(BLUE);
        Piece piece9 = new Eclaireur(BLUE);
        Piece piece10 = new Eclaireur(RED);
        Piece piece11 = new Marechal(BLUE);
        Piece piece12 = new Marechal(RED);
        Piece piece13 = new Spy(BLUE);
        Piece piece14 = new Spy(RED);

        Position p = new Position(0, 1);
        Position p2 = new Position(3, 2);
        Position p3 = new Position(4, 2);
        Position p4 = new Position(4, 1);
        Position p5 = new Position(1, 0);
        Position p6 = new Position(1, 2);
        Position p7 = new Position(3, 1);
        Position p8 = new Position(2, 0);
        Position p9 = new Position(5, 4);
        Position p10 = new Position(0, 5);
        Position p11 = new Position(6, 0);
        Position p12 = new Position(6, 1);
        Position p13 = new Position(5, 3);
        Position p14 = new Position(3, 4);

        board.getSquare(p).put(piece1);
        board.getSquare(p2).put(piece2);
        board.getSquare(p3).put(piece5);
        board.getSquare(p4).put(piece6);
        board.getSquare(p5).put(piece3);
        board.getSquare(p6).put(piece4);
        board.getSquare(p7).put(piece7);
        board.getSquare(p8).put(piece8);
        board.getSquare(p9).put(piece9);
        board.getSquare(p10).put(piece10);
        board.getSquare(p11).put(piece11);
        board.getSquare(p12).put(piece14);
        board.getSquare(p13).put(piece13);
        board.getSquare(p14).put(piece12);

        current.addPiece(piece1);
        current.addPiece(piece2);
        current.addPiece(piece4);
        current.addPiece(piece3);
        current.addPiece(piece10);
        current.addPiece(piece12);
        current.addPiece(piece14);
        
        opponent.addPiece(piece5);
        opponent.addPiece(piece6);
        opponent.addPiece(piece7);
        opponent.addPiece(piece8);
        opponent.addPiece(piece9);
        opponent.addPiece(piece11);
        opponent.addPiece(piece13);
         
        this.initialize = true;
    }

    /**
     * Allows to start the game.
     */
    @Override
    public void start() {

        if (isOver() || !initialize) {
            throw new IllegalStateException("The board is maybe not initialize or the game is over");
        }
    }

    /**
     * allows to know if the game is over.
     *
     * @return false if the game is not over.
     */
    @Override
    public boolean isOver() {
        return !hasMoves(this.current)
                || !this.current.hasFlag()
                || !hasMoves(this.opponent)
                || !this.opponent.hasFlag();

    }

    /**
     * Allows to get the board.
     *
     * @return the board of Stratego.
     */
    @Override
    public Square[][] getBoard() {
        return board.getSquares();
    }

    /**
     * Allows to select a piece.
     *
     * @param row the row of the board.
     * @param column the column of the board.
     */
    @Override
    public void select(int row, int column) {

        Position p = new Position(row, column);
        if (!board.isInside(p)) {
            throw new IllegalArgumentException("the position is not good");
        } else if (board.isFree(p) || !board.isMyOwn(p, current.getColor())) {
            throw new IllegalStateException("There is no piece to select");
        }
        this.selected = p;

    }

    /**
     * Allows to get the piece selected.
     *
     * @return the piece selected.
     */
    @Override
    public Piece getSelected() {
        if (board.squares[selected.getRow()][selected.getColumn()].getPiece() == null) {
            throw new NullPointerException("there is no piece in this square");
        }
        return board.squares[selected.getRow()][selected.getColumn()].getPiece();
    }

    /**
     * This is the getter of all possibles moves.
     *
     * @return all possibles moves in a list.
     */
    @Override
    public List<Move> getMoves() {
        if (this.selected == null) {
            throw new NullPointerException("There is no selected piece");
        }

        List<Move> movePossible = new ArrayList<>(4);

        if (getSelected().getNbSteps() >= 1) {
            for (Direction dir : Direction.values()) {
                Position pStart = new Position(selected.getRow(), selected.getColumn());
                Position pEnd = pStart.next(dir);

                if (board.isInside(pEnd) && !board.getSquare(pEnd).isMyOwn(current.color) && getSelected().canCross(board.getSquare(pEnd))) {
                    Move m = new Move(getSelected(), pStart, pEnd);
                    movePossible.add(m);
                }
            }
        }

        if (getSelected().getNbSteps() == 2) {
            for (Direction dir : Direction.values()) {
                Position pStart = new Position(selected.getRow(), selected.getColumn());
                Position pEndFirst = pStart.next(dir);
                Position pEnd = pEndFirst.next(dir);
                if (board.isInside(pEnd) && !board.getSquare(pEnd).isMyOwn(current.color) && getSelected().canCross(board.getSquare(pEnd))) {
                    Move m = new Move(getSelected(), pStart, pEnd);
                    movePossible.add(m);
                }
            }
        }

        return movePossible;
    }

    /**
     * Allows to apply a move.
     *
     * @param move the move that the player want to do.
     */
    @Override
    public void apply(Move move) {
        if (move == null) {
            throw new NullPointerException("They are no move");
        }

        board.remove(move.getStart());
        if (board.getSquare(move.getEnd()).isFree()) {

            board.getSquare(move.getEnd()).put(move.getPiece());
        } else {
            fight(move);
        }
        swapPlayers();
    }

    /**
     * Allows to know who win during a fight.
     *
     * @param move
     */
    private void fight(Move move) {

        if (move.getPiece().isStronger(board.getSquare(move.getEnd()).getPiece())) {

            opponent.removePiece(getBoard()[move.getEnd().row][move.getEnd().column].getPiece());
            board.remove(move.getEnd());
            board.getSquare(move.getEnd()).put(move.getPiece());

        } else if (move.getPiece().hasSameRank(board.getSquare(move.getEnd()).getPiece())) {
            opponent.removePiece(board.getSquare(move.getEnd()).getPiece());
            current.removePiece(move.getPiece());
            board.remove(move.getEnd());

        } else {
            current.removePiece(move.getPiece());
        }
    }

    /**
     * Allows to swap the current players.
     */
    private void swapPlayers() {
        if (hasMoves(opponent)) {
            PlayerColor temp = current.getColor();
            List<Piece> tempList = current.getPieces();
            current.color = opponent.getColor();
            current.pieces = opponent.getPieces();
            opponent.color = temp;
            opponent.pieces = tempList;

        }
    }

    /**
     * Allows to know if the player has possibles moves.
     *
     * @param player the player in the game.
     * @return true if he has possibles moves.
     */
    private boolean hasMoves(Player player) {
        List<Position> PositionsPieces = board.getTakenSquare(player);
        Position temp = selected;
        for (Position position : PositionsPieces) {

            selected = new Position(position.getRow(), position.getColumn());
            List<Move> moves = getMoves();

            if (moves.size() > 0) {
                this.selected = temp;
                return true;
            }

        }
        this.selected = temp;
        return false;
    }

    /**
     * Allows to get the current player.
     *
     * @return the current player.
     */
    @Override
    public Player getCurrent() {
        return current;
    }

    /**
     * Allows to know who's the winner.
     *
     * @return the winner(s) in a list.
     */
    @Override
    public List<Player> getWinners() {

        if (!isOver()) {
            throw new IllegalStateException("The game is not over yet");
        }
        List<Player> winners = new ArrayList<>(2);

        if (!current.hasFlag() && opponent.hasFlag()) {
            winners.add(opponent);
        } else if (!opponent.hasFlag() && opponent.hasFlag()) {
            winners.add(current);
        } else {
            winners.add(current);
            winners.add(opponent);
        }

        return winners;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (!Objects.equals(this.board, other.board)) {
            return false;
        }
        if (!Objects.equals(this.current, other.current)) {
            return false;
        }
        if (!Objects.equals(this.opponent, other.opponent)) {
            return false;
        }
        return this.initialize == other.initialize;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.board);
        hash = 41 * hash + Objects.hashCode(this.current);
        hash = 41 * hash + Objects.hashCode(this.opponent);
        hash = 41 * hash + (this.initialize ? 1 : 0);
        hash = 41 * hash + Objects.hashCode(this.selected);
        return hash;
    }

}
