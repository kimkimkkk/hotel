package com.hotelproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hotelproject.dto.FacilitiesFormDto;
import com.hotelproject.dto.FacilitiesImgDto;
import com.hotelproject.dto.FacilitiesSearchDto;
import com.hotelproject.dto.MainFacilitiesDto;
import com.hotelproject.entity.Facilities;
import com.hotelproject.entity.FacilitiesImg;
import com.hotelproject.repository.FacilitiesImgRepository;
import com.hotelproject.repository.FacilitiesRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FacilitiesService {
	private final FacilitiesRepository facilitiesRepository;
	private final FacilitiesImgService facilitiesImgService;
	private final FacilitiesImgRepository facilitiesImgRepository;

	// facilities 테이블에 상품 등록(insert)
	public Long savefacilities(FacilitiesFormDto facilitiesFormDto, List<MultipartFile> facilitiesImgFileList) throws Exception {
		// 1. 상품등록
		Facilities facilities = facilitiesFormDto.createFacilities(); // dto -< entity
		facilitiesRepository.save(facilities); // insert(저장)

		// 2. 이미지등록
		for (int i = 0; i < facilitiesImgFileList.size(); i++) {
			// fk키를 사용시 부모테이블에 해당하는 entity를 먼저 넣어줘야한다.
			FacilitiesImg facilitiesImg = new FacilitiesImg();
			facilitiesImg.setFacilities(facilities);
			// 첫번째 이미지 일때 대표상품 이미지 지정
			if (i == 0) {
				facilitiesImg.setRepimgYn("Y");
			} else {
				facilitiesImg.setRepimgYn("N");
			}
			facilitiesImgService.saveFacilitiesImg(facilitiesImg, facilitiesImgFileList.get(i));
		}
		return facilities.getId(); // 등록한 상품 id를 리턴
	}

	@Transactional(readOnly = true) // 트랜젝션 읽기 전용(변경감지 수행하지 않음) -> 성능향상
	public FacilitiesFormDto getFacilitiesDtl(Long facilitiesId) {
		// 1. facilities_img 테이블의 이미지를 가져온다.
		List<FacilitiesImg> facilitiesImgList = facilitiesImgRepository.findByFacilitiesIdOrderByIdAsc(facilitiesId);
		List<FacilitiesImgDto> facilitiesImgDtoList = new ArrayList<>();

		for (FacilitiesImg facilitiesImg : facilitiesImgList) {
			FacilitiesImgDto facilitiesImgDto = FacilitiesImgDto.of(facilitiesImg);
			facilitiesImgDtoList.add(facilitiesImgDto);
		}
		// 2. facilities 테이블에 있는 데이터를 가져온다.
		Facilities facilities = facilitiesRepository.findById(facilitiesId).orElseThrow(EntityNotFoundException::new);

		// facilities 앤티티 객체 -> dto로 변환
		FacilitiesFormDto facilitiesFormDto = FacilitiesFormDto.of(facilities);

		// 3. facilitiesFormDto에 이미지 정보(facilitiesImgDtoList)를 넣어준다.
		facilitiesFormDto.setFacilitiesImgDtoList(facilitiesImgDtoList);

		return facilitiesFormDto;
	}

	public Long updatefacilities(FacilitiesFormDto facilitiesFormDto, List<MultipartFile> facilitiesImgFileList) throws Exception {
		Facilities facilities = facilitiesRepository.findById(facilitiesFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		// update쿼리문 실행
		// 앤티티 데이터만 변경해준다.
		facilities.updateFacilities(facilitiesFormDto);

		// 2 facilities_img를 바꿔준다. -> 5개의 레코드 전부 변경
		List<Long> facilitiesImgIds = facilitiesFormDto.getFacilitiesImgIds(); // 상품 이미지 아이디 리스트 조회

		for (int i = 0; i < facilitiesImgFileList.size(); i++) {
			facilitiesImgService.updateFacilitiesImg(facilitiesImgIds.get(i), facilitiesImgFileList.get(i));
		}
		return facilities.getId();
	}

	public Page<Facilities> getAdminFacilitiesPage(FacilitiesSearchDto facilitiesSearchDto, Pageable pageable) {
		Page<Facilities> facilitiesPage = facilitiesRepository.getAdminFacilitiesPage(facilitiesSearchDto, pageable);
		return facilitiesPage;
	}

	public Page<MainFacilitiesDto> getMainFacilitiesPage(FacilitiesSearchDto facilitiesSearchDto, Pageable pageable) {
		Page<MainFacilitiesDto> facilitiesPage = facilitiesRepository.getMainFacilitiesPage(facilitiesSearchDto, pageable);
		return facilitiesPage;
	}
	/*
	 * @Transactional(readOnly = true) public List<facilitiesRankDto> getfacilitiesRankList() {
	 * return facilitiesRepository.getfacilitiesRankList(); }
	 */

}