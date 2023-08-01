package com.hotelproject.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelproject.dto.InquirySearchDto;
import com.hotelproject.entity.Inquiry;

@Primary
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom{
}
