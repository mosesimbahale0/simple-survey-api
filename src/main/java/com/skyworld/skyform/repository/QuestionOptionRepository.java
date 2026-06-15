package com.skyworld.skyform.repository;

import com.skyworld.skyform.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM question_options WHERE id = :id", nativeQuery = true)
    void deleteByIdNative(Long id);
}