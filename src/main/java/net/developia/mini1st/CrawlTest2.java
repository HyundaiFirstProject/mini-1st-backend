package net.developia.mini1st;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrawlTest2 {
	private static WebDriver driver;
	static final String WEB_DRIVER_PATH = "C:/ITNE/chromedriver-win64";

	public static void main(String[] args) {
		// 현재 package의 workspace 경로, Windows는 [ chromedriver.exe ]
        Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver");  // 현재 package의
        
        // WebDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", path.toString());
        
        // WebDriver 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");          // 전체화면으로 실행
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함
        
        // WebDriver 객체 생성
        ChromeDriver driver = new ChromeDriver( options );
        
        // 빈 탭 생성
        driver.executeScript("window.open('about:blank','_blank');");
        
        // 탭 목록 가져오기
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        
        
        
        // 첫번째 탭으로 전환
        driver.switchTo().window(tabs.get(0));
        
        // 웹페이지 요청
        driver.get("https://heodolf.tistory.com/101");
        
        // 웹페이지에서 글제목 가져오기
       // WebElement page1_title = driver.findElementByXPath("//*[@id=\"content\"]/div[1]/div[1]/div/h1");
       // if( page1_title != null  ) {
        //    System.out.println( page1_title.getText() );            
        //}
        // 웹페이지 소스 출력
        //System.out.println( driver.getPageSource() );
        
        // 탭 종료
        driver.close();
        
        
        
        // 두번째 탭으로 전환
        driver.switchTo().window(tabs.get(1));
        
        // 웹페이지 요청
        driver.get("https://heodolf.tistory.com/102");
        
        // 웹페이지에서 글제목 가져오기
//        WebElement page2_title = driver.findElementByXPath("//*[@id=\"content\"]/div[1]/div[1]/div/h1");
//        if( page1_title != null  ) {
//            System.out.println( page2_title.getText() );            
//        }
        
        // 웹페이지 소스 출력
        //System.out.println( driver.getPageSource() );
        
        // 탭 종료
        driver.close();
        
        
        
        // 5초 후에 WebDriver 종료
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // WebDriver 종료
            driver.quit();
        }
	}

	
}
