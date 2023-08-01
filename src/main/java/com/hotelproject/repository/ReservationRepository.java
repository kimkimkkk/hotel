package com.hotelproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelproject.entity.Item;
import com.hotelproject.entity.Member;
import com.hotelproject.entity.Reservation;


public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	//Reservation findByItemNm(String email);
//	Member findByEmail(String email);
}
