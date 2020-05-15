package g49262.stratego.model;

/**
 * The color of the player.
 *
 * @author Meihdi El Amouri
 */
public enum PlayerColor {
    RED("RED"), // The red player
    BLUE("BLUE"); // the blue player

    String value;

    private PlayerColor(String aValue) {
        this.value = aValue;
    }

    public String getValue() {
        return this.value;
    }
}
