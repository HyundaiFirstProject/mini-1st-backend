package net.developia.mini1st.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.developia.mini1st.domain.ProductsDTO;
import net.developia.mini1st.mapper.ProductsMapper;

@Service
public class ProductsServiceImpl implements ProductsService{
	
	@Autowired
	private ProductsMapper mapper;

	// 크롤링한 결과를 DB에 저장
	@Override
	public void createProductList() {
		System.out.println("createProductsList(Service)...");
		// pet_products 테이블 비우기
		mapper.initProducts();
		// WebDriverManager를 사용하여 WebDriver 초기화
		WebDriverManager.chromedriver().setup();
		//System.setProperty("webdriver.chrome.driver", "C:/dev/chromedriver-win64/chromedriver.exe");
		// ChromeOptions를 사용하여 Chrome을 백그라운드 모드로 실행
	    ChromeOptions options = new ChromeOptions();
	    //options.addArguments("--headless"); // 백그라운드 모드로 실행
		// WebDriver 설정 
        WebDriver driver = new ChromeDriver(options);
        try {
            // 쇼핑몰 페이지 열기
            driver.get("https://www.thehyundai.com/front/dpa/sbSect.thd?sectId=192766");

            // WebDriver를 사용하여 페이지가 완전히 로드될 때까지 기다리기
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

            // 예제: 웹 페이지에서 특정 요소 찾기
            // 찾은 요소의 텍스트 출력
            List<WebElement> elements = driver.findElements(By.cssSelector("div.prod-unit"));

            for (int i = 0; i < elements.size(); i++) {
            	ProductsDTO dto = new ProductsDTO();
            	// i 번재 element 가져오기
                WebElement element = elements.get(i);

                String imageUrl = element.findElement(By.cssSelector("div.img img")).getAttribute("src");
                System.out.println("Image URL: " + imageUrl);
                dto.setImg(imageUrl);

                // 제품명 추출
                String productName = element.findElement(By.cssSelector("div.info a.title")).getText();
                System.out.println("Product Name: " + productName);
                dto.setProduct_name(productName);

                // 링크 추출
                try {
                    // <a> 태그를 찾아 클릭
                	WebElement linkElement = element.findElement(By.cssSelector("a[onclick^='javascript:itemDetailView']"));
                	linkElement.click();

                    // 클릭 후 현재 페이지 URL 가져오기
                    String currentURL = driver.getCurrentUrl();
                    System.out.println("현재 페이지 URL: " + currentURL);
                    dto.setUrl(currentURL);
                } catch (Exception e) {
                    // 에러가 발생한 경우 에러 메시지 출력
                    System.err.println("제품 링크를 가져오는 중 오류 발생: " + e.getMessage());
                    // 혹은 e.printStackTrace(); 를 사용하여 전체 스택 트레이스 출력 가능
                }
                System.out.println("=================================================");
                System.out.println("제품명 : " + productName);
                System.out.println("이미지 URL : " + imageUrl);
//                System.out.println("링크 : " + currentURL);
                System.out.println("=================================================");
                // 크롤링한 DTO를 DB에 저장
                mapper.insertProduct(dto);
                // 뒤로 가기
                driver.navigate().back();

                // 페이지가 완전히 로드될 때까지 대기
                wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

                // 뒤로 이동 후 요소를 다시 찾음
                elements = driver.findElements(By.cssSelector("div.prod-unit"));

                System.out.println("--------------------------");
            }
        } finally {
            // WebDriver 종료
            driver.quit();
        }
	}

	// DB 에서 제품 목록 불러오기
	@Override
	public List<ProductsDTO> getProductList() {
		return mapper.getProductsList();
	}

	@Override
	public List<ProductsDTO> searchProducts(String product_name) {
		return mapper.searchProducts(product_name);
	}

}