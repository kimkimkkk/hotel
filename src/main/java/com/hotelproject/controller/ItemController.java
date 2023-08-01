package com.hotelproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hotelproject.dto.ItemFormDto;
import com.hotelproject.dto.ItemSearchDto;
import com.hotelproject.dto.MainItemDto;
import com.hotelproject.dto.ReservationDto;
import com.hotelproject.entity.Item;
import com.hotelproject.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; 

@Controller
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	// 객실전체 리스트(헤더부분)
	@GetMapping(value = "/item/room")
	public String itemShopList(Model model, ItemSearchDto itemSearchDto, Optional<Integer> page) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage", 5);
		return "item/room";
	}

	// 상품 상세 페이지
	@GetMapping(value = "/item/{itemId}")
	public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
		ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
		
		model.addAttribute("ReservationDto", new ReservationDto());
		model.addAttribute("item", itemFormDto);

		return "item/itemDtl";
	}
	// 상품등록 페이지
	@GetMapping(value = "/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "item/itemForm";
	}

	// 상품, 상품이미지 등록(insert)
	@PostMapping(value = "/admin/item/new")
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
		if (bindingResult.hasErrors()) {
			return "item/itemForm";
		}
		if (itemImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입니다.");
			return "item/itemForm";
		}

		try {
			itemService.saveItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "item/itemForm";
		}

		return "redirect:/";
	}

	// 상품 관리 페이지
	@GetMapping(value = { "/admin/items", "/admin/items/{page}" })
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		
		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage", 5); // 상품관리 페이지하단에 보여줄 최대 페이지번호

		return "item/itemMng";
	}

	// 상품 수정페이지 보기
	@GetMapping(value = "/admin/item/{itemId}")
	public String itemDtl(@PathVariable("itemId") Long itemid, Model model) {
		try {
			ItemFormDto itemFormDto = itemService.getItemDtl(itemid);
			model.addAttribute("itemFormDto", itemFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 수정정보를 불러오는중 에러가 발생했습니다.");
			model.addAttribute("itemFormDto", new ItemFormDto());
			return "item/itemForm";
		}

		return "item/itemModifyFrom";
	}

	// 상품 수정
	@PostMapping(value = "/admin/item{itemId}")
	public String itemUpdate(@Valid ItemFormDto itemFormDto, Model model, BindingResult bindingResult,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFilesList) {
		if (bindingResult.hasErrors()) {
			return "item/itemForm";

		}
		if (itemImgFilesList.get(0).isEmpty() && itemFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 사움 이미지는 필수입니다.");
			return "item/itemForm";
		}
		try {
			itemService.updateItem(itemFormDto, itemImgFilesList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errormessage", "상품 수정 중 에러가 발생했습니다.");
			return "item/itemForm";

		}
		return "redirect:/";
	}
}