package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Pipeline extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private boolean recommended;

  private Double cost;

  @ManyToOne private Project project;

  @OneToMany(mappedBy = "pipeline")
  private List<PipelineTool> pipelineTools;
}
