package com.dovit.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Ramón París
 * @since 29-09-2019
 */
@Configuration
@EnableJpaAuditing
public class AuditingConfig {
}
