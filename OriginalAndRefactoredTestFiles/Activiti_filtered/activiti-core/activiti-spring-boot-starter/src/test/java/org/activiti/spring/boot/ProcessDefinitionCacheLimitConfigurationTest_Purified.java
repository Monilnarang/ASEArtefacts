package org.activiti.spring.boot;

import static org.assertj.core.api.Assertions.assertThat;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = { "spring.activiti.process-definition-cache-limit=100" })
public class ProcessDefinitionCacheLimitConfigurationTest_Purified {

    @Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private ActivitiProperties activitiProperties;

    @Autowired
    RepositoryService repositoryService;

    @Test
    public void shouldConfigureProcessDefinitionCacheLimit_1() {
        assertThat(activitiProperties.getProcessDefinitionCacheLimit()).isEqualTo(100);
    }

    @Test
    public void shouldConfigureProcessDefinitionCacheLimit_2() {
        assertThat(processEngineConfiguration.getProcessDefinitionCacheLimit()).isEqualTo(activitiProperties.getProcessDefinitionCacheLimit());
    }
}
