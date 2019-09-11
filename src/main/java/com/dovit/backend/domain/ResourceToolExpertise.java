package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ResourceToolExpertiseId.class)
public class ResourceToolExpertise {

  @Id
  private Long idResource;

  @Id
  private Long idTool;

  @Id
  private Long idExpertise;

  @ManyToOne
  @MapsId("idResource")
  @JoinColumn(name = "idResource")
  private Resource resource;

  @ManyToOne
  @MapsId("idTool")
  @JoinColumn(name = "idTool")
  private Tool tool;

  @ManyToOne
  @MapsId("idExpertise")
  @JoinColumn(name = "idExpertise")
  private Expertise expertise;
}
