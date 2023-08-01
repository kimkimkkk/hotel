package com.hotelproject.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.entity.Item;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemFormDto {
	private Long id;
	
	@NotBlank(message = "상품명은 필수 입력입니다.")
	private String itemNm;
	
	@NotNull(message = "가격은 필수 입력입니다.")
	private int price;
	
	@NotNull(message = "재고는 필수 입력입니다.")
	private int stockNumber;
	
	@NotBlank(message = "상품 상세설명은 필수 입력입니다.")
	private String itemDetail;
	
	@NotNull(message = "객실 사이즈는 필수 입력입니다.")
	private  int roomSize;
	
	@NotBlank(message = "전망은 필수 입력입니다.")
	private String view;
	
	@NotBlank(message = "침대 사이즈는 필수 입력입니다.")
	private String bedSize;
	
	private ItemStatus itemStatus;
	
	//상품 이미지 정보를 저장
	private List<ItemImgDto> itemImgDtoList = new ArrayList<>();
	
	//상품 이미지 아이디들을 저장 -> 수정시에 이미지 아이디들을 담아둘 용도
	private List<Long> itemImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto -> entity로 바꿈
	public Item createItem() {
		return modelMapper.map(this, Item.class);
	}
	
	//entity -> dto로 바꿈
	public static ItemFormDto of(Item room) {
		return modelMapper.map(room, ItemFormDto.class);
	}
	
	
	
}