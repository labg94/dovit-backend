package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private Instant start;

    private String observation;

    private Boolean finished;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "project")
    private List<ProjectMember> members;



}