package com.hotelproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hotelproject.dto.ItemSearchDto;
import com.hotelproject.dto.MainItemDto;
import com.hotelproject.entity.Item;

public interface ItemRepositoryCustom {
	Page <Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

	Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
	
}
