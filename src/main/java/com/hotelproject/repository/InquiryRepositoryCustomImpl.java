package com.hotelproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.hotelproject.constant.State;
import com.hotelproject.dto.InquirySearchDto;
import com.hotelproject.entity.Inquiry;
import com.hotelproject.entity.QInquiry;
import com.hotelproject.entity.QItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class InquiryRepositoryCustomImpl implements InquiryRepositoryCustom {
	private JPAQueryFactory queryFactory;

	public InquiryRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression regDtsAfter(String searcgDateType) {
		LocalDateTime dateTime = LocalDateTime.now(); // 현재날짜 , 시간

		if (StringUtils.equals("all", searcgDateType) || searcgDateType == null)
			return null;
		else if (StringUtils.equals("1d", searcgDateType))
			dateTime = dateTime.minusDays(1); // 현재날짜부터 1일전
		else if (StringUtils.equals("1w", searcgDateType))
			dateTime = dateTime.minusWeeks(1); // 현재날짜부터 1주일전
		else if (StringUtils.equals("1m", searcgDateType))
			dateTime = dateTime.minusMonths(1); // 현재날짜부터 1달전
		else if (StringUtils.equals("6m", searcgDateType))
			dateTime = dateTime.minusMonths(6); // 현재날짜부터 6개월전

		return QInquiry.inquiry.inquiryDate.after(dateTime);
	}

	private BooleanExpression searchSellStatusEq(State state) {
		return state == null ? null : QInquiry.inquiry.state.eq(state);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if (StringUtils.equals("email", searchBy)) {
			return QInquiry.inquiry.Email.like("%" + searchQuery + "%"); // create_by like %검색어%
		}
		return null;
	}

	@Override
	public Page<Inquiry> getAdminInquiryPage(InquirySearchDto inquirySearchDto, Pageable pageable) {
		List<Inquiry> content = queryFactory.selectFrom(QInquiry.inquiry)
				.where(searchSellStatusEq(inquirySearchDto.getState()),
						searchByLike(inquirySearchDto.getSearchBy(), inquirySearchDto.getSearchQuery()))
				.orderBy(QInquiry.inquiry.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();

		long total = queryFactory.select(Wildcard.count).from(QInquiry.inquiry)
				.where(regDtsAfter(inquirySearchDto.getSearchDateType()),
						searchSellStatusEq(inquirySearchDto.getState()),
						searchByLike(inquirySearchDto.getSearchBy(), inquirySearchDto.getSearchQuery()))
				.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}


}
