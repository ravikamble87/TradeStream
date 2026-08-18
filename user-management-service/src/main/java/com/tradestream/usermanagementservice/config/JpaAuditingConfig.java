package com.tradestream.usermanagementservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Kept separate from the @SpringBootApplication class on purpose: @WebMvcTest slices
// always load that class as their configuration source, and @EnableJpaAuditing there
// would drag in JPA auditing infrastructure (and fail with "JPA metamodel must not be
// empty") even in tests that never touch the database.
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
