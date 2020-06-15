package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import com.dovit.backend.domain.keys.PipelineToolId;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@IdClass(PipelineToolId.class)
@Builder
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

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private Object log;
}
