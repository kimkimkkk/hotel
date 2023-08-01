package com.hotelproject.controller;

import java.security.Principal;
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

import com.hotelproject.dto.InquiryDto;
import com.hotelproject.dto.InquirySearchDto;
import com.hotelproject.entity.Inquiry;
import com.hotelproject.service.InquiryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InpuryController {
	private final InquiryService inquiryService;
	
	@GetMapping(value="/inquirys/inquiry")
	public String inin(InquiryDto inquiryDto ,Model model,Principal principal) {
		String email = principal.getName();
		model.addAttribute("inquiryDto", new InquiryDto());
		model.addAttribute("email", email);
		
		
		return "/inquiry/inquiry";
	}
	
	@PostMapping(value = "/inquirys/inquiry")
	public String inins(@Valid InquiryDto inquiryDto, BindingResult bindingResult, Model model,Principal principal) {
		String email = principal.getName();
		inquiryService.order(inquiryDto, email);
		
		return "redirect:/";
	}
	
	@GetMapping(value = {"/admin/inquirys","/admin/inquirys/{page}"})
	public String adminInquiry(InquirySearchDto inquirySearchDto,@PathVariable("page") Optional<Integer> page,Model model) {
		
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
		
		Page<Inquiry> inquirys = inquiryService.getAdminInquiryPage(inquirySearchDto, pageable);
		model.addAttribute("inquirys", inquirys);
		model.addAttribute("inquirySearchDto", inquirySearchDto);
		model.addAttribute("maxPage", 5); // 상품관리 페이지하단에 보여줄 최대 페이지번호
		
	
		return "inquiry/Inquirys";
	}
	
	@GetMapping(value = "/admin/inquiry/{inquiryid}")
	public String UqdateInquiry(@PathVariable("inquiryid") Long inquiryid,Model model) {
		
		

		
		return "/admin/inquirys";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
