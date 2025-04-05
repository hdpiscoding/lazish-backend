package com.lazish.service.implementations;

import com.lazish.dto.ExerciseDTO;
import com.lazish.entity.Exercise;
import com.lazish.entity.Lesson;
import com.lazish.mapper.ExerciseMapper;
import com.lazish.mapper.LessonMapper;
import com.lazish.repository.ExerciseRepository;
import com.lazish.repository.LessonRepository;
import com.lazish.service.interfaces.ExerciseService;
import com.lazish.utils.enums.ExerciseType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final ApplicationContext applicationContext;


    @Override
    @Transactional
    public Exercise createExercise(ExerciseDTO exerciseDTO, Lesson lesson) {
        Exercise exercise = new Exercise();
        if(ExerciseType.valueOf(exerciseDTO.getExerciseType()) == ExerciseType.LISTENING){
            exercise = Exercise
                    .builder()
                    .question(null)
                    .exerciseType(ExerciseType.valueOf(exerciseDTO.getExerciseType()))
                    .audio(exerciseDTO.getAudio())
                    .wordPool(exerciseDTO.getWordPool())
                    .answer(exerciseDTO.getAnswer())
                    .placeholders(exerciseDTO.getPlaceholders())
                    .questionPair(null)
                    .answerPair(null)
                    .lesson(lesson)
                    .build();
        }
        else if(ExerciseType.valueOf(exerciseDTO.getExerciseType()) == ExerciseType.SPEAKING){
            exercise = Exercise
                    .builder()
                    .question(exerciseDTO.getQuestion())
                    .exerciseType(ExerciseType.valueOf(exerciseDTO.getExerciseType()))
                    .audio(exerciseDTO.getAudio())
                    .wordPool(null)
                    .answer(null)
                    .placeholders(0)
                    .questionPair(null)
                    .answerPair(null)
                    .lesson(lesson)
                    .build();
        }
        else if(ExerciseType.valueOf(exerciseDTO.getExerciseType()) == ExerciseType.TRANSLATE){
            exercise = Exercise
                    .builder()
                    .question(exerciseDTO.getQuestion())
                    .exerciseType(ExerciseType.valueOf(exerciseDTO.getExerciseType()))
                    .audio(exerciseDTO.getAudio())
                    .wordPool(exerciseDTO.getWordPool())
                    .answer(exerciseDTO.getAnswer())
                    .placeholders(exerciseDTO.getPlaceholders())
                    .questionPair(null)
                    .answerPair(null)
                    .lesson(lesson)
                    .build();
        }
        else{
            exercise = Exercise
                    .builder()
                    .question(null)
                    .exerciseType(ExerciseType.valueOf(exerciseDTO.getExerciseType()))
                    .audio(null)
                    .wordPool(null)
                    .answer(null)
                    .placeholders(0)
                    .questionPair(exerciseDTO.getQuestionPair())
                    .answerPair(exerciseDTO.getAnswerPair())
                    .lesson(lesson)
                    .build();
        }

        return exerciseRepository.save(exercise);
    }

    @Override
    @Transactional
    public ExerciseDTO updateExercise(UUID id, ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Exercise not found!"));
        exercise.setExerciseType(ExerciseType.valueOf(exerciseDTO.getExerciseType()));
        if(exercise.getExerciseType() == ExerciseType.TRANSLATE){
            exercise.setExerciseType(ExerciseType.valueOf(exerciseDTO.getExerciseType()));
            if(exerciseDTO.getQuestion() != null) { exercise.setQuestion(exerciseDTO.getQuestion()); }
            if(exerciseDTO.getAnswer() != null) { exercise.setAnswer(exerciseDTO.getAnswer()); }
            if(exerciseDTO.getWordPool() != null) { exercise.setWordPool(exerciseDTO.getWordPool()); }
            if(exerciseDTO.getPlaceholders() != 0) { exercise.setPlaceholders(exerciseDTO.getPlaceholders()); }
        }
        else if(exercise.getExerciseType() == ExerciseType.LISTENING){
            if(exerciseDTO.getAudio() != null) { exercise.setAudio(exerciseDTO.getAudio()); }
            if(exerciseDTO.getWordPool() != null) { exercise.setWordPool(exerciseDTO.getWordPool()); }
            if(exerciseDTO.getAnswer() != null) { exercise.setAnswer(exerciseDTO.getAnswer()); }
            if(exerciseDTO.getPlaceholders() != 0) { exercise.setPlaceholders(exerciseDTO.getPlaceholders()); }
        }
        else if(exercise.getExerciseType() == ExerciseType.SPEAKING){
            if(exerciseDTO.getQuestion() != null) { exercise.setQuestion(exerciseDTO.getQuestion()); }
            if(exerciseDTO.getAudio() != null) { exercise.setAudio(exerciseDTO.getAudio()); }
        }
        else{
            if(exerciseDTO.getQuestionPair() != null) { exercise.setQuestionPair(exerciseDTO.getQuestionPair()); }
            if(exerciseDTO.getAnswerPair() != null) { exercise.setAnswerPair(exerciseDTO.getAnswerPair()); }
        }

        return exerciseMapper.toDto(exerciseRepository.save(exercise));
    }

    @Override
    @Transactional
    public void deleteExercise(UUID id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ExerciseDTO addExerciseToLesson(ExerciseDTO exerciseDTO, UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new EntityNotFoundException("Lesson not found!"));

        // Make sure @Transactional works correctly
        ExerciseServiceImpl self = applicationContext.getBean(ExerciseServiceImpl.class);
        Exercise exercise = self.createExercise(exerciseDTO, lesson);
        return exerciseMapper.toDto(exercise);
    }
}
