package net.developia.mini1st;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlTest {
	static final String URL = "https://www.thehyundai.com/front/dpa/sbSect.thd?sectId=192766";
	public static void main(String[] args) throws Exception {
		
		Document doc = Jsoup.connect(URL).get();

//		Elements el = doc.getElementsByAttributeValue("class", "prod-unit");
//		System.out.println(el);
		
//		for(Element element : el) {
//			System.out.println(element.text()); // 제품 이름 추출
//		}
//		Elements imgElements = doc.select("div.img img");
//
//        for (Element imgElement : imgElements) {
//            // 이미지 URL을 출력
//            String imageUrl = imgElement.attr("src");
//            System.out.println("Image URL: " + imageUrl);
//        }
		
		// div.prod-unit 요소 선택
        Elements prodUnits = doc.select("div.prod-unit");

        for (Element prodUnit : prodUnits) {
            // (1) 이미지
            String imageUrl = prodUnit.select("div.img img").attr("src");

            // (2) 제품명
            String productName = prodUnit.select("div.info a.title").text();

            // (3) 제품 링크
            String productLink = prodUnit.select("div.img a").attr("href");
            // 출력
            System.out.println("Image URL: " + imageUrl);
            System.out.println("Product Name: " + productName);
            System.out.println("Product Link: " + productLink);
            System.out.println("--------");
        }
	}
	
}
