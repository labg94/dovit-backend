package com.dovit.backend.util;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public enum RoleName {
    ROLE_ADMIN("Administrador"),
    ROLE_CLIENT("Cliente");

    private final String label;

    RoleName(String label) {
        this.label = label;
    }
}
