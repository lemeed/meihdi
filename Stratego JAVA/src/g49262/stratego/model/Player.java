package g49262.stratego.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The player of the game.
 *
 * @author Meihdi El Amouri
 */
public class Player {

    public PlayerColor color;
    public List<Piece> pieces;

    /**
     * This is the constructor of the player.
     *
     * @param aColor The color of the player.
     */
    public Player(PlayerColor aColor) {
        if (aColor == null) {
            throw new NullPointerException("the color of player is null");
        }
        this.color = aColor;
        this.pieces = new ArrayList<>();
    }

    /**
     * This is the getter of Color.
     *
     * @return return the color of the player.
     */
    public PlayerColor getColor() {

        return color;
    }

    /**
     * this is the getter of the list of pieces.
     *
     * @return the list of piece of player.
     */
    public List<Piece> getPieces() {
        return pieces;
    }

    /**
     * Allows to add a piece in the list pieces of player.
     *
     * @param piece the piece who want to add.
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    /**
     * Allows to remove the piece.
     *
     * @param piece the piece that we want to remove.
     */
    public void removePiece(Piece piece) {
        if (getPieces().get(0).getRank() == piece.getRank()) {
            getPieces().remove(0);
        } else if (getPieces().get(1).getRank() == piece.getRank()) {
            getPieces().remove(1);
        }
    }

    /**
     * Allows to know if the player has flag.
     *
     * @return true if he has a flag
     */
    public boolean hasFlag() {

        boolean hasFlag = false;

        if (pieces.get(0).getRank() == 0) {

            hasFlag = true;
        } else if (pieces.size() > 1 && pieces.get(1).getRank() == 0) {
            hasFlag = true;
        }

        return hasFlag;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.color);
        hash = 97 * hash + Objects.hashCode(this.pieces);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.color != other.color) {
            return false;
        }
        return Objects.equals(this.pieces, other.pieces);
    }

}
