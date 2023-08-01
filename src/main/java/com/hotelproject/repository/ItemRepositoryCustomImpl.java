package com.hotelproject.repository;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.dto.ItemSearchDto;
import com.hotelproject.dto.MainItemDto;
import com.hotelproject.dto.QMainItemDto;
import com.hotelproject.entity.Item;
import com.hotelproject.entity.QItem;
import com.hotelproject.entity.QItemImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
	

	private JPAQueryFactory queryFactory;
	
	
	public ItemRepositoryCustomImpl(EntityManager em) {
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
			
		return QItem.item.regTime.after(dateTime);
	}
	
	private BooleanExpression searchSellStatusEq(ItemStatus searchSellStatus) {
		return searchSellStatus == null ? null : QItem.item.itemStatus.eq(searchSellStatus);
	}
	
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if (StringUtils.equals("itemNm", searchBy)) {
			//등록자 검색시 
			return QItem.item.itemNm.like("%" + searchQuery + "%"); //item_nm like %검색어%
		}else if(StringUtils.equals("createdBy",searchBy)) {
			return QItem.item.createdBy.like("%" + searchQuery + "%"); //create_by like %검색어%
		}
		return null;
	}
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		
		
		
		
		
		
		/* 
		 * select * from item where reg_time = ?
		 * and item_sell_status = ? and item_nm(create_by) like %검색어%
		 * order by item_id desc;
		 */
		List<Item> content = queryFactory.selectFrom(QItem.item)
										 .where(searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
										 searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))										 
										 .orderBy(QItem.item.id.desc())
										 .offset(pageable.getOffset())
										 .limit(pageable.getPageSize())
										 .fetch();
		
		
		//select
		long total = queryFactory.select(Wildcard.count).from(QItem.item)
				.where(regDtsAfter(itemSearchDto.getSearchDateType()),
						 searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
						 searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())).fetchOne();
		
		
		
		
		
		
		return new PageImpl<>(content, pageable, total);
	}
	
	private BooleanExpression itemNmlike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? 
				null: QItem.item.itemNm.like("%" +searchQuery + "%");
	}
	

	@Override
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		//   select * from item, item_img, item_img.imgUrl, item.price item.Detail where item.item_id= item_img
		//   and item_img.repimg_tn = 'y'
		//   and item.item_nm like '%검색어%'
		//   oreder by item.item_id desc
		
		QItem item = QItem.item;
		QItemImg itemImg = QItemImg.itemImg;
		
		
		//dto를 객체로 바로 받아올때는 컴럼과 dto객체의 필드가 일치해야한다
		//2.@QuertProjection을 반드시 사용해야한다.
		//3.
		List<MainItemDto> content = queryFactory
								.select( 
										new QMainItemDto( 
												item.id,
												item.itemNm,
												item.itemDetail,
												itemImg.imgUrl,
												item.price,
												item.bedSize,
												item.view,
												item.roomSize
												)
										)
										.from(itemImg)
										.join(itemImg.item, item)
										.where(itemImg.repimgYn.eq("Y"))
										.where(itemNmlike(itemSearchDto.getSearchQuery()))
										.orderBy(item.id.desc())
										.offset(pageable.getOffset())
										.limit(pageable.getPageSize())
										.fetch();
		
		long total = queryFactory.select(Wildcard.count)
				.from(itemImg)
				.join(itemImg.item, item)
				.where(itemImg.repimgYn.eq("Y"))
				.where(itemNmlike(itemSearchDto.getSearchQuery()))
				.orderBy(item.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchOne();
		
		
		return new PageImpl<>(content, pageable, total);
	}

}