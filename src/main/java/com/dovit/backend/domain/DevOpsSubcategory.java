package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Subarea that this tool is part. Example: Issue Tracking, Kanban Dashboards, Repository,
 * Automatized test, ....
 *
 * @author Ramón París
 * @since 01-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devops_subcategories")
@Builder
public class DevOpsSubcategory extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_sequence")
  @SequenceGenerator(initialValue = 100, name = "subcategory_sequence")
  @Column(name = "devops_subcategory_id")
  private Long id;

  @NotEmpty private String description;

  @Column(columnDefinition = "boolean default true")
  private boolean active;

  @ManyToMany(mappedBy = "subcategories")
  List<Tool> tools;

  @ManyToOne
  @JoinColumn(nullable = false, name = "devops_category_id")
  private DevOpsCategory devOpsCategory;

  @OneToMany(mappedBy = "subcategory")
  private List<SuggestionMailbox> suggestions;
}
