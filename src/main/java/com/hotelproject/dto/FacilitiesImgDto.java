package com.hotelproject.dto;

import org.modelmapper.ModelMapper;

import com.hotelproject.entity.FacilitiesImg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilitiesImgDto {
	private Long id;
	
	private String imgName;
	
	private String oriImgName; //원본 이미지 파일명
	
	private String imgUrl; //이미지 조회 경로
	
	private String repimgYn; //대표 이미지 여부
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static FacilitiesImgDto of(FacilitiesImg facilitiesImg) {
		return modelMapper.map(facilitiesImg, FacilitiesImgDto.class);
	}
	
}	