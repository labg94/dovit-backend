package com.dovit.backend.services;

import com.dovit.backend.domain.Holding;
import com.dovit.backend.payloads.requests.HoldingRequest;
import com.dovit.backend.payloads.responses.HoldingResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 23-06-20
 */
public interface HoldingService {

  List<HoldingResponse> findAll();

  HoldingResponse findResponseById(Long id);

  Holding save(HoldingRequest request);

  Holding update(HoldingRequest request);
}
