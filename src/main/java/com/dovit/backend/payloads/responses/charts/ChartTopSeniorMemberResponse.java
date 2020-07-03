package com.dovit.backend.payloads.responses.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartTopSeniorMemberResponse {

  private Long id;
  private String fullName;
  private List<ChartMemberKnowledge> knowledgeList;
  private int maxValue;
}
