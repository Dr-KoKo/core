package hello.core.twoBeanInjection;

import hello.core.AutoAppConfig;
import hello.core.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TwoBeanInjectionTest {

    @Test
    void primary(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        OrderService orderService = ac.getBean(OrderService.class);
    }

}
