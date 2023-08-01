package com.hotelproject.entity;

import java.time.LocalDateTime;


import com.hotelproject.constant.State;
import com.hotelproject.dto.InquiryDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Inquiry")
@Getter
@Setter
@ToString
public class Inquiry {
		
	@Id
	@Column(name = "inquiry_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime inquiryDate; //문의 날짜
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String content;
	
	private String Email;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	public static Inquiry createInquiry(InquiryDto inquiryDto, String email){
		Inquiry inquiry = new Inquiry();
		inquiry.setInquiryDate(LocalDateTime.now());
		inquiry.setContent(inquiryDto.getContant());
		inquiry.setEmail(email);
		inquiry.setState(State.PROCESSING);
		
		return inquiry;
	}
	
}