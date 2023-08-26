package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.exceptions.BadRequestException;
import com.cooksys.quiz_api.exceptions.NotFoundException;

public interface QuizService {

	List<QuizResponseDto> getAllQuizzes();

	QuizResponseDto createQuiz(QuizRequestDto newQuiz);


	QuizResponseDto deleteQuiz(Long id);

	QuizResponseDto updateQuiz(Long id, String newName);

	QuestionResponseDto randomQuestion(Long id);

	QuizResponseDto addQuestion(Long id, QuestionRequestDto questionRequestDto);
//
	QuestionResponseDto deleteQuestion(Long id, Long questionId);

}
