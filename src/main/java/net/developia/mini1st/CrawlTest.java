package net.developia.mini1st;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.util.ObjectUtils;

public class CrawlTest {
	static final String THEHYUNDAI = "https://www.thehyundai.com/front/dpa/sbSect.thd?sectId=192766";
	static final String HMALL = "https://www.hmall.com/pd/dpa/searchSpexSectItem?sectId=2209819";
	static final String WEB_DRIVER_PATH = "C:/ITNE/";
	public static void main(String[] args) throws Exception {
		//String url = THEHYUNDAI;
		
		Document doc = Jsoup.connect(HMALL).get();

		Elements el = doc.getElementsByAttributeValue("class", "visual-wrap");
		
		System.out.println(el);
	}
	
}
