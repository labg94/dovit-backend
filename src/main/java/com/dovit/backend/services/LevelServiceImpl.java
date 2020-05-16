package com.dovit.backend.services;

import com.dovit.backend.model.responses.LevelResponse;
import com.dovit.backend.repositories.LevelRepository;
import lombok.RequiredArgsConstructor;
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
public class LevelServiceImpl implements LevelService {

  private final LevelRepository levelRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<LevelResponse> findAll() {
    return levelRepository.findAll().stream()
        .map(l -> modelMapper.map(l, LevelResponse.class))
        .collect(Collectors.toList());
  }
}
