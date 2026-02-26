package gift.option;

import gift.common.NameValidator;
import gift.product.Product;
import gift.product.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Each product must have at least one option at all times.
 * Option names are validated against allowed characters and length constraints.
 */
@RestController
@RequestMapping(path = "/api/products/{productId}/options")
public class OptionController {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionController(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable Long productId) {
        productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("Product not found. id=" + productId));
        List<OptionResponse> options = optionRepository.findByProductId(productId).stream()
            .map(OptionResponse::from)
            .toList();
        return ResponseEntity.ok(options);
    }

    @PostMapping
    public ResponseEntity<OptionResponse> createOption(
        @PathVariable Long productId,
        @Valid @RequestBody OptionRequest request
    ) {
        validateName(request.name());

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("Product not found. id=" + productId));

        if (optionRepository.existsByProductIdAndName(productId, request.name())) {
            throw new IllegalArgumentException("Option name already exists.");
        }

        Option saved = optionRepository.save(new Option(product, request.name(), request.quantity()));
        URI location = URI.create("/api/products/" + productId + "/options/" + saved.getId());
        return ResponseEntity.created(location)
            .body(OptionResponse.from(saved));
    }

    @DeleteMapping(path = "/{optionId}")
    public ResponseEntity<Void> deleteOption(
        @PathVariable Long productId,
        @PathVariable Long optionId
    ) {
        productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("Product not found. id=" + productId));

        List<Option> options = optionRepository.findByProductId(productId);
        if (options.size() <= 1) {
            throw new IllegalArgumentException("Cannot delete the last option of a product.");
        }

        Option option = optionRepository.findById(optionId)
            .orElseThrow(() -> new NoSuchElementException("Option not found. id=" + optionId));
        if (!option.getProduct().getId().equals(productId)) {
            throw new NoSuchElementException("Option not found. id=" + optionId);
        }

        optionRepository.delete(option);
        return ResponseEntity.noContent().build();
    }

    private void validateName(String name) {
        List<String> errors = NameValidator.validate(name, "Option name", 50);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}
