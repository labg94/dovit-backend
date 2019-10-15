package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Subarea that this tool is part. Example: Issue Tracking, Kanban Dashboards, Repository, Automatized test, ....
 * @author Ramón París
 * @since 01-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devops_subcategories")
public class DevOpsSubcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "devops_subcategory_id")
    private Long id;

    @NotEmpty
    private String description;

    @ManyToMany(mappedBy = "subcategories", fetch = FetchType.EAGER)
    List<Tool> tools;

    @ManyToOne
    @JoinColumn(nullable = false, name = "devops_category_id")
    private DevOpsCategory devOpsCategory;

}
