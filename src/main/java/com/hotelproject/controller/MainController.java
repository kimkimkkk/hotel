package com.hotelproject.controller;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hotelproject.dto.FacilitiesSearchDto;
import com.hotelproject.dto.ItemSearchDto;
import com.hotelproject.dto.MainFacilitiesDto;
import com.hotelproject.dto.MainItemDto;
import com.hotelproject.service.FacilitiesService;
import com.hotelproject.service.ItemService;

import lombok.RequiredArgsConstructor;


@Controller
@ComponentScan(basePackages = { "찾지 못하는 패키지루트" })
@RequiredArgsConstructor
public class MainController {
	private final ItemService itemService;
	private final FacilitiesService facilitiesService;
	

	@GetMapping(value = "/")
	public String index(ItemSearchDto itemSearchDto, FacilitiesSearchDto facilitiesSearchDto, Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
		Page<MainFacilitiesDto> facilitiess = facilitiesService.getMainFacilitiesPage(facilitiesSearchDto, pageable);
		
		
//		List<ItemRankDto> itemsRank = itemService.getItemRankList();
		model.addAttribute("facilitiess", facilitiess);
		model.addAttribute("facilitiesSearchDto", facilitiesSearchDto);
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		return "/index";
	}
	

}
