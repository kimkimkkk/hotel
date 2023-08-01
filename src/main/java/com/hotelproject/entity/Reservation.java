package com.hotelproject.entity;

import java.time.LocalDateTime;

import com.hotelproject.constant.OrderStatus;
import com.hotelproject.dto.ReservationDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Reservations")
@Getter
@Setter
@ToString
public class Reservation{
	
	@Id
	@Column(name = "Reserv_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime orderDate; // 주문일
	
	private String Email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item itemId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "facilities_id")
	private Facilities facilities;
	
	private String endDay;
	
	private String startDay;
	
	private int person;
	
	private long night;
	
	private String tprice;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; // 주문상태
	
	public static Reservation createorder(ReservationDto reservationDto, String email){
		Reservation Reservation = new Reservation();
		
		Reservation.setEmail(email);
		Reservation.setItemId(reservationDto.getItemId());
		Reservation.setFacilities(reservationDto.getFacilities());
		Reservation.setStartDay(reservationDto.getStartDay());
		Reservation.setEndDay(reservationDto.getEndDay());
		Reservation.setPerson(reservationDto.getPerson());
		Reservation.setTprice(reservationDto.getTprice());
		Reservation.setNight(reservationDto.getNight());
		Reservation.setOrderStatus(OrderStatus.ORDER);
		
		return Reservation;
	}
//	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY) // 연관관계의 주인 설정(외래키 지정)
//	private List<OrderIte> orderItems = new ArrayList<>();
	
	
	
	
	
	
	
	
	
	
	
	
}