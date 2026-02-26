package gift.product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ProductNameValidator {
    private static final int MAX_LENGTH = 15;
    private static final Pattern ALLOWED_PATTERN =
        Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ ()\\[\\]+\\-&/_]*$");

    private ProductNameValidator() {
    }

    public static List<String> validate(String name) {
        return validate(name, false);
    }

    public static List<String> validate(String name, boolean allowKakao) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("Product name is required.");
            return errors;
        }

        if (name.length() > MAX_LENGTH) {
            errors.add("Product name must be at most 15 characters.");
        }

        if (!ALLOWED_PATTERN.matcher(name).matches()) {
            errors.add("Product name contains invalid special characters. Allowed: ( ) [ ] + - & / _");
        }

        if (!allowKakao && name.contains("카카오")) {
            errors.add("Product name containing \"카카오\" requires approval from the MD team.");
        }

        return errors;
    }
}
