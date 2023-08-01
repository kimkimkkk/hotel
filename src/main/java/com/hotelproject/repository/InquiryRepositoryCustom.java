package com.hotelproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hotelproject.dto.InquirySearchDto;
import com.hotelproject.entity.Inquiry;


public interface InquiryRepositoryCustom {
	Page <Inquiry> getAdminInquiryPage(InquirySearchDto inquirySearchDto, Pageable pageable);
}
