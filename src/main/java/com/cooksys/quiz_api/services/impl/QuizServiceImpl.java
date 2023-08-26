package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.exceptions.BadRequestException;
import com.cooksys.quiz_api.exceptions.NotFoundException;
import com.cooksys.quiz_api.mappers.AnswerMapper;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.services.QuizService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;
	private final QuizMapper quizMapper;
	private final QuestionMapper questionMapper;
	private final QuestionRepository questionRepository;
	private final AnswerMapper answerMapper;
	private final AnswerRepository answerRepository;
	

	// DTO to entity for GET
	@Override
	public List<QuizResponseDto> getAllQuizzes() {
		return quizMapper.entitiesToDtos(quizRepository.findAll());
	}

	// Entity to DTO for Post
	@Override
	public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) throws BadRequestException {
		Quiz quiz = quizMapper.requestDtoToEntity(quizRequestDto);
		quizRepository.saveAndFlush(quiz);
		
		for(Question question : quiz.getQuestions()) {
			question.setQuiz(quiz);
			questionRepository.saveAndFlush(question);
			
			for(Answer answer : question.getAnswers()) {
				answer.setQuestion(question);
				answerRepository.saveAndFlush(answer);
			}
			
		}
		return quizMapper.entityToDto(quiz);

	}

	@Override
	public QuizResponseDto getQuizById(Long id) throws NotFoundException {
		Optional<Quiz> optionalQuiz = quizRepository.findById(id);
		if(optionalQuiz.isEmpty()) {
			throw new NotFoundException("No quiz found using id");
		}
		return quizMapper.entityToResponseDto(optionalQuiz.get());
	}

	@Override
	public QuizResponseDto deleteQuiz(Long id) throws NotFoundException{
		Optional<Quiz> findQuiz = quizRepository.findById(id);
		if(findQuiz.isEmpty()) {
			throw new NotFoundException("No Quiz Found with id: " + id);
		}
		
		QuizResponseDto deleteQuiz = quizMapper.entityToDto(findQuiz);
		quizRepository.deleteById(id);
		return deleteQuiz;
		}

	@Override
	public QuizResponseDto updateQuiz(Long id, String newName) throws NotFoundException {
		Optional<Quiz> findQuiz = quizRepository.findById(id);
		if(findQuiz.isEmpty()) {
			throw new NotFoundException("No Quiz Found with id: " + id);
		}		
		
		Quiz renameQuiz = quizMapper.dtoToEntity(newName);
		renameQuiz.setQuiz(findQuiz.get());
		quizRepository.saveAndFlush(renameQuiz).getById(id);
		
		
		return quizMapper.entityToDto(quizRepository.findById(id).get());
	}

	@Override
	public QuestionResponseDto randomQuestion(Long id) {
		
		return null;
	}

	@Override
	public QuizResponseDto addQuestion(Long id, QuestionRequestDto questionRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuestionResponseDto deleteQuestion(Long id, Long questionId) {
		// TODO Auto-generated method stub
		return null;
	}
//	
//	}
	
	
	
	
	

}
