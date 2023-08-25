package com.cooksys.quiz_api.dtos;

import com.cooksys.quiz_api.entities.Question;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class QuestionRequestDto {

	private Long id;

	private String text;

	public boolean correct;

	public Question question;
}
