package com.hotelproject.dto;

import org.modelmapper.ModelMapper;

import com.hotelproject.entity.Inquiry;
import com.hotelproject.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InquiryDto {
	
	@NotBlank(message = "내용을 입력해주세요.")
	private String contant;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Inquiry createInquiry() {
		return modelMapper.map(this, Inquiry.class);
	}
	
	public static InquiryDto of(Inquiry inquiry) {
		return modelMapper.map(inquiry, InquiryDto.class);
	}
	
	
	
}