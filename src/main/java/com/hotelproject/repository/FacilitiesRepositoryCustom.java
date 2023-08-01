package com.hotelproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hotelproject.dto.FacilitiesSearchDto;
import com.hotelproject.dto.MainFacilitiesDto;
import com.hotelproject.entity.Facilities;

public interface FacilitiesRepositoryCustom {
	Page <Facilities> getAdminFacilitiesPage(FacilitiesSearchDto faacilitiesSearchDto, Pageable pageable);
	
	Page <MainFacilitiesDto> getMainFacilitiesPage(FacilitiesSearchDto faacilitiesSearchDto, Pageable pageable);
	
	
}
