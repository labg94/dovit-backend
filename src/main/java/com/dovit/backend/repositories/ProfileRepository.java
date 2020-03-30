package com.dovit.backend.repositories;

import com.dovit.backend.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ramón París
 * @since 08-12-2019
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  List<Profile> findAllByIdIn(List<Long> ids);
}
