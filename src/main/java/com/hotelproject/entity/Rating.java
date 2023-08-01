package com.hotelproject.entity;


import com.hotelproject.constant.RatingNm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@Column(name = "rating_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private int discount;
	
	private String service;
	
	@Enumerated(EnumType.STRING)
	private RatingNm ratingNm;
}
