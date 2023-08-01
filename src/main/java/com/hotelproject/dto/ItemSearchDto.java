package com.hotelproject.dto;


import com.hotelproject.constant.ItemStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {
	
	private String searchDateType;
	private ItemStatus searchSellStatus;
	private String searchBy;
	private String searchQuery = "";
}
