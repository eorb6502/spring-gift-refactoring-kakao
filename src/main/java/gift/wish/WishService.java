package gift.wish;

import gift.auth.ForbiddenException;
import gift.product.Product;
import gift.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    public Page<Wish> findByMemberId(Long memberId, Pageable pageable) {
        return wishRepository.findByMemberId(memberId, pageable);
    }

    public Optional<Wish> findByMemberIdAndProductId(Long memberId, Long productId) {
        return wishRepository.findByMemberIdAndProductId(memberId, productId);
    }

    public Wish addWish(Long memberId, Long productId) {
        Product product = productService.findById(productId);
        return wishRepository.save(new Wish(memberId, product));
    }

    public void removeWish(Long memberId, Long wishId) {
        Wish wish = wishRepository.findById(wishId)
            .orElseThrow(() -> new NoSuchElementException("Wish not found. id=" + wishId));

        if (!wish.getMemberId().equals(memberId)) {
            throw new ForbiddenException("Cannot delete another member's wish.");
        }

        wishRepository.delete(wish);
    }
}
