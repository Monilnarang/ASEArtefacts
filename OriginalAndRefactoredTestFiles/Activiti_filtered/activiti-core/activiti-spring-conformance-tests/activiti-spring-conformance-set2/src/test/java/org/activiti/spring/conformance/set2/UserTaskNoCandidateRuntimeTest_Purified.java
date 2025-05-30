package org.activiti.spring.conformance.set2;

import static org.assertj.core.api.Assertions.assertThat;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessAdminRuntime;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.spring.conformance.util.RuntimeTestConfiguration;
import org.activiti.spring.conformance.util.security.SecurityUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserTaskNoCandidateRuntimeTest_Purified {

    private final String processKey = "usertaskwi-4d5c4312-e8fc-4766-a727-b55a4d3255e9";

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private ProcessAdminRuntime processAdminRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private TaskAdminRuntime taskAdminRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    @BeforeEach
    public void cleanUp() {
        RuntimeTestConfiguration.collectedEvents.clear();
    }

    @AfterEach
    public void cleanup() {
        securityUtil.logInAs("admin");
        Page<ProcessInstance> processInstancePage = processAdminRuntime.processInstances(Pageable.of(0, 50));
        for (ProcessInstance pi : processInstancePage.getContent()) {
            processAdminRuntime.delete(ProcessPayloadBuilder.delete(pi.getId()));
        }
        RuntimeTestConfiguration.collectedEvents.clear();
    }

    @Test
    public void shouldNotSeeTaskWithoutCandidateOrAssignee_1_testMerged_1() {
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey(processKey).withBusinessKey("my-business-key").withName("my-process-instance-name").build());
        assertThat(processInstance).isNotNull();
        assertThat(processInstance.getStatus()).isEqualTo(ProcessInstance.ProcessInstanceStatus.RUNNING);
        assertThat(processInstance.getBusinessKey()).isEqualTo("my-business-key");
        assertThat(processInstance.getName()).isEqualTo("my-process-instance-name");
        ProcessInstance processInstanceById = processRuntime.processInstance(processInstance.getId());
        assertThat(processInstanceById).isEqualTo(processInstance);
    }

    @Test
    public void shouldNotSeeTaskWithoutCandidateOrAssignee_6_testMerged_2() {
        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 50));
        assertThat(tasks.getTotalItems()).isEqualTo(0);
        tasks = taskRuntime.tasks(Pageable.of(0, 50));
        tasks = taskAdminRuntime.tasks(Pageable.of(0, 50));
        assertThat(tasks.getTotalItems()).isEqualTo(1);
    }
}
