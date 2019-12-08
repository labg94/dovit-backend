package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    private Long id;

    @NotBlank
    private String description;


    @ManyToMany(mappedBy = "profiles")
    private List<Member> members;

}
