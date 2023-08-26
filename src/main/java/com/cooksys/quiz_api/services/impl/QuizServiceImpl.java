package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.AnswerMapper;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
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
	public QuizResponseDto createQuiz(QuizRequestDto newQuiz) {
		Quiz quiz = quizMapper.requestDtoToEntity(newQuiz);

		List<Question> questionList = quiz.getQuestions();

		Quiz saveQuiz = quizRepository.saveAndFlush(quiz);

		for (Question question : questionList) {

			question.setQuiz(saveQuiz);
			Question saveQuestion = questionRepository.saveAndFlush(question);

			for (Answer answer : question.getAnswers()) {
				answer.setQuestion(saveQuestion);
				answerRepository.saveAndFlush(answer);
			}

		}
		return quizMapper.entityToDto(saveQuiz);

	}

	@Override
	public QuizResponseDto deleteQuiz(Long id) {
		Quiz findQuiz = quizRepository.findById(id).get();
		quizRepository.deleteById(id);
		
		
		return quizMapper.entityToDto(findQuiz);
	}

	
	@Override
	public QuizResponseDto updateQuiz(Long id, String newName) {
		Quiz renameQuiz = quizRepository.findById(id).get();
			
		renameQuiz.setName(newName);
		
		return quizMapper.entityToDto(quizRepository.saveAndFlush(renameQuiz));
		
	}

	@Override
	public QuestionResponseDto randomQuestion(Long id) {
		Random r = new Random();
		List<QuestionResponseDto> questions = questionMapper.entitiesToDtos(quizRepository.getById(id).getQuestions());
		
		return questions.get(r.nextInt(questions.size()));
	}


	@Override
	public QuestionResponseDto deleteQuestion(Long id, Long questionId)  {
		Quiz foundQuiz = quizRepository.findById(id).get();
		
		QuestionResponseDto deleteQuestion = null;
		List<Question> questions = foundQuiz.getQuestions();
		for(Question question : questions) {
			if(Objects.equals(question.getId(), questionId)) {
				deleteQuestion = questionMapper.entityToDto(question);
				questionRepository.deleteById(questionId);
			}
		}
		
		return deleteQuestion;
		
	}

	@Override
	public QuizResponseDto addQuestion(Long id, QuestionRequestDto questionRequestDto) {
		Question questionToAdd = questionMapper.requestDtoToEntity(questionRequestDto);
		
		quizRepository.saveAndFlush(quizRepository.getById(id));
		
		
		return quizMapper.entityToDto(quizRepository.getById(id));
	}

}
