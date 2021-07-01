import java.util.Arrays;
import java.util.Objects;

class WordNetValidator {
    static void validateNotNull(Object... o) {
        if(Arrays.stream(o).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }
}
