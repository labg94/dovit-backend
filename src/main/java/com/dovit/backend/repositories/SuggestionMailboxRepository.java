package com.dovit.backend.repositories;

import com.dovit.backend.domain.SuggestionMailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@Repository
public interface SuggestionMailboxRepository extends JpaRepository<SuggestionMailbox, Long> {}
