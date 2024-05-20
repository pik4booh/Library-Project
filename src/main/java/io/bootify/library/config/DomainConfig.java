package io.bootify.library.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.library.domain")
@EnableJpaRepositories("io.bootify.library.repos")
@EnableTransactionManagement
public class DomainConfig {
}
