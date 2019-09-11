package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String description;

  private LocalDateTime initialDate;
  private LocalDateTime expirationDate;
  private Integer cost;

  @OneToMany(mappedBy = "subscription")
  private List<CompanySubscription> companySubscriptions;
}
