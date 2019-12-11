package com.dovit.backend.services;

import com.dovit.backend.model.responses.LevelResponse;
import com.dovit.backend.repositories.LevelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    @Override
    public List<LevelResponse> findAll() {
        return levelRepository.findAll().stream().map(l -> new ModelMapper().map(l, LevelResponse.class)).collect(Collectors.toList());
    }
}
