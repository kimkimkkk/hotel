package com.hotelproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.entity.Facilities;


public interface FacilitiesRepository extends JpaRepository<Facilities, Long>, FacilitiesRepositoryCustom{ // <해당 repository에서 사용할 Entity, Entity 클래스의 기본키 타입>
	
	// select * from item where item_nm = ?
	List<Facilities> findByFacilitiesNm(String facilitiesNm);
	
	List<Facilities> findByFacilitiesNmAndItemStatus(String facilitiesNm, ItemStatus itemStatus);
	List<Facilities> findByPriceBetween(int s, int e);
	List<Facilities> findByRegTimeAfter(LocalDateTime regTime);
	List<Facilities> findByItemStatusNotNull();
	List<Facilities> findByFacilitiesDetailEndingWith(String facilitiesDetail);
	List<Facilities> findByFacilitiesNmOrFacilitiesDetail(String facilitiesNm, String facilitiesDetail);
	List<Facilities> findByPriceLessThanOrderByPrice(int e);

	@Query("select e from Facilities e where e.facilitiesDetail like %:facilitiesDetail% order by e.price desc")
	List<Facilities> findByFacilitiesDetail(@Param("facilitiesDetail") String facilitiesDetail);
	
	@Query(value = "select * from facilities where facilities_detail like %:facilitiesDetail% order by price desc", nativeQuery = true)
	List<Facilities> findByFacilitiesDetailByNative(@Param("facilitiesDetail") String facilitiesDetail);
	
    @Modifying
    @Query(value = "delete from facilities where facilities_id = :facilitiesId", nativeQuery = true)
    void deleteByFacilitiesIdByNative(@Param("facilitiesId") long facilitiesId);
}