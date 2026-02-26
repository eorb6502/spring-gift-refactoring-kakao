package gift.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNameValidatorTest {

    @Nested
    @DisplayName("유효한 이름")
    class ValidNames {

        @ParameterizedTest
        @ValueSource(strings = {"상품", "Product", "상품123", "상품 (A)", "테스트[1]", "A+B", "A-B", "A&B", "A/B", "A_B"})
        @DisplayName("허용된 문자로 구성된 이름은 에러가 없다")
        void validNames(String name) {
            List<String> errors = ProductNameValidator.validate(name);

            assertThat(errors).isEmpty();
        }

        @Test
        @DisplayName("최대 15자까지 허용된다")
        void exactlyMaxLength() {
            String name = "가나다라마바사아자차카타파";  // 13자
            List<String> errors = ProductNameValidator.validate(name);

            assertThat(errors).isEmpty();
        }

        @Test
        @DisplayName("공백을 포함한 15자 이름도 허용된다")
        void maxLengthWithSpaces() {
            String name = "가 나 다 라 마 바 사아"; // 15자 (공백 포함)
            List<String> errors = ProductNameValidator.validate(name);

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
            List<String> errors = ProductNameValidator.validate(name);

            assertThat(errors).isNotEmpty();
            assertThat(errors.get(0)).contains("required");
        }

        @Test
        @DisplayName("공백만으로 구성된 이름이면 에러가 발생한다")
        void blankName() {
            List<String> errors = ProductNameValidator.validate("   ");

            assertThat(errors).isNotEmpty();
            assertThat(errors.get(0)).contains("required");
        }
    }

    @Nested
    @DisplayName("길이 초과")
    class ExceedsMaxLength {

        @Test
        @DisplayName("16자 이상이면 길이 초과 에러가 발생한다")
        void exceedsMaxLength() {
            String name = "가나다라마바사아자차카타파하거너"; // 16자
            List<String> errors = ProductNameValidator.validate(name);

            assertThat(errors).anyMatch(e -> e.contains("15 characters"));
        }
    }

    @Nested
    @DisplayName("허용되지 않는 특수 문자")
    class InvalidCharacters {

        @ParameterizedTest
        @ValueSource(strings = {"상품!!", "상품@", "상품#", "상품$", "상품%", "상품^"})
        @DisplayName("허용되지 않는 특수 문자가 포함되면 에러가 발생한다")
        void invalidSpecialChars(String name) {
            List<String> errors = ProductNameValidator.validate(name);

            assertThat(errors).anyMatch(e -> e.contains("invalid special characters"));
        }
    }

    @Nested
    @DisplayName("카카오 포함 검증")
    class KakaoRestriction {

        @Test
        @DisplayName("카카오가 포함된 이름은 기본적으로 에러가 발생한다")
        void kakaoNotAllowedByDefault() {
            List<String> errors = ProductNameValidator.validate("카카오선물");

            assertThat(errors).anyMatch(e -> e.contains("카카오"));
        }

        @Test
        @DisplayName("allowKakao가 true이면 카카오가 포함되어도 에러가 없다")
        void kakaoAllowedWhenFlagTrue() {
            List<String> errors = ProductNameValidator.validate("카카오선물", true);

            assertThat(errors).isEmpty();
        }

        @Test
        @DisplayName("allowKakao가 false이면 카카오가 포함된 이름에서 에러가 발생한다")
        void kakaoNotAllowedWhenFlagFalse() {
            List<String> errors = ProductNameValidator.validate("카카오선물", false);

            assertThat(errors).anyMatch(e -> e.contains("카카오"));
        }
    }

    @Nested
    @DisplayName("복합 에러")
    class MultipleErrors {

        @Test
        @DisplayName("길이 초과와 허용되지 않는 문자를 동시에 검증한다")
        void lengthAndCharacterErrors() {
            String name = "abcdefghijklmnop!"; // 17자 + 특수문자
            List<String> errors = ProductNameValidator.validate(name);

            assertThat(errors).hasSizeGreaterThanOrEqualTo(2);
        }
    }
}
