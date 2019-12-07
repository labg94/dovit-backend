package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Master table/entity that stores the type of licenses. It can be: hosted, free or paid
 * @author Ramón París
 * @since 01-10-2019
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseType {

    @Id
    private Long id;

    @NotEmpty
    private String description;

    @OneToMany(mappedBy = "licenseType")
    private List<License> licenses;
}
