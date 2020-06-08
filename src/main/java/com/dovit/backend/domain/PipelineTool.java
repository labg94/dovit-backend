package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import com.dovit.backend.domain.keys.PipelineToolId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Ramón París
 * @since 07-06-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pipeline_tool")
@IdClass(PipelineToolId.class)
public class PipelineTool extends DateAudit {

  @Id
  @Column(name = "pipeline_id")
  private Long pipelineId;

  @Id
  @Column(name = "tool_id")
  private Long toolId;

  @Id
  @Column(name = "category_id")
  private Long categoryId;

  @ManyToOne
  @JoinColumn(name = "pipeline_id", referencedColumnName = "id")
  @MapsId("pipelineId")
  private Pipeline pipeline;

  @ManyToOne
  @JoinColumn(name = "tool_id", referencedColumnName = "tool_id")
  @MapsId("toolId")
  private Tool tool;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  @MapsId("categoryId")
  private DevOpsCategory category;
}
