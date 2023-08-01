package com.hotelproject.repository;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.dto.FacilitiesSearchDto;
import com.hotelproject.dto.MainFacilitiesDto;
import com.hotelproject.dto.QMainFacilitiesDto;
import com.hotelproject.entity.Facilities;
import com.hotelproject.entity.QFacilities;
import com.hotelproject.entity.QFacilitiesImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class FacilitiesRepositoryCustomImpl implements FacilitiesRepositoryCustom{
	private JPAQueryFactory queryFactory;
	
	
	public FacilitiesRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression regDtsAfter(String searcgDateType) {
		LocalDateTime dateTime = LocalDateTime.now(); //현재날짜 , 시간
		
		if (StringUtils.equals("all",searcgDateType) || searcgDateType == null) return null;
		else if(StringUtils.equals("1d",searcgDateType))
			dateTime = dateTime.minusDays(1); // 현재날짜부터 1일전
		else if(StringUtils.equals("1w",searcgDateType))
			dateTime = dateTime.minusWeeks(1); // 현재날짜부터 1주일전
		else if(StringUtils.equals("1m",searcgDateType))
			dateTime = dateTime.minusMonths(1); // 현재날짜부터 1달전
		else if(StringUtils.equals("6m",searcgDateType))
			dateTime = dateTime.minusMonths(6); // 현재날짜부터 6개월전
			
		return QFacilities.facilities.regTime.after(dateTime);
	}
	
	private BooleanExpression searchSellStatusEq(ItemStatus searchSellStatus) {
		return searchSellStatus == null ? null : QFacilities.facilities.itemStatus.eq(searchSellStatus);
	}
	
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if (StringUtils.equals("facilitiesNm", searchBy)) {
			//등록자 검색시 
			return QFacilities.facilities.facilitiesNm.like("%" + searchQuery + "%"); //item_nm like %검색어%
		}else if(StringUtils.equals("createdBy",searchBy)) {
			return QFacilities.facilities.createdBy.like("%" + searchQuery + "%"); //create_by like %검색어%
		}
		return null;
	}
	
	@Override
	public Page<Facilities> getAdminFacilitiesPage(FacilitiesSearchDto facilitiesSearchDto, Pageable pageable) {
		
		
		
		
		
		
		/* 
		 * select * from item where reg_time = ?
		 * and item_sell_status = ? and item_nm(create_by) like %검색어%
		 * order by item_id desc;
		 */
		List<Facilities> content = queryFactory.selectFrom(QFacilities.facilities)
										 .where(regDtsAfter(facilitiesSearchDto.getSearchDateType()),
										 searchSellStatusEq(facilitiesSearchDto.getSearchSellStatus()),
										 searchByLike(facilitiesSearchDto.getSearchBy(), facilitiesSearchDto.getSearchQuery()))										 
									 .orderBy(QFacilities.facilities.id.desc())
										 .offset(pageable.getOffset())
										 .limit(pageable.getPageSize())
										 .fetch();
		
		
		//select
		long total = queryFactory.select(Wildcard.count).from(QFacilities.facilities)
				.where(regDtsAfter(facilitiesSearchDto.getSearchDateType()),
						 searchSellStatusEq(facilitiesSearchDto.getSearchSellStatus()),
						 searchByLike(facilitiesSearchDto.getSearchBy(), facilitiesSearchDto.getSearchQuery())).fetchOne();
		
		
		
		
		
		
		return new PageImpl<>(content, pageable, total);
	}
	
	private BooleanExpression facilitiesNmlike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? 
				null: QFacilities.facilities.facilitiesNm.like("%" +searchQuery + "%");
	}
	

	@Override
	public Page<MainFacilitiesDto> getMainFacilitiesPage(FacilitiesSearchDto facilitiesSearchDto, Pageable pageable) {
		//   select * from item, item_img, item_img.imgUrl, item.price item.Detail where item.item_id= item_img
		//   and item_img.repimg_tn = 'y'
		//   and item.item_nm like '%검색어%'
		//   oreder by item.item_id desc
		
		QFacilities facilities = QFacilities.facilities;
		QFacilitiesImg facilitiesImg = QFacilitiesImg.facilitiesImg;
		
		
		//dto를 객체로 바로 받아올때는 컴럼과 dto객체의 필드가 일치해야한다
		//2.@QuertProjection을 반드시 사용해야한다.
		//3.
		List<MainFacilitiesDto> content=  queryFactory
				.select( 
						new QMainFacilitiesDto( 
								facilities.id,
								facilities.facilitiesNm,
								facilities.facilitiesDetail,
								facilitiesImg.imgUrl,
								facilities.price
								)
						)
						.from(facilitiesImg)
						.join(facilitiesImg.facilities, facilities)
						.where(facilitiesImg.repimgYn.eq("Y"))
						.where(facilitiesNmlike(facilitiesSearchDto.getSearchQuery()))
						.orderBy(facilities.id.desc())
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();	
		long total = queryFactory.select(Wildcard.count)
				.from(facilitiesImg)
				.join(facilitiesImg.facilities, facilities)
				.where(facilitiesImg.repimgYn.eq("Y"))
				.where(facilitiesNmlike(facilitiesSearchDto.getSearchQuery()))
				.orderBy(facilities.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchOne();
		
		
		return new PageImpl<>(content, pageable, total);
	}
}