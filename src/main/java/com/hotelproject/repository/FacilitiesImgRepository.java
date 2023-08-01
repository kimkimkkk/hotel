package com.hotelproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelproject.entity.FacilitiesImg;


public interface FacilitiesImgRepository extends JpaRepository<FacilitiesImg, Long>{

	List<FacilitiesImg> findByFacilitiesIdOrderByIdAsc(Long facilitiesId);

	FacilitiesImg findByFacilitiesIdAndRepimgYn(Long facilitiesId, String repimgYn);
}
