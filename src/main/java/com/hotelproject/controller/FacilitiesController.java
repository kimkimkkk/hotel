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

import com.hotelproject.dto.FacilitiesFormDto;
import com.hotelproject.dto.FacilitiesSearchDto;
import com.hotelproject.dto.MainFacilitiesDto;
import com.hotelproject.dto.ReservationDto;
import com.hotelproject.entity.Facilities;
import com.hotelproject.service.FacilitiesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; 

@Controller
@RequiredArgsConstructor
public class FacilitiesController {
	private final FacilitiesService facilitiesService;

	// 객실전체 리스트(헤더부분)
	@GetMapping(value = "/facilities/facilities")
	public String facilitiesShopList(Model model, FacilitiesSearchDto facilitiesSearchDto, Optional<Integer> page) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
		Page<MainFacilitiesDto> facilitiess = facilitiesService.getMainFacilitiesPage(facilitiesSearchDto, pageable);
		model.addAttribute("facilitiess", facilitiess);
		model.addAttribute("facilitiesSearchDto",facilitiesSearchDto);
		model.addAttribute("maxPage", 5);
		return "facilities/facilities";
	}

	// 상품 상세 페이지
	@GetMapping(value = "/facilities/{facilitiesId}")
	public String FacilitiesDtl(Model model, @PathVariable("facilitiesId") Long facilitiesId) {
		FacilitiesFormDto facilitiesFormDto = facilitiesService.getFacilitiesDtl(facilitiesId);
		
		model.addAttribute("ReservationDto", new ReservationDto());
		model.addAttribute("facilities", facilitiesFormDto);
		return "facilities/facilitiesDtl";
	}

	// 상품등록 페이지
	@GetMapping(value = "/admin/facilities/new")
	public String facilitiesForm(Model model) {
		model.addAttribute("facilitiesFormDto", new FacilitiesFormDto());
		return "facilities/facilitiesForm";
	}

	// 상품, 상품이미지 등록(insert)
	@PostMapping(value = "/admin/facilities/new")
	public String facilitiesNew(@Valid FacilitiesFormDto facilitiesFormDto, BindingResult bindingResult, Model model,
			@RequestParam("facilitiesImgFile") List<MultipartFile> facilitiesImgFileList) {
		if (bindingResult.hasErrors()) {
			return "facilities/facilitiesForm";
		}
		if (facilitiesImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입니다.");
			return "facilities/facilitiesForm";
		}

		try {
			facilitiesService.savefacilities(facilitiesFormDto, facilitiesImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "facilities/facilitiesForm";
		}

		return "redirect:/";
	}

	// 상품 관리 페이지
	@GetMapping(value = { "/admin/facilitiess", "/admin/facilitiess/{page}" })
	public String facilitiesManage(FacilitiesSearchDto facilitiesSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		Page<Facilities> facilitiess = facilitiesService.getAdminFacilitiesPage(facilitiesSearchDto, pageable);
		model.addAttribute("Facilitiess", facilitiess);
		model.addAttribute("FacilitiesSearchDto", facilitiesSearchDto);
		model.addAttribute("maxPage", 5); // 상품관리 페이지하단에 보여줄 최대 페이지번호

		return "facilities/facilitiesMng";
	}

	// 상품 수정페이지 보기
	@GetMapping(value = "/admin/Facilities/{FacilitiesId}")
	public String facilitiesDtl(@PathVariable("FacilitiesId") Long facilitiesid, Model model) {
		try {
			FacilitiesFormDto facilitiesFormDto = facilitiesService.getFacilitiesDtl(facilitiesid);
			model.addAttribute("facilitiesFormDto", facilitiesFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 수정정보를 불러오는중 에러가 발생했습니다.");
			model.addAttribute("facilitiesFormDto", new FacilitiesFormDto());
			return "facilities/facilitiesForm";
		}

		return "facilities/facilitiesModifyFrom";
	}

	// 상품 수정
	@PostMapping(value = "/admin/facilities{facilitiesId}")
	public String facilitiesUpdate(@Valid FacilitiesFormDto facilitiesFormDto, Model model, BindingResult bindingResult,
			@RequestParam("facilitiesImgFile") List<MultipartFile> facilitiesImgFilesList) {
		if (bindingResult.hasErrors()) {
			return "facilities/facilitiesForm";

		}
		if (facilitiesImgFilesList.get(0).isEmpty() && facilitiesFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 사움 이미지는 필수입니다.");
			return "facilities/facilitiesForm";
		}
		try {
			facilitiesService.updatefacilities(facilitiesFormDto, facilitiesImgFilesList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errormessage", "상품 수정 중 에러가 발생했습니다.");
			return "facilities/facilitiesForm";

		}
		return "redirect:/";
	}
}

