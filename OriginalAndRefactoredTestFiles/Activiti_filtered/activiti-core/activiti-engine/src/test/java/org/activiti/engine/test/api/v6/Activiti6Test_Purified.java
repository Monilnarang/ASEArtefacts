package org.activiti.engine.test.api.v6;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class Activiti6Test_Purified extends PluggableActivitiTestCase {

    @Test
    @org.activiti.engine.test.Deployment
    public void testOneTaskProcess_1_testMerged_1() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oneTaskProcess");
        assertThat(processInstance).isNotNull();
        assertThat(processInstance.isEnded()).isFalse();
    }

    @Test
    @org.activiti.engine.test.Deployment
    public void testOneTaskProcess_3_testMerged_2() {
        Task task = taskService.createTaskQuery().singleResult();
        assertThat(task.getName()).isEqualTo("The famous task");
        assertThat(task.getAssignee()).isEqualTo("kermit");
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = "org/activiti/engine/test/api/v6/Activiti6Test.testOneTaskProcess.bpmn20.xml")
    public void testOneTaskProcessCleanupInMiddleOfProcess_1_testMerged_1() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oneTaskProcess");
        assertThat(processInstance).isNotNull();
        assertThat(processInstance.isEnded()).isFalse();
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = "org/activiti/engine/test/api/v6/Activiti6Test.testOneTaskProcess.bpmn20.xml")
    public void testOneTaskProcessCleanupInMiddleOfProcess_3_testMerged_2() {
        Task task = taskService.createTaskQuery().singleResult();
        assertThat(task.getName()).isEqualTo("The famous task");
        assertThat(task.getAssignee()).isEqualTo("kermit");
    }

    @Test
    @org.activiti.engine.test.Deployment
    public void testSimpleTimerBoundaryEvent_1_testMerged_1() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simpleBoundaryTimer");
        assertThat(processInstance).isNotNull();
        assertThat(processInstance.isEnded()).isFalse();
        assertThat(runtimeService.createExecutionQuery().count()).isEqualTo(0);
    }

    @Test
    @org.activiti.engine.test.Deployment
    public void testSimpleTimerBoundaryEvent_3() {
        Task task = taskService.createTaskQuery().singleResult();
        assertThat(task.getName()).isEqualTo("Task after timer");
    }

    @Test
    @org.activiti.engine.test.Deployment
    public void testSimpleTimerBoundaryEventTimerDoesNotFire_1_testMerged_1() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simpleBoundaryTimer");
        assertThat(processInstance).isNotNull();
        assertThat(processInstance.isEnded()).isFalse();
        assertThat(runtimeService.createExecutionQuery().count()).isEqualTo(0);
    }

    @Test
    @org.activiti.engine.test.Deployment
    public void testSimpleTimerBoundaryEventTimerDoesNotFire_3() {
        assertThat(managementService.createTimerJobQuery().count()).isEqualTo(1);
    }

    @Test
    @org.activiti.engine.test.Deployment
    public void testSimpleTimerBoundaryEventTimerDoesNotFire_4() {
        Task task = taskService.createTaskQuery().singleResult();
        assertThat(task.getName()).isEqualTo("The famous task");
    }

    @Test
    @org.activiti.engine.test.Deployment
    public void testSimpleTimerBoundaryEventTimerDoesNotFire_5() {
        assertThat(managementService.createTimerJobQuery().count()).isEqualTo(0);
    }
}
