package gift.option;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
 * Validates option names against the following rules:
 * - Must not be null or blank
 * - Maximum length of 50 characters (including spaces)
 * - Only Korean, English, digits, spaces, and selected special characters are allowed: ( ) [ ] + - & / _
 */
public class OptionNameValidator {
    private static final int MAX_LENGTH = 50;
    private static final Pattern ALLOWED_PATTERN =
        Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ ()\\[\\]+\\-&/_]*$");

    private OptionNameValidator() {
    }

    public static List<String> validate(String name) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("Option name is required.");
            return errors;
        }

        if (name.length() > MAX_LENGTH) {
            errors.add("Option name must be at most 50 characters.");
        }

        if (!ALLOWED_PATTERN.matcher(name).matches()) {
            errors.add("Option name contains invalid special characters. Allowed: ( ) [ ] + - & / _");
        }

        return errors;
    }
}
