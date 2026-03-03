package gift.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NameValidator {
    private static final Pattern ALLOWED_PATTERN =
        Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ ()\\[\\]+\\-&/_]*$");

    private NameValidator() {
    }

    public static List<String> validate(String name, String label, int maxLength) {
        return validate(name, label, maxLength, false);
    }

    public static List<String> validate(String name, String label, int maxLength, boolean checkKakao) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add(label + " is required.");
            return errors;
        }

        if (name.length() > maxLength) {
            errors.add(label + " must be at most " + maxLength + " characters.");
        }

        if (!ALLOWED_PATTERN.matcher(name).matches()) {
            errors.add(label + " contains invalid special characters. Allowed: ( ) [ ] + - & / _");
        }

        if (checkKakao && name.contains("카카오")) {
            errors.add(label + " containing \"카카오\" requires approval from the MD team.");
        }

        return errors;
    }
}
