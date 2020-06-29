package com.dovit.backend.payloads.responses.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartMemberKnowledge {

  private String category;
  private int value;
  private String tools;
}
