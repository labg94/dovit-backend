package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import com.dovit.backend.domain.keys.ToolProjectTypeId;
import lombok.*;

import javax.persistence.*;

/**
 * @author Ramón París
 * @since 08-06-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tool_project_type")
@IdClass(ToolProjectTypeId.class)
@Builder
public class ToolProjectType extends DateAudit {

  @Id
  @Column(nullable = false, name = "project_type_id")
  private Long projectTypeId;

  @Id
  @Column(nullable = false, name = "tool_id")
  private Long toolId;

  @ManyToOne
  @JoinColumn(name = "project_type_id")
  @MapsId("projectTypeId")
  private ProjectType projectType;

  @ManyToOne
  @JoinColumn(name = "tool_id")
  @MapsId("toolId")
  private Tool tool;
}
