package edu.java.scrapper.configuration;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
@RequiredArgsConstructor
public class JooqConfiguration {
    private final DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider dataSourceConnectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DSLContext dsl(DataSourceConnectionProvider dataSourceConnectionProvider) {
        return new DefaultDSLContext(buildConfiguration(dataSourceConnectionProvider));
    }

    public org.jooq.Configuration buildConfiguration(DataSourceConnectionProvider connectionProvider) {
        return new DefaultConfiguration()
            .set(connectionProvider)
            .set(SQLDialect.POSTGRES)
            .set(new Settings().withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED))
            .set(new DefaultExecuteListenerProvider(new JooqExceptionTranslator()));
    }
}
