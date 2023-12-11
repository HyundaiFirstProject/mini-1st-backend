package net.developia.mini1st;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrawlTest2 {
	public static void main(String[] args) {
		 // WebDriver 설정 (ChromeDriver 사용 예제)
        System.setProperty("webdriver.chrome.driver", "C:/dev/chromedriver-win64/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // 크롤링할 웹 페이지 열기
        driver.get("https://www.thehyundai.com/front/dpa/sbSect.thd?sectId=192766");
        
        // WebDriver를 사용하여 페이지가 완전히 로드될 때까지 기다리기
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));


        // 예제: 웹 페이지에서 특정 요소 찾기
        // 찾은 요소의 텍스트 출력
        List<WebElement> elements = driver.findElements(By.cssSelector("div.prod-unit"));

        for (WebElement element : elements) {
        	String imageUrl = element.findElement(By.cssSelector("div.img img")).getAttribute("src");
            System.out.println("Image URL: " + imageUrl);

            // 제품명 추출
            String productName = element.findElement(By.cssSelector("div.info a.title")).getText();
            System.out.println("Product Name: " + productName);

            // 링크 추출
            String link = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].getElementsByTagName('a')[0].getAttribute('href');", element);
            System.out.println("Link: " + link);

            System.out.println(); // 각 제품 정보를 구분하기 위한 빈 줄 추가
        }

        // WebDriver 종료
        driver.quit();
	}
	
}
