package com.dovit.backend.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ramón París
 * @since 10-09-2019
 */
public class ResourceToolExpertiseId implements Serializable {

    private Long idResource;
    private Long idTool;
    private Long idExpertise;

    public ResourceToolExpertiseId() {
    }

    public ResourceToolExpertiseId(Long idResource, Long idTool, Long idExpertise) {
        this.idResource = idResource;
        this.idTool = idTool;
        this.idExpertise = idExpertise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceToolExpertiseId that = (ResourceToolExpertiseId) o;
        return Objects.equals(idResource, that.idResource)
                && Objects.equals(idTool, that.idTool)
                && Objects.equals(idExpertise, that.idExpertise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResource, idTool, idExpertise);
    }
}
