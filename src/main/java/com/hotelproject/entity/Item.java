package com.hotelproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.dto.ItemFormDto;
import com.hotelproject.exception.OutOfStockExeption;

import jakarta.persistence.*;

@Entity // 엔티티 클래스로 정의
@Table(name="item") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Item extends BaseEntity{
	
	@Id
	@Column(name="item_id") // 테이블 생성될때 컬럼 이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) // 키본키를 자동으로 생성해주는 전략 사용(시퀀스)
	private Long id; // 상품코드
	
	@Column(nullable = false, length = 50) // not null, 컬럼 크기지정
	private String itemNm; // 상품명 -> item_nm
	
	@Column(nullable = false)
	private int price; // 가격
	
	@Column(nullable = false)
	private int stockNumber; // 재고수량 -> stock_number
	
	@Column(nullable = false)
	private int roomSize;
	
	@Column(nullable = false)
	private String view;
	
	@Column(nullable = false)
	private String bedSize; // 재고수량 -> stock_number
	
	
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String itemDetail; // 상품상세설명 -> item_detail
	
	
	@Enumerated(EnumType.STRING) // enum의 이름을 DB의 저장
	private ItemStatus itemStatus; // 판매상태(SELL, SOLD_OUT) -> item_sell_status
	
	public void updateItem(ItemFormDto itemFormDto ) {
		this.itemNm = itemFormDto.getItemNm();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.itemDetail = itemFormDto.getItemDetail();
		this.itemStatus = itemFormDto.getItemStatus();
	}

}