package org.apache.amoro.utils;

import org.apache.amoro.io.AuthenticatedFileIO;
import org.apache.amoro.io.PathInfo;
import org.apache.amoro.io.SupportsFileSystemOperations;
import org.apache.amoro.shade.guava32.com.google.common.collect.Lists;
import org.apache.amoro.shade.guava32.com.google.common.collect.Sets;
import org.apache.iceberg.Files;
import org.apache.iceberg.io.FileInfo;
import org.apache.iceberg.io.InputFile;
import org.apache.iceberg.io.OutputFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestFileUtil_Parameterized {

    private static final TemporaryFolder temp = new TemporaryFolder();

    static class LocalAuthenticatedFileIO implements AuthenticatedFileIO, SupportsFileSystemOperations {

        @Override
        public <T> T doAs(Callable<T> callable) {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public InputFile newInputFile(String path) {
            return Files.localInput(path);
        }

        @Override
        public OutputFile newOutputFile(String path) {
            return Files.localOutput(path);
        }

        @Override
        public void deleteFile(String path) {
            if (!(new File(path)).delete()) {
                throw new UncheckedIOException(new IOException("Failed to delete file: " + path));
            }
        }

        @Override
        public void makeDirectories(String path) {
            try {
                temp.newFolder(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean isDirectory(String location) {
            return new File(location).isDirectory();
        }

        @Override
        public boolean isEmptyDirectory(String location) {
            return Objects.requireNonNull(new File(location).listFiles()).length == 0;
        }

        @Override
        public void rename(String oldPath, String newPath) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterable<PathInfo> listDirectory(String location) {
            File dir = new File(location);
            File[] files = dir.listFiles();
            Iterator<PathInfo> it = Stream.of(files).map(file -> new PathInfo(file.getPath(), file.getTotalSpace(), System.currentTimeMillis(), file.isDirectory())).iterator();
            return () -> it;
        }

        @Override
        public Iterable<FileInfo> listPrefix(String prefix) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deletePrefix(String prefix) {
            File folder = new File(prefix);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deletePrefix(file.getAbsolutePath());
                    } else {
                        file.delete();
                    }
                }
            }
            folder.delete();
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetUriPath_1to6")
    public void testGetUriPath_1to6(String param1, String param2) {
        Assert.assertEquals(param1, TableFileUtil.getUriPath(param2));
    }

    static public Stream<Arguments> Provider_testGetUriPath_1to6() {
        return Stream.of(arguments("/a/b/c", "hdfs://xxxxx/a/b/c"), arguments("/a/b/c", "hdfs://localhost:8888/a/b/c"), arguments("/a/b/c", "file://xxxxx/a/b/c"), arguments("/a/b/c", "/a/b/c"), arguments("/a/b/c", "hdfs:/a/b/c"), arguments("a/b/c", "a/b/c"));
    }
}
