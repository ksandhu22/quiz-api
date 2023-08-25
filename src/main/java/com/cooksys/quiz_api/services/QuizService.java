package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;

public interface QuizService {

	List<QuizResponseDto> getAllQuizzes();

//	QuizResponseDto createQuiz(QuizRequestDto quizRequestDto);
//
//	QuizResponseDto getQuizById(Long id);
//
//	QuizResponseDto deleteQuiz(Long id);
//
//	QuizResponseDto updateQuiz(Long id, String newName);
//
//	QuestionResponseDto randomQuestion(Long id);
//
//	QuizResponseDto addQuestion(Long id, QuestionRequestDto questionRequestDto);
//
//	QuestionResponseDto deleteQuestion(Long id, Long questionId);

}
