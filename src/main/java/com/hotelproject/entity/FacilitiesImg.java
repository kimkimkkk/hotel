package com.hotelproject.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "facilities_img")
@Getter
@Setter
@ToString
public class FacilitiesImg extends BaseEntity{
		
	@Id
	@Column(name = "item_img_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String imgName; //바뀐 이미지 파일명
	
	private String oriImgName; //원본 이미지 파일명
	
	private String imgUrl; //이미지 경로
	
	private String repimgYn; //대표이미지 여부
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "facilities_id")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private	Facilities facilities;
	
	public void updateFacilitiesImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
		
	}
}