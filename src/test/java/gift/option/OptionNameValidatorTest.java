package gift.option;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OptionNameValidatorTest {

    @Nested
    @DisplayName("유효한 이름")
    class ValidNames {

        @ParameterizedTest
        @ValueSource(strings = {"옵션A", "Option 1", "블루 / 256GB", "사이즈(L)", "색상[레드]", "A+B", "A-B", "A&B", "A_B"})
        @DisplayName("허용된 문자로 구성된 이름은 에러가 없다")
        void validNames(String name) {
            List<String> errors = OptionNameValidator.validate(name);

            assertThat(errors).isEmpty();
        }

        @Test
        @DisplayName("최대 50자까지 허용된다")
        void exactlyMaxLength() {
            String name = "a".repeat(50);
            List<String> errors = OptionNameValidator.validate(name);

            assertThat(errors).isEmpty();
        }
    }

    @Nested
    @DisplayName("null 또는 빈 문자열")
    class NullOrBlank {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("null이나 빈 문자열이면 에러가 발생한다")
        void nullOrEmpty(String name) {
            List<String> errors = OptionNameValidator.validate(name);

            assertThat(errors).isNotEmpty();
            assertThat(errors.get(0)).contains("required");
        }

        @Test
        @DisplayName("공백만으로 구성된 이름이면 에러가 발생한다")
        void blankName() {
            List<String> errors = OptionNameValidator.validate("   ");

            assertThat(errors).isNotEmpty();
            assertThat(errors.get(0)).contains("required");
        }
    }

    @Nested
    @DisplayName("길이 초과")
    class ExceedsMaxLength {

        @Test
        @DisplayName("51자 이상이면 길이 초과 에러가 발생한다")
        void exceedsMaxLength() {
            String name = "a".repeat(51);
            List<String> errors = OptionNameValidator.validate(name);

            assertThat(errors).anyMatch(e -> e.contains("50 characters"));
        }
    }

    @Nested
    @DisplayName("허용되지 않는 특수 문자")
    class InvalidCharacters {

        @ParameterizedTest
        @ValueSource(strings = {"옵션!", "옵션@", "옵션#", "옵션$", "옵션%"})
        @DisplayName("허용되지 않는 특수 문자가 포함되면 에러가 발생한다")
        void invalidSpecialChars(String name) {
            List<String> errors = OptionNameValidator.validate(name);

            assertThat(errors).anyMatch(e -> e.contains("invalid special characters"));
        }
    }

    @Nested
    @DisplayName("복합 에러")
    class MultipleErrors {

        @Test
        @DisplayName("길이 초과와 허용되지 않는 문자를 동시에 검증한다")
        void lengthAndCharacterErrors() {
            String name = "!".repeat(51);
            List<String> errors = OptionNameValidator.validate(name);

            assertThat(errors).hasSizeGreaterThanOrEqualTo(2);
        }
    }
}
