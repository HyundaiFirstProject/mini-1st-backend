package net.developia.mini1st;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrawlTest2 {
	public static void main(String[] args) {
		// 1. WebDriver와 ChromeDriver 설정
		// 프로젝트 폴더 기준으로 chromedirver.exe 파일의 위치를 작성
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
	
		
		// 2. 웹 페이지 접속
		String baseUrl = "https://www.thehyundai.com/front/dpa/sbSect.thd?sectId=192766";
		driver.get(baseUrl);
		
		// 3. 데이터 추출
		List<WebElement> prodUnits = driver.findElements(By.className("prod-unit"));

        for (WebElement prodUnit : prodUnits) {
            // (1) 이미지
            WebElement imgElement = prodUnit.findElement(By.cssSelector("div.img img"));
            String imageUrl = imgElement.getAttribute("src");

            // (2) 제품명
            WebElement titleElement = prodUnit.findElement(By.cssSelector("div.info a.title"));
            String productName = titleElement.getText();

            // (3) 제품 링크
            WebElement productLinkElement = prodUnit.findElement(By.cssSelector("div.img a"));
            String productLink = productLinkElement.getAttribute("href");

            // 출력
            System.out.println("Image URL: " + imageUrl);
            System.out.println("Product Name: " + productName);
            System.out.println("Product Link: " + productLink);
            System.out.println("--------");
        }
		
		// 4. WebDriver 종료
		driver.quit();
	}
	
}
