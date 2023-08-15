package com.hotelproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotelproject.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	// Reservation findByItemNm(String email);
//	Member findByEmail(String email);
	@Modifying
	@Query(value = "delete from Reservations where facilities_id = :facilitiesId", nativeQuery = true)
	void deleteByfacilitiesId(@Param("facilitiesId") long facilitiesId);
	
	@Modifying
	@Query(value = "delete from Reservations where item_id = :itemId", nativeQuery = true)
	void deleteByitemId(@Param("itemId") long itemId);
}
