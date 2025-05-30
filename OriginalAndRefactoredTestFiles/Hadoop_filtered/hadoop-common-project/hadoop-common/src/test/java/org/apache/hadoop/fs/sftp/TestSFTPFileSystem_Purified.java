package org.apache.hadoop.fs.sftp;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.test.GenericTestUtils;
import static org.apache.hadoop.test.PlatformAssumptions.assumeNotWindows;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.UserAuth;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.UserAuthPasswordFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.junit.After;
import org.junit.AfterClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class TestSFTPFileSystem_Purified {

    private static final String TEST_SFTP_DIR = "testsftp";

    private static final String TEST_ROOT_DIR = GenericTestUtils.getTestDir().getAbsolutePath();

    @Rule
    public TestName name = new TestName();

    private static final String connection = "sftp://user:password@localhost";

    private static Path localDir = null;

    private static FileSystem localFs = null;

    private FileSystem sftpFs = null;

    private static SshServer sshd = null;

    private static Configuration conf = null;

    private static int port;

    private static void startSshdServer() throws IOException {
        sshd = SshServer.setUpDefaultServer();
        sshd.setPort(0);
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        List<NamedFactory<UserAuth>> userAuthFactories = new ArrayList<NamedFactory<UserAuth>>();
        userAuthFactories.add(new UserAuthPasswordFactory());
        sshd.setUserAuthFactories(userAuthFactories);
        sshd.setPasswordAuthenticator(new PasswordAuthenticator() {

            @Override
            public boolean authenticate(String username, String password, ServerSession session) {
                if (username.equals("user") && password.equals("password")) {
                    return true;
                }
                return false;
            }
        });
        sshd.setSubsystemFactories(Arrays.<NamedFactory<Command>>asList(new SftpSubsystemFactory()));
        sshd.start();
        port = sshd.getPort();
    }

    @Before
    public void init() throws Exception {
        sftpFs = FileSystem.get(URI.create(connection), conf);
    }

    @After
    public void cleanUp() throws Exception {
        if (sftpFs != null) {
            try {
                sftpFs.close();
            } catch (IOException e) {
            }
        }
    }

    @BeforeClass
    public static void setUp() throws Exception {
        assumeNotWindows();
        startSshdServer();
        conf = new Configuration();
        conf.setClass("fs.sftp.impl", SFTPFileSystem.class, FileSystem.class);
        conf.setInt("fs.sftp.host.port", port);
        conf.setBoolean("fs.sftp.impl.disable.cache", true);
        localFs = FileSystem.getLocal(conf);
        localDir = localFs.makeQualified(new Path(TEST_ROOT_DIR, TEST_SFTP_DIR));
        if (localFs.exists(localDir)) {
            localFs.delete(localDir, true);
        }
        localFs.mkdirs(localDir);
    }

    @AfterClass
    public static void tearDown() {
        if (localFs != null) {
            try {
                localFs.delete(localDir, true);
                localFs.close();
            } catch (IOException e) {
            }
        }
        if (sshd != null) {
            try {
                sshd.stop(true);
            } catch (IOException e) {
            }
        }
    }

    private static final Path touch(FileSystem fs, String filename) throws IOException {
        return touch(fs, filename, null);
    }

    private static final Path touch(FileSystem fs, String filename, byte[] data) throws IOException {
        Path lPath = new Path(localDir.toUri().getPath(), filename);
        FSDataOutputStream out = null;
        try {
            out = fs.create(lPath);
            if (data != null) {
                out.write(data);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return lPath;
    }

    @Test
    public void testFileExists_1_testMerged_1() throws Exception {
        Path file = touch(localFs, name.getMethodName().toLowerCase());
        assertTrue(sftpFs.exists(file));
        assertTrue(localFs.exists(file));
        assertTrue(sftpFs.delete(file, false));
        assertFalse(sftpFs.exists(file));
        assertFalse(localFs.exists(file));
    }

    @Test
    public void testFileExists_6() throws Exception {
        assertThat(((SFTPFileSystem) sftpFs).getConnectionPool().getLiveConnCount()).isEqualTo(1);
    }

    @Test
    public void testDeleteNonExistFile_1() throws Exception {
        Path file = new Path(localDir, name.getMethodName().toLowerCase());
        assertFalse(sftpFs.delete(file, false));
    }

    @Test
    public void testDeleteNonExistFile_2() throws Exception {
        assertThat(((SFTPFileSystem) sftpFs).getConnectionPool().getLiveConnCount()).isEqualTo(1);
    }
}
