package ressources;

/**
 *
 * @author g49262
 */
public enum LetterFrequency {
    A('A', 8.40),
    B('B', 1.06),
    C('C', 3.03),
    D('D', 4.18),
    E('E', 17.26),
    F('F', 1.12),
    G('G', 1.27),
    H('H', 0.92),
    I('I', 7.34),
    J('J', 0.31),
    K('K', 0.05),
    L('L', 6.01),
    M('M', 2.96),
    N('N', 7.13),
    O('O', 5.26),
    P('P', 3.01),
    Q('Q', 0.99),
    R('R', 6.55),
    S('S', 8.08),
    T('T', 7.07),
    U('U', 5.74),
    V('V', 1.32),
    W('W', 0.04),
    X('X', 0.45),
    Y('Y', 0.30),
    Z('Z', 0.12);

    private final double frequency;
    private final char character;

    private LetterFrequency(char character, double value) {
        this.frequency = value;
        this.character = character;
    }

    public double getFrequency() {
        return frequency;
    }

    public char getCharacter() {
        return character;
    }
}