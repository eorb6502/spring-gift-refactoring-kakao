package gift.order;

import gift.member.Member;
import gift.member.MemberRepository;
import gift.option.Option;
import gift.option.OptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OptionService optionService;
    private final MemberRepository memberRepository;
    private final KakaoNotificationService kakaoNotificationService;

    public OrderService(
        OrderRepository orderRepository,
        OptionService optionService,
        MemberRepository memberRepository,
        KakaoNotificationService kakaoNotificationService
    ) {
        this.orderRepository = orderRepository;
        this.optionService = optionService;
        this.memberRepository = memberRepository;
        this.kakaoNotificationService = kakaoNotificationService;
    }

    public Page<Order> findByMemberId(Long memberId, Pageable pageable) {
        return orderRepository.findByMemberId(memberId, pageable);
    }

    public Order createOrder(Member member, Long optionId, int quantity, String message) {
        // subtract stock
        Option option = optionService.subtractQuantity(optionId, quantity);

        // deduct points
        var price = option.getProduct().getPrice() * quantity;
        member.deductPoint(price);
        memberRepository.save(member);

        // save order
        var saved = orderRepository.save(new Order(option, member.getId(), quantity, message));

        // best-effort kakao notification
        kakaoNotificationService.sendOrderNotification(member, saved, option);

        return saved;
    }
}
