package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * This entity define the "Parent" category. This can be: Planning, Build, Test, CI/CD, Configuration, Monitoring... This entity has "child" category that are technologies of this "parent" category.
 * @author Ramón París
 * @since 01-10-2019
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "devops_categories")
public class DevOpsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    @OneToMany(mappedBy = "devOpsCategory", fetch = FetchType.EAGER)
    private List<DevOpsSubcategory> subcategories;

    @OneToMany(mappedBy = "devOpsCategories")
    private List<ProjectMember> projectMembers;


}
