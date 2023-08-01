package com.hotelproject.dto;

import com.hotelproject.constant.State;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquirySearchDto {
	private String searchQuery = "";
	private String searchDateType;
	private State state;
	private String searchBy;
}
