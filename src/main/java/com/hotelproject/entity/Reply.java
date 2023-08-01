package com.hotelproject.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // 엔티티 클래스로 정의
@Table(name="Reply") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Reply {

	@Id
	@Column(name="Reply_id") 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String reply;
	
	@Column(nullable = false)
	private String email;
	
}
