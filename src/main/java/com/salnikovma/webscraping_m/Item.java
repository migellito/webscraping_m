package com.salnikovma.webscraping_m;
import java.math.BigDecimal;

class Item {
	private String title ; 
	private BigDecimal price ;
	private BigDecimal square ;
	private String url ;
	
	 BigDecimal getSquare() {
		return square;
	}
	 void setSquare(BigDecimal square) {
		this.square = square;
	}
	 String getTitle() {
		return title;
	}
	 void setTitle(String title) {
		this.title = title;
	}
	 BigDecimal getPrice() {
		return price;
	}
	 void setPrice(BigDecimal price) {
		this.price = price;
	}
	 String getUrl() {
		return url;
	}
	 void setUrl(String url) {
		this.url = url;
	}
}
