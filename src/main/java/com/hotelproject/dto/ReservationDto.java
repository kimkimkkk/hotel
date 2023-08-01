package com.hotelproject.dto;

import com.hotelproject.constant.OrderStatus;
import com.hotelproject.entity.Facilities;
import com.hotelproject.entity.Item;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDto {
	
	private String startDay;
	
	private String endDay;
	
	private Item itemId;
	
	private Facilities facilities;
	
	private int person;
	
	private String imgUrl;
	
	//숙박일 수
	private long night;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	private String tprice;
	

}

