package com.dovit.backend.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * IT Tools that solve a certain problem. Example: Jenkins, Gitlab, Github, JMeter, ....
 *
 * @author Ramón París
 * @since 29-09-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tools")
@Builder
@ToString
public class Tool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tool_id")
  private Long id;

  @NotEmpty private String name;

  private String imageUrl;

  @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<License> licenses;

  @ManyToMany
  @JoinTable(
      name = "tool_subcategory",
      joinColumns = @JoinColumn(name = "tool_id"),
      inverseJoinColumns = @JoinColumn(name = "subcategory_id"))
  private List<DevOpsSubcategory> subcategories;

  @OneToMany(mappedBy = "tool")
  private List<ToolProfile> toolProfile;
}
