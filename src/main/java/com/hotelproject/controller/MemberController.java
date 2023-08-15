package com.hotelproject.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelproject.dto.MemberFormDto;
import com.hotelproject.entity.Inquiry;
import com.hotelproject.entity.Member;
import com.hotelproject.service.EmailService;
import com.hotelproject.service.EmailServiceImpl;
import com.hotelproject.service.InquiryService;
import com.hotelproject.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;
	private final InquiryService inquiryService;
	// 로그인
	@GetMapping(value = "/members/login")
	public String login() {
		return "member/memberLoginForm";
	}

	// 회원가입 화면
	@GetMapping(value = "/members/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}

	// 회원가입
	@PostMapping(value = "/members/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		return "redirect:/";
	}
	

	// 로그인 실패했을 때
	@GetMapping(value = "/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "member/memberLoginForm";
	}

	//비밀번호 찾기 창
	@GetMapping(value = "/members/Pw")
	public String findpass(Model model) {
		return "member/findpass";
	}
	//비밀번호 찾기
	@PostMapping(value = "/emailConfirm")
	public String emailConfirm(@RequestParam("email") String email, Model model) throws Exception {
		
		Member member = new Member();
		try {
			// 1. 입력한 이메일이 회원인지 확인
			member = memberService.findMember(email);
			
			// 2. 입력한 메일로 임시 비밀번호 전송
			String tempPassword = EmailServiceImpl.createTempPassword();
			emailService.sendSimpleMessage(email, tempPassword);
			
			// 3. 기존 비밀번호를 임시 비밀번호로 변경
			member.setPassword(passwordEncoder.encode(tempPassword));
			memberService.updateMember(member);
			model.addAttribute("tempPasswordMessage", "임시 비밀번호로 로그인해주세요.");
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "가입된 이메일이 아닙니다!");
			return "redirect:/";
		}
		return "redirect:/";
	}
	//마이페이지
	@GetMapping(value = "/members/myPage")
	public String passwordChange(Model model, Principal principal) {
		Member member = memberService.findMember(principal.getName());
		
		List<Inquiry> inquiries = inquiryService.getInquiry(principal.getName());
		model.addAttribute("member",member);
		model.addAttribute("inquiries",inquiries);
		
		return "member/mypage";
	}
	//마이페이지에서 수정한 값 바꾸기
	@PostMapping(value = "/members/myChange")
	public String Pwchange(@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("password") String password ,@RequestParam("address") String address, Model model) throws Exception {
		System.out.println(password + "asdasdsdsdsds");
		Member member = new Member();
		try {
			member = memberService.findMember(email);
			if (!password.isBlank()){
				member.setPassword(passwordEncoder.encode(password));
			}else {
			}
			member.setAddress(address);
			member.setName(name);
			memberService.updateMember(member);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "가입된 이메일이 아닙니다!");
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	
}
