package com.hotelproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotelproject.dto.InquiryDto;
import com.hotelproject.dto.InquirySearchDto;
import com.hotelproject.dto.ItemSearchDto;
import com.hotelproject.entity.Inquiry;
import com.hotelproject.entity.Item;
import com.hotelproject.repository.InquiryRepository;
import com.hotelproject.repository.InquiryRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InquiryService {
	private final InquiryRepository inquiryRepository;
	private final InquiryRepositoryCustom repositoryCustom;

	public Long order(InquiryDto inquiryDto, String email) {
		Inquiry inquiry = Inquiry.createInquiry(inquiryDto, email);// dto -< entity
		inquiryRepository.save(inquiry); // insert(저장)

		return inquiry.getId();
	}

	public Page<Inquiry> getAdminInquiryPage(InquirySearchDto inquirySearchDto, Pageable pageable) {
		Page<Inquiry> inquiryPage = repositoryCustom.getAdminInquiryPage(inquirySearchDto, pageable);
		return inquiryPage;
	}

	public List<Inquiry> getInquiry(String email) {
		List<Inquiry> inquiries = inquiryRepository.findByEmail(email);
		return inquiries;
	}
}
