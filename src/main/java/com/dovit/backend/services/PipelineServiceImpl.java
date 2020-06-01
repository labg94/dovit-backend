package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.model.requests.PipelineRecommendationRequest;
import com.dovit.backend.model.responses.PipelineResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.PipelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {

  private final PipelineRepository pipelineRepository;
  private final DevOpsCategoryRepository devOpsCategoryRepository;

  @Override
  @Transactional
  public List<PipelineResponse> generatePipelineRecommendation(
      PipelineRecommendationRequest request) {

    final List<DevOpsCategory> categories = devOpsCategoryRepository.findAllByActiveOrderById(true);
    categories.stream()
        .map(
            category -> {
              return null;
            });
    return null;
  }

  @Override
  public List<PipelineResponse> findAllByProjectId(Long projectId) {
    return null;
  }
}
