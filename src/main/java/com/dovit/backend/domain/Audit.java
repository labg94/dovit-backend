package com.dovit.backend.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Audit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "audit_id")
  private Long id;

  private LocalDateTime actionDate;

  @ManyToOne private User user;

  private String status;

  private String message;

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private Object data;

  private String databaseUser;
}
