package org.apache.hadoop.mapred;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.http.HttpServer2;
import org.junit.Test;

public class TestJobEndNotifier_Purified {

    HttpServer2 server;

    URL baseUrl;

    @SuppressWarnings("serial")
    public static class JobEndServlet extends HttpServlet {

        public static volatile int calledTimes = 0;

        public static URI requestUri;

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            InputStreamReader in = new InputStreamReader(request.getInputStream());
            PrintStream out = new PrintStream(response.getOutputStream());
            calledTimes++;
            try {
                requestUri = new URI(null, null, request.getRequestURI(), request.getQueryString(), null);
            } catch (URISyntaxException e) {
            }
            in.close();
            out.close();
        }
    }

    @SuppressWarnings("serial")
    public static class DelayServlet extends HttpServlet {

        public static volatile int calledTimes = 0;

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            boolean timedOut = false;
            calledTimes++;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                timedOut = true;
            }
            assertTrue("DelayServlet should be interrupted", timedOut);
        }
    }

    @SuppressWarnings("serial")
    public static class FailServlet extends HttpServlet {

        public static volatile int calledTimes = 0;

        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            calledTimes++;
            throw new IOException("I am failing!");
        }
    }

    @Before
    public void setUp() throws Exception {
        new File(System.getProperty("build.webapps", "build/webapps") + "/test").mkdirs();
        server = new HttpServer2.Builder().setName("test").addEndpoint(URI.create("http://localhost:0")).setFindPort(true).build();
        server.addServlet("delay", "/delay", DelayServlet.class);
        server.addServlet("jobend", "/jobend", JobEndServlet.class);
        server.addServlet("fail", "/fail", FailServlet.class);
        server.start();
        int port = server.getConnectorAddress(0).getPort();
        baseUrl = new URL("http://localhost:" + port + "/");
        JobEndServlet.calledTimes = 0;
        JobEndServlet.requestUri = null;
        DelayServlet.calledTimes = 0;
        FailServlet.calledTimes = 0;
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    private static JobStatus createTestJobStatus(String jobId, int state) {
        return new JobStatus(JobID.forName(jobId), 0.5f, 0.0f, state, "root", "TestJobEndNotifier", null, null);
    }

    private static JobConf createTestJobConf(Configuration conf, int retryAttempts, String notificationUri) {
        JobConf jobConf = new JobConf(conf);
        jobConf.setInt("job.end.retry.attempts", retryAttempts);
        jobConf.set("job.end.retry.interval", "0");
        jobConf.setJobEndNotificationURI(notificationUri);
        return jobConf;
    }

    @Test
    public void testLocalJobRunnerUriSubstitution_1() throws InterruptedException {
        assertEquals(1, JobEndServlet.calledTimes);
    }

    @Test
    public void testLocalJobRunnerUriSubstitution_2() throws InterruptedException {
        assertEquals("jobid=job_20130313155005308_0001&status=SUCCEEDED", JobEndServlet.requestUri.getQuery());
    }

    @Test
    public void testNotificationTimeout_1() throws InterruptedException {
        assertEquals(1, DelayServlet.calledTimes);
    }

    @Test
    public void testNotificationTimeout_2() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        assertTrue(elapsedTime < 2000);
    }
}
