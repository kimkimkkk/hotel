package com.hotelproject.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.hotelproject.dto.ReservationDto;
import com.hotelproject.entity.Facilities;
import com.hotelproject.entity.Item;
import com.hotelproject.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@GetMapping(value = "/order")
	public String orderForm(Model model) {
		model.addAttribute("ReservationDto", new ReservationDto());
		return "order/orderForm"; // orderForm.html 페이지를 반환
	}

	@PostMapping(value = "/order")
	public String order(@Valid ReservationDto reservationDto, BindingResult bindingResult, Principal principal, @RequestParam("itemId") Long ItemId, @RequestParam("facilitiesId") Long FacilitiesId) {
		
		if (bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				//에러 메세지 합치기
				sb.append(fieldError.getDefaultMessage());
			}
			return "redirect:/";
		}
		String email = principal.getName(); // id에 해당하는 정보를 가지고 온다.
		Long itemId = ItemId;
		Long facilitiesId = FacilitiesId;
		// Principal: 로그인한 사용자의 정보를 가져올수있다
        
		try {
			if(itemId > 0) {
				Item item = reservationService.setItem(itemId);
				System.out.println("xxxxxxxxxxxxxxxxxxxx");
				reservationDto.setItemId(item);
				System.out.println("ssssssssssssssss");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate startDate = LocalDate.parse(reservationDto.getStartDay(), formatter);
		        LocalDate endDate = LocalDate.parse(reservationDto.getEndDay(), formatter);
		        long daysBetween = endDate.toEpochDay() - startDate.toEpochDay(); //두 날짜 차이 구하기.
		        reservationDto.setNight(daysBetween);
			}
			if(facilitiesId > 0) {
				Facilities facilities = reservationService.setFacilities(FacilitiesId);
				reservationDto.setFacilities(facilities);
			}
			reservationService.order(reservationDto, email); // 주문하기 실행
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "redirect:/";
		}

		return "redirect:/";
	}
}
