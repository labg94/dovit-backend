package com.dovit.backend.repositories;

import com.dovit.backend.domain.PipelineTool;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ramón París
 * @since 14-06-20
 */
public interface PipelineToolRepository extends JpaRepository<PipelineTool, Long> {

  void deleteAllByPipelineId(Long pipelineId);
}
