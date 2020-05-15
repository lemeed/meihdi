package g49262.stratego.model;

/**
 * This class allows to represent the type of the square.
 *
 * @author Meihdi El Amouri
 */
public enum SquareType {
    LAND("LAND"), //The square is a land.
    WATER("WATER"); //the square contains water.

    private String value;

    private SquareType(String value) {
        this.value = value;
    }

    /**
     * Allows to know the value of the square
     * @return the value of the square.
     */
    public String getValue() {
        return value;
    }

}
