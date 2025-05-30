package org.apache.hadoop.yarn.server.nodemanager.health;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimerTask;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileContext;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Shell;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestNodeHealthScriptRunner_Purified {

    private static File testRootDir = new File("target", TestNodeHealthScriptRunner.class.getName() + "-localDir").getAbsoluteFile();

    private File nodeHealthscriptFile = new File(testRootDir, Shell.appendScriptExtension("failingscript"));

    @Before
    public void setup() {
        testRootDir.mkdirs();
    }

    @After
    public void tearDown() throws Exception {
        if (testRootDir.exists()) {
            FileContext.getLocalFSFileContext().delete(new Path(testRootDir.getAbsolutePath()), true);
        }
    }

    private void writeNodeHealthScriptFile(String scriptStr, boolean setExecutable) throws IOException {
        PrintWriter pw = null;
        try {
            FileUtil.setWritable(nodeHealthscriptFile, true);
            FileUtil.setReadable(nodeHealthscriptFile, true);
            pw = new PrintWriter(new FileOutputStream(nodeHealthscriptFile));
            pw.println(scriptStr);
            pw.flush();
        } finally {
            pw.close();
        }
        FileUtil.setExecutable(nodeHealthscriptFile, setExecutable);
    }

    private NodeHealthScriptRunner createNodeHealthScript() {
        String scriptName = "custom";
        YarnConfiguration conf = new YarnConfiguration();
        conf.set(YarnConfiguration.NM_HEALTH_CHECK_SCRIPTS, scriptName);
        String timeoutConfig = String.format(YarnConfiguration.NM_HEALTH_CHECK_SCRIPT_TIMEOUT_MS_TEMPLATE, scriptName);
        conf.setLong(timeoutConfig, 1000L);
        String intervalConfig = String.format(YarnConfiguration.NM_HEALTH_CHECK_SCRIPT_INTERVAL_MS_TEMPLATE, scriptName);
        conf.setLong(intervalConfig, 500L);
        String pathConfig = String.format(YarnConfiguration.NM_HEALTH_CHECK_SCRIPT_PATH_TEMPLATE, scriptName);
        conf.set(pathConfig, nodeHealthscriptFile.getAbsolutePath());
        return NodeHealthScriptRunner.newInstance("custom", conf);
    }

    @Test
    public void testNodeHealthScriptShouldRun_1() throws IOException {
        assertFalse("Node health script should start", NodeHealthScriptRunner.shouldRun("script", nodeHealthscriptFile.getAbsolutePath()));
    }

    @Test
    public void testNodeHealthScriptShouldRun_2() throws IOException {
        assertFalse("Node health script should start", NodeHealthScriptRunner.shouldRun("script", nodeHealthscriptFile.getAbsolutePath()));
    }

    @Test
    public void testNodeHealthScriptShouldRun_3() throws IOException {
        assertTrue("Node health script should start", NodeHealthScriptRunner.shouldRun("script", nodeHealthscriptFile.getAbsolutePath()));
    }
}
