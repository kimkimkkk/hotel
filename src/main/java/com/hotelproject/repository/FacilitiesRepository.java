package com.hotelproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.entity.Facilities;


public interface FacilitiesRepository extends JpaRepository<Facilities, Long>, FacilitiesRepositoryCustom{ // <해당 repository에서 사용할 Entity, Entity 클래스의 기본키 타입>
	
	// select * from item where item_nm = ?
	List<Facilities> findByFacilitiesNm(String facilitiesNm);
	
	// 퀴즈 1-1
	List<Facilities> findByFacilitiesNmAndItemStatus(String facilitiesNm, ItemStatus itemStatus);
	// 퀴즈 1-2
	List<Facilities> findByPriceBetween(int s, int e);
	// 퀴즈 1-3
	List<Facilities> findByRegTimeAfter(LocalDateTime regTime);
	// 퀴즈 1-4
	List<Facilities> findByItemStatusNotNull();
	// 퀴즈 1-5
	List<Facilities> findByFacilitiesDetailEndingWith(String facilitiesDetail);
	// 퀴즈 1-6
	List<Facilities> findByFacilitiesNmOrFacilitiesDetail(String facilitiesNm, String facilitiesDetail);
	// 퀴즈 1-7
	List<Facilities> findByPriceLessThanOrderByPrice(int e);

	// JPQL(non native 쿼리) -> 컬럼명, 테이블명은 entity 클래스 기준으로 작성
	@Query("select e from Facilities e where e.facilitiesDetail like %:facilitiesDetail% order by e.price desc")
	List<Facilities> findByFacilitiesDetail(@Param("facilitiesDetail") String facilitiesDetail);
	
	// JPQL(native 쿼리) -> h2 데이터베이스를 기준으로 작성
	@Query(value = "select * from facilities where facilities_detail like %:facilitiesDetail% order by price desc", nativeQuery = true)
	List<Facilities> findByFacilitiesDetailByNative(@Param("facilitiesDetail") String facilitiesDetail);
	
	// 퀴즈 2-1
	/*
	 * @Query("select e from facilities e where e.price >= :price") List<Facilities>
	 * findByPrice(@Param("price") int price);
	 */

	// 퀴즈 2-2
//	@Query("select i from facilities i where i.facilitiesNm = :facilitiesNm and i.facilitiesStatus = :facilitiesStatus")
//	List<Facilities> findByFacilitiesNmAndFacilitiesStatus2(@Param("facilitiesNm") String facilitiesNm, @Param("facilitiesStatus") FacilitiesStatus facilitiesStatus);
//	
//	@Query("select * from Item")
//	List<Item> findByItem(@Param("itemNm") String itemNm, @Param("itemStatus") ItemStatus itemStatus);
}