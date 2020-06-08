package com.dovit.backend.domain;

import com.dovit.backend.domain.keys.ToolProfileId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SuppressWarnings("JpaModelReferenceInspection")
@Entity(name = "tool_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ToolProfileId.class)
@Builder
public class ToolProfile {

  @Id
  @Column(name = "member_id")
  private Long memberId;

  @ManyToOne(optional = false)
  @MapsId("member_id")
  @JoinColumn(name = "member_id")
  private Member member;

  @Id
  @Column(name = "tool_id")
  private Long toolId;

  @ManyToOne(optional = false)
  @MapsId("tool_id")
  @JoinColumn(name = "tool_id")
  private Tool tool;

  @Id
  @Column(name = "level_id")
  private Long levelId;

  @ManyToOne(optional = false)
  @MapsId("level_id")
  @JoinColumn(name = "level_id")
  private Level level;
}
