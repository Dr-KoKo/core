package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(2);

    }

    @Test
    void singletonClientUsePrototype2() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, AnotherClientBean.class, PrototypeBean.class);

        ClientBean clientBean = ac.getBean(ClientBean.class);
        int count1 = clientBean.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        AnotherClientBean anotherClientBean = ac.getBean(AnotherClientBean.class);
        int count2 = anotherClientBean.logic();
        Assertions.assertThat(count2).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototypeV2() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBeanV2.class, PrototypeBean.class);

        ClientBeanV2 clientBean = ac.getBean(ClientBeanV2.class);
        int count1 = clientBean.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBeanV2 clientBean2 = ac.getBean(ClientBeanV2.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototypeV3() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBeanV3.class, PrototypeBean.class);

        ClientBeanV3 clientBean = ac.getBean(ClientBeanV3.class);
        int count1 = clientBean.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBeanV3 clientBean2 = ac.getBean(ClientBeanV3.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototypeV3_2() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBeanV3_2.class, PrototypeBean.class);

        ClientBeanV3_2 clientBean = ac.getBean(ClientBeanV3_2.class);
        int count1 = clientBean.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBeanV3_2 clientBean2 = ac.getBean(ClientBeanV3_2.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);
    }


    @Scope
    @Component
    static class ClientBean{
        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope
    @Component
    static class AnotherClientBean{
        private final PrototypeBean prototypeBean;

        @Autowired
        public AnotherClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope
    @Component
    static class ClientBeanV2 {
        @Autowired
        private ApplicationContext ac;

        public int logic() {
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope
    @Component
    static class ClientBeanV3 {
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope
    @Component
    static class ClientBeanV3_2 {
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    @Component
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init: " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
