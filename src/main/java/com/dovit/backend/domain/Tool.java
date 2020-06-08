package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

/**
 * IT Tools that solve a certain problem. Example: Jenkins, Gitlab, Github, JMeter, ....
 *
 * @author Ramón París
 * @since 29-09-2019
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tools")
@Builder
@ToString
public class Tool extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tool_sequence")
  @SequenceGenerator(initialValue = 100, name = "tool_sequence")
  @Column(name = "tool_id")
  private Long id;

  @NotEmpty private String name;

  private String description;

  private String imageUrl;

  @Column(columnDefinition = "boolean default true")
  private boolean active;

  @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<License> licenses;

  @ManyToMany(cascade = CascadeType.REFRESH)
  @JoinTable(
      name = "tool_subcategory",
      joinColumns = @JoinColumn(name = "tool_id"),
      inverseJoinColumns = @JoinColumn(name = "subcategory_id"))
  private List<DevOpsSubcategory> subcategories;

  @OneToMany(mappedBy = "tool")
  private List<ToolProfile> toolProfile;

  @OneToMany(mappedBy = "tool")
  private List<PipelineTool> pipelineTools;

  @OneToMany(mappedBy = "tool")
  private Collection<ToolProjectType> projectTypes;

  public Collection<ToolProjectType> getProjectTypes() {
    return projectTypes;
  }

  public void setProjectTypes(Collection<ToolProjectType> projectTypes) {
    this.projectTypes = projectTypes;
  }
}
