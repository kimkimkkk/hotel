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
import com.hotelproject.dto.FacilitiesFormDto;
import com.hotelproject.exception.OutOfStockExeption;

import jakarta.persistence.*;

@Entity // 엔티티 클래스로 정의
@Table(name="facilities") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Facilities extends BaseEntity{
	
	@Id
	@Column(name="facilities_id") // 테이블 생성될때 컬럼 이름을 지정해준다
	@GeneratedValue(strategy = GenerationType.AUTO) // 키본키를 자동으로 생성해주는 전략 사용(시퀀스)
	private Long id; // 상품코드
	
	@Column(nullable = false, length = 50) // not null, 컬럼 크기지정
	private String facilitiesNm; // 상품명 -> item_nm
	
	@Column(nullable = false)
	private int startTime; // 시작시간
	
	@Column(nullable = false)
	private int endTime; // 끝 시간
	
	@Column(nullable = false)
	private String holiday; // 휴일
	
	@Column(nullable = false)
	private String address; // 위치
	
	@Column(nullable = false)
	private String precautions; // 주의사항
	
	@Column(nullable = false)
	private int age; // 연령제한
	
	@Column(nullable = false)
	private int price; // 가격
	
	@Column(nullable = false)
	private String inquiryCall; // 문의번호
	
	@Lob // clob과 같은 큰타입으로 컬럼을 만든다
	@Column(nullable = false, columnDefinition = "longtext")
	private String facilitiesDetail; // 상품상세설명 -> item_detail
	
	@Enumerated(EnumType.STRING) // enum의 이름을 DB의 저장
	private ItemStatus itemStatus; // 판매상태(SELL, SOLD_OUT) -> item_sell_status
	
	public void updateFacilities(FacilitiesFormDto facilitiesFormDto ) {
		this.facilitiesNm = facilitiesFormDto.getFacilitiesNm();
		this.price = facilitiesFormDto.getPrice();
		this.facilitiesDetail = facilitiesFormDto.getFacilitiesDetail();
		this.itemStatus = facilitiesFormDto.getItemStatus();
		this.startTime = facilitiesFormDto.getStartTime();
		this.endTime = facilitiesFormDto.getEndTime();
		this.holiday = facilitiesFormDto.getHoliday();
		this.address = facilitiesFormDto.getAddress();
		this.precautions =	 facilitiesFormDto.getPrecautions();
		this.age =	 facilitiesFormDto.getAge();
		this.inquiryCall =	 facilitiesFormDto.getInquiryCall();
	}
}