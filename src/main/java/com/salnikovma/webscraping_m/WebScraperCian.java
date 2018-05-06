package com.salnikovma.webscraping_m;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class WebScraperCian {
		public static void main(String[] args) {
			HtmlPage page1;
		    String searchQuery = "kupit-2-komnatnuyu-kvartiru-moskovskaya-oblast-krasnogorsk-opaliha-mkr-ulica-dezhneva-02511229" ;
			String baseUrl = "https://krasnogorsk.cian.ru/" ;
			WebClient client = new WebClient(BrowserVersion.CHROME);
			client.getOptions().setCssEnabled(false);
			client.getOptions().setJavaScriptEnabled(false);
			try {
				//String searchUrl = baseUrl + URLEncoder.encode(searchQuery, "UTF-8");
				String searchUrl = "https://krasnogorsk.cian.ru/kupit-2-komnatnuyu-kvartiru-moskovskaya-oblast-krasnogorsk-opaliha-mkr-ulica-dezhneva-02511229/" ;
				//String searchUrl = "https://fritzmorgen.livejournal.com/1226268.html" ;
				
				HtmlPage page0 = client.getPage(searchUrl);
				HtmlElement results = (HtmlElement) page0.getFirstByXPath("//div[@class='wrapper--1Z8Nz']") ;
				
				try(  PrintWriter out = new PrintWriter( "filename.txt" )  ){
				    out.println( results.asXml() );
				}
				List<HtmlElement> items = (List<HtmlElement>) results.getByXPath("//div[@class='offer-container--2MrIy']");
				List<HtmlAnchor> links = (List<HtmlAnchor>) results.getByXPath(".//a[@class='header--28QlS']");				

				if(links.isEmpty()){
					System.out.println("No links found !");
				}else{
					for(HtmlAnchor htmlLink : links){
						String resultUrl = htmlLink.getHrefAttribute();
						page1 = client.getPage(resultUrl);
						
						HtmlElement offerTitle = ((HtmlElement) page1.getFirstByXPath("//h1[@class='title--2oO4e']"));
						HtmlElement offerPrice = ((HtmlElement) page1.getFirstByXPath("//span[@class='price_value--XlUfS']"));
						HtmlElement offerSquare= ((HtmlElement) page1.getFirstByXPath("//div[@class='info-block--1hVvz']/div[1]/div[@class='info-text--3GGPV']")) ;

						Item item = constructItem(offerTitle,offerPrice,offerSquare,resultUrl);
						
						
						ObjectMapper mapper = new ObjectMapper();
						String jsonString = mapper.writeValueAsString(item) ;
						System.out.println(jsonString);
					}
				}
			} catch(Exception e){
				e.printStackTrace();
			}

		}

		private static Item constructItem(HtmlElement elTitle,HtmlElement elPrice,HtmlElement elSquare, String url) {
			Item item = new Item();
			item.setTitle(elTitle.asText());
			
			String price = elPrice.asText();
			price = price.replace(" ", "");
			price = price.replace("₽", "");
			item.setPrice(new BigDecimal(price));
			
			String square = elSquare.asText();
			square = square.replace(" м²", "");
			square = square.replace(",", ".");
			item.setSquare(new BigDecimal(square));	
			
			item.setUrl(url);
			return item;
		}

		public static void saveXml(HtmlPage savepage){
			File file = new File("c:/temp/out");
			try {
				savepage.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}