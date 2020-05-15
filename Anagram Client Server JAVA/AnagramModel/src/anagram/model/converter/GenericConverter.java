package anagram.model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;

/**
 * A generic class converter. Turns some class I to a class O.
 *
 * @param <I> the input Class to change.
 * @param <O> the result of the conversion.
 */
public interface GenericConverter<I, O> extends Function<I, O> {

    /**
     * Turns some Class I to Class O.
     *
     * @param input input class.
     * @return a converted class.
     */
    default O convert(final I input) {
        O output = null;
        if (input != null) {
            output = this.apply(input);
        }
        return output;
    }

    /**
     * Turns some List of Class I to a list of Class O.
     *
     * @param input list of input class.
     * @return a list of converted class.
     */
    default List<O> convert(final List<I> input) {
        List<O> output = new ArrayList<>();
        if (input != null) {
            output = input.stream().map(this::apply).collect(toList());
        }
        return output;
    }
}
