package com.hotelproject.exception;

public class OutOfStockExeption extends RuntimeException{
	
	//상품 주문 수량보다 재고가 적으면 발생되는 exception
	public OutOfStockExeption(String message) {
		super(message);
	}

}
