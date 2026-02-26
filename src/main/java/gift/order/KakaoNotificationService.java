package gift.order;

import gift.member.Member;
import gift.option.Option;
import org.springframework.stereotype.Service;

@Service
public class KakaoNotificationService {
    private final KakaoMessageClient kakaoMessageClient;

    public KakaoNotificationService(KakaoMessageClient kakaoMessageClient) {
        this.kakaoMessageClient = kakaoMessageClient;
    }

    public void sendOrderNotification(Member member, Order order, Option option) {
        if (member.getKakaoAccessToken() == null) {
            return;
        }
        try {
            var product = option.getProduct();
            kakaoMessageClient.sendToMe(member.getKakaoAccessToken(), order, product);
        } catch (Exception ignored) {
        }
    }
}
