package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

/**
 * This entity define the "Parent" category. This can be: Planning, Build, Test, CI/CD,
 * Configuration, Monitoring... This entity has "child" category that are technologies of this
 * "parent" category.
 *
 * @author Ramón París
 * @since 01-10-2019
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "devops_categories")
@Builder
public class DevOpsCategory extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
  @SequenceGenerator(initialValue = 100, name = "category_sequence")
  private Long id;

  @NotEmpty private String description;

  @Column(columnDefinition = "boolean default true")
  private boolean active;

  @OneToMany(mappedBy = "devOpsCategory")
  private List<DevOpsSubcategory> subcategories;

  @OneToMany(mappedBy = "devOpsCategories")
  private List<ProjectMember> projectMembers;

  @OneToMany(mappedBy = "category")
  private List<PipelineTool> pipelineTools;

  @OneToMany(mappedBy = "category")
  private Collection<SuggestionMailbox> suggestions;
}
