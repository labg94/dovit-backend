package com.dovit.backend.services;

import com.dovit.backend.model.responses.LevelResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
public interface LevelService {

    List<LevelResponse> findAll();

}
