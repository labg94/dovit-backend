package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class SuggestionMailbox extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestion_sequence")
  @SequenceGenerator(initialValue = 100, name = "suggestion_sequence")
  @Column(name = "suggestion_id")
  private Long id;

  @NotEmpty private String tool;

  @NotEmpty private String category;

  @NotEmpty private String subcategory;

  @Size(min = 1, max = 280, message = "Message not in range 1 to 280 characters")
  private String message;

  @Column(columnDefinition = "boolean default false")
  private boolean viewed;

  @ManyToOne private User suggestedBy;
}
