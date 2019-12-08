package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Level {

    @Id
    @Column(name = "level_id")
    private Long levelId;

    private String description;

    @OneToMany(mappedBy = "level")
    private List<ToolProfile> toolProfile;

}
