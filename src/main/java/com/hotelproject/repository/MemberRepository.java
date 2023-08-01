package com.hotelproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelproject.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long>{
	Member findByEmail(String email);
}
