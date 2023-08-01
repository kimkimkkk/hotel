	package com.hotelproject.dto;

	import com.querydsl.core.annotations.QueryProjection;

	import lombok.Getter;
	import lombok.Setter;

	@Getter
	@Setter
	public class MainFacilitiesDto {
		private Long id;
		private String facilitiesNm;
		private String facilitiesDetail;
		private String imgUrl;
		private Integer price;
		
		@QueryProjection //쿼리dsl로 결과 조회 할때 MainItemDto 객체로 바로 받아올 수 있다.
		public MainFacilitiesDto(Long id, String facilitiesNm, String facilitiesDetail, String imgUrl, Integer price) {
			this.id = id;
			this.facilitiesNm = facilitiesNm;
			this.facilitiesDetail = facilitiesDetail;
			this.imgUrl = imgUrl;
			this.price = price;
		}
	}
