package com.hotelproject.dto;



import com.hotelproject.constant.ItemStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilitiesSearchDto {
	
	private String searchDateType;
	private ItemStatus searchSellStatus;
	private String searchBy;
	private String searchQuery = "";
}
