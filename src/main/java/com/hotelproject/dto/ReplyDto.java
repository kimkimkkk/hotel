package com.hotelproject.dto;



import org.modelmapper.ModelMapper;

import com.hotelproject.entity.Reply;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReplyDto {

	private Long id;
	
	@NotBlank(message = "답글을 달아주세요.")
	private String replry;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Reply createReply() {
		return modelMapper.map(this, Reply.class);
	}
	
	public static ReplyDto of(Reply reply) {
		return modelMapper.map(reply, ReplyDto.class);
	}
}
