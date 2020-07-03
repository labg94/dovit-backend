package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import com.dovit.backend.domain.keys.ProjectMemberId;
import lombok.*;

import javax.persistence.*;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@SuppressWarnings("JpaModelReferenceInspection")
@EqualsAndHashCode(callSuper = true)
@Entity
@IdClass(ProjectMemberId.class)
@Data
@Table(name = "project_members")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember extends DateAudit {

  @Id
  @Column(name = "member_id")
  private Long memberId;

  @Id
  @Column(name = "project_id")
  private Long projectId;

  @Id
  @Column(name = "devops_category_id")
  private Long devOpsCategoryId;

  @ManyToOne
  @MapsId("member_id")
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @MapsId("project_id")
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne(optional = false)
  @MapsId("devops_category_id")
  @JoinColumn(name = "devops_category_id")
  private DevOpsCategory devOpsCategories;
}
