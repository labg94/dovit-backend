package com.dovit.backend.services;

import com.dovit.backend.domain.Holding;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.HoldingRequest;
import com.dovit.backend.payloads.responses.HoldingResponse;
import com.dovit.backend.repositories.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 23-06-20
 */
@Service
@RequiredArgsConstructor
public class HoldingServiceImpl implements HoldingService {

  private final ModelMapper modelMapper;
  private final HoldingRepository holdingRepository;

  @Override
  @Transactional
  public List<HoldingResponse> findAll() {
    List<Holding> holdings = holdingRepository.findAll();
    return holdings.stream()
        .map(holding -> modelMapper.map(holding, HoldingResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public HoldingResponse findResponseById(Long id) {
    final Holding holding =
        holdingRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Holding", "id", id));
    return modelMapper.map(holding, HoldingResponse.class);
  }

  @Override
  @Transactional
  public Holding save(HoldingRequest request) {
    request.setId(null);
    final Holding holding = modelMapper.map(request, Holding.class);
    return holdingRepository.save(holding);
  }

  @Override
  @Transactional
  public Holding update(HoldingRequest request) {
    final Holding holding = holdingRepository.findById(request.getId()).orElseThrow();
    modelMapper.map(request, holding);
    return holdingRepository.save(holding);
  }
}
