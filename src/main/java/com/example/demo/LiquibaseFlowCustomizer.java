package com.example.demo;

import com.datical.liquibase.ext.command.FlowCommandStep;
// import liquibase.Contexts;
// import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.command.CommandScope;
import liquibase.integration.spring.Customizer;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseFlowCustomizer<T extends Liquibase> implements Customizer<T> {

    @Value("${mongo.url}")
    private String mongoUrl;

    @Value("${mongo.username}")
    private String mongoUsername;

    @Value("${mongo.password}")
    private String mongoPassword;

    @Value("${mongo.change-log}")
    private String mongoChangelogFile;


    @Value("${liquibase.flowfile}")
    private String flowFile;

    @Value("${liquibase.reports.enabled}")
    private String isReportsEnabled;

    @Value("${liquibase.license_key}")
    private String licenseKey;

    // private static final Logger LOG = LoggerFactory.getLogger(LiquibaseFlowCustomizer.class);

    @Override
    public void customize(T liquibase) {
        // System.setProperty("liquibaseProLicenseKey", licenseKey);

        System.setProperty("liquibase.command.url", mongoUrl);
        System.setProperty("liquibase.command.username", mongoUsername);
        System.setProperty("liquibase.command.password", mongoPassword);
        System.setProperty("liquibase.command.changelogFile", mongoChangelogFile);

        System.setProperty("liquibase.licenseKey", licenseKey);
        System.setProperty("liquibase.command.flowFile", flowFile);
        System.setProperty("liquibase.reports.enabled", isReportsEnabled);


        try {
            // liquibase.listUnrunChangeSets(new Contexts(), new LabelExpression())
            //                 .forEach(changeSet -> LOG.info("Unrun changeset: {}", changeSet));

            new CommandScope(FlowCommandStep.COMMAND_NAME)
                .addArgumentValue(FlowCommandStep.FLOW_FILE, flowFile)
                .execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}