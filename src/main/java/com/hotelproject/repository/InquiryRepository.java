package com.hotelproject.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.dto.InquirySearchDto;
import com.hotelproject.entity.Inquiry;
import com.hotelproject.entity.Item;

@Primary
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom{
	
	 List<Inquiry> findByEmail(String email);
}
