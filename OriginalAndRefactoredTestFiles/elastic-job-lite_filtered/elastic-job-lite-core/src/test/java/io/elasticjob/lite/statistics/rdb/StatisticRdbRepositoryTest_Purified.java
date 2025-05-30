package io.elasticjob.lite.statistics.rdb;

import com.google.common.base.Optional;
import io.elasticjob.lite.statistics.StatisticInterval;
import io.elasticjob.lite.statistics.type.job.JobRegisterStatistics;
import io.elasticjob.lite.statistics.type.job.JobRunningStatistics;
import io.elasticjob.lite.statistics.type.task.TaskResultStatistics;
import io.elasticjob.lite.statistics.type.task.TaskRunningStatistics;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class StatisticRdbRepositoryTest_Purified {

    private StatisticRdbRepository repository;

    @Before
    public void setup() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(org.h2.Driver.class.getName());
        dataSource.setUrl("jdbc:h2:mem:");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        repository = new StatisticRdbRepository(dataSource);
    }

    private Date getYesterday() {
        return new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
    }

    @Test
    public void assertFindTaskResultStatisticsWhenTableIsEmpty_1() {
        assertThat(repository.findTaskResultStatistics(new Date(), StatisticInterval.MINUTE).size(), is(0));
    }

    @Test
    public void assertFindTaskResultStatisticsWhenTableIsEmpty_2() {
        assertThat(repository.findTaskResultStatistics(new Date(), StatisticInterval.HOUR).size(), is(0));
    }

    @Test
    public void assertFindTaskResultStatisticsWhenTableIsEmpty_3() {
        assertThat(repository.findTaskResultStatistics(new Date(), StatisticInterval.DAY).size(), is(0));
    }

    @Test
    public void assertFindTaskRunningStatisticsWithDifferentFromDate_1_testMerged_1() {
        Date yesterday = getYesterday();
        assertTrue(repository.add(new TaskRunningStatistics(100, yesterday)));
        assertThat(repository.findTaskRunningStatistics(yesterday).size(), is(2));
    }

    @Test
    public void assertFindTaskRunningStatisticsWithDifferentFromDate_2_testMerged_2() {
        Date now = new Date();
        assertTrue(repository.add(new TaskRunningStatistics(100, now)));
        assertThat(repository.findTaskRunningStatistics(now).size(), is(1));
    }

    @Test
    public void assertFindJobRunningStatisticsWithDifferentFromDate_1_testMerged_1() {
        Date yesterday = getYesterday();
        assertTrue(repository.add(new JobRunningStatistics(100, yesterday)));
        assertThat(repository.findJobRunningStatistics(yesterday).size(), is(2));
    }

    @Test
    public void assertFindJobRunningStatisticsWithDifferentFromDate_2_testMerged_2() {
        Date now = new Date();
        assertTrue(repository.add(new JobRunningStatistics(100, now)));
        assertThat(repository.findJobRunningStatistics(now).size(), is(1));
    }

    @Test
    public void assertFindJobRegisterStatisticsWithDifferentFromDate_1_testMerged_1() {
        Date yesterday = getYesterday();
        assertTrue(repository.add(new JobRegisterStatistics(100, yesterday)));
        assertThat(repository.findJobRegisterStatistics(yesterday).size(), is(2));
    }

    @Test
    public void assertFindJobRegisterStatisticsWithDifferentFromDate_2_testMerged_2() {
        Date now = new Date();
        assertTrue(repository.add(new JobRegisterStatistics(100, now)));
        assertThat(repository.findJobRegisterStatistics(now).size(), is(1));
    }
}
