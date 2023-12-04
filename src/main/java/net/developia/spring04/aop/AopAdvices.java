package net.developia.spring04.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AopAdvices {

    @Before("execution(* net.developia.spring04.controller.SampleController.getSample*(..))")
    public void ad_before() {
        log.info("★★★★★★★★★★★");
        log.info("★   before advice  ★");
        log.info("★★★★★★★★★★★");
    }
}
