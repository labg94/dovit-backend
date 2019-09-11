package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class License {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private LocalDateTime initialDate;
  private LocalDateTime expirationDate;

  @ManyToOne private Company company;

  @ManyToOne private Tool tool;

  @OneToMany(mappedBy = "license")
  private List<LicenseType> licenseTypes;
}
