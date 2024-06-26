package net.developia.mini1st;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

import net.developia.mini1st.service.ProductsService;

@Component
public class CrawlingProducts {
	private ProductsService service;
	
	@Autowired
	public CrawlingProducts(ProductsService service) {
		this.service = service;
	}
	// 현재 크롤링한 데이터 지워지는 현상 있어서 스케줄 꺼놓음
	//@Scheduled(cron = "0 0 2 * * ?") // 매일 오전 2시에 실행
	//@Scheduled(fixedRate = 60000 * 5) // 매분마다 실행 (1분 = 60,000 밀리초)
    public void crawlAndStoreData() {
        System.out.println("Scheduled Crawling Task...");
        // 크롤링 및 데이터베이스에 저장하는 메서드 호출
        service.createProductList();
    }
    
    //CrawlingProducts 클래스에서 스케줄링을 테스트하기 위한 추가 메서드
    public void executeCrawlAndStoreData() {
        crawlAndStoreData();
    }
}