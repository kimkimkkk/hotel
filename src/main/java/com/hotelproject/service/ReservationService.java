package com.hotelproject.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotelproject.dto.ReservationDto;
import com.hotelproject.entity.Facilities;
import com.hotelproject.entity.Item;
import com.hotelproject.entity.Reservation;
import com.hotelproject.repository.FacilitiesImgRepository;
import com.hotelproject.repository.FacilitiesRepository;
import com.hotelproject.repository.ItemImgRepository;
import com.hotelproject.repository.ItemRepository;
import com.hotelproject.repository.MemberRepository;
import com.hotelproject.repository.ReservationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final ItemImgRepository itemImgRepository;
	private final FacilitiesRepository facilitiesRepository;

	public Reservation saveReservation(Reservation reservation) {
		Reservation saveReservation;
		saveReservation = reservationRepository.save(reservation);

		return saveReservation; // 회원가입된 데이터를 칮는다.
	}

	public Item setItem(Long itemId) {
		Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
		Facilities facilities = facilitiesRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

		return item;
	}

	public Facilities setFacilities(Long facilitieId) {
		Facilities facilities = facilitiesRepository.findById(facilitieId).orElseThrow(EntityNotFoundException::new);

		return facilities;
	}

	public Long order(ReservationDto orderDto, String email) {
		List<Reservation> orderItemList = new ArrayList<>();
		Reservation orderItem = Reservation.createorder(orderDto, email);
		orderItemList.add(orderItem);

		reservationRepository.save(orderItem);

		return orderItem.getId();

	}

	public void deletFacilities(Long facilitiesId) {
		reservationRepository.deleteByfacilitiesId(facilitiesId);
	}

	public void deletItems(Long itemId) {
		reservationRepository.deleteByitemId(itemId);

	}
}
