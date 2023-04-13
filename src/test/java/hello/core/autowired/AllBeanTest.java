package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        Member member = new Member(1L, "userA", Grade.VIP);
        DiscountService discountService = ac.getBean(DiscountService.class);

        int fixDiscount = discountService.discount(member, 20000, "fixDiscountPolicy");
        int rateDiscount = discountService.discount(member, 20000, "rateDiscountPolicy");

        Assertions.assertThat(fixDiscount).isEqualTo(1000);
        Assertions.assertThat(rateDiscount).isEqualTo(2000);

    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> discountPolicyMap;
        private final List<DiscountPolicy> discountPolicyList;

        public DiscountService(Map<String, DiscountPolicy> discountPolicyMap, List<DiscountPolicy> discountPolicyList) {
            this.discountPolicyMap = discountPolicyMap;
            this.discountPolicyList = discountPolicyList;
        }

        public int discount(Member member, int price, String policyName) {
            DiscountPolicy discountPolicy = discountPolicyMap.get(policyName);
            return discountPolicy.discount(member, price);
        }

    }
}
