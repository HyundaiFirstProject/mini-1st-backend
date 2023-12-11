package net.developia.mini1st;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class AutoCrawling {
	public static void main(String[] args) {
		System.out.println("AutoCrawling 호출....");
		// XML 설정 파일을 사용하여 ApplicationContext 생성
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("scheduling-context.xml")){
            // 애플리케이션 종료 시 자원 정리를 위해 등록
            context.registerShutdownHook();
        }
	}
}
