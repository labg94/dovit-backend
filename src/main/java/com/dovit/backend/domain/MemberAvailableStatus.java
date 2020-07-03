package com.dovit.backend.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Master table to store the possible statuses of members (Available, Partially available, Busy....)
 *
 * @author Ramón París
 * @since 21-05-20
 */
@Entity
@Table
@Data
public class MemberAvailableStatus {

  @Id private Long id;

  @Column private String description;
}
