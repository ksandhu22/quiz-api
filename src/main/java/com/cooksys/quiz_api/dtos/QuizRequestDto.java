package com.cooksys.quiz_api.dtos;

import java.util.List;

import com.cooksys.quiz_api.entities.Quiz;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class QuizRequestDto {

	 private Long id;

	 private String text;
	 
	 public Quiz quiz;

	 public List<AnswerResponseDto> answers;
}
