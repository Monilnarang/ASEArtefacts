package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class SolrCliTest_Purified {

    public static final String ZK_HOST = "localhost:9983";

    public static final String DEFAULT_CONFIG_SET = "_default";

    public static final Option OPTION_ZKHOST_DEPRECATED = Option.builder("zkHost").longOpt("zkHost").deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("9.6").setDescription("Use --zk-host instead").get()).argName("HOST").hasArg().required(false).desc("Zookeeper connection string; unnecessary if ZK_HOST is defined in solr.in.sh; otherwise, defaults to " + ZK_HOST + '.').build();

    public static final Option OPTION_ZKHOST = Option.builder("z").longOpt("zk-host").argName("HOST").hasArg().required(false).desc("Zookeeper connection string; unnecessary if ZK_HOST is defined in solr.in.sh; otherwise, defaults to " + ZK_HOST + '.').build();

    public static final Option OPTION_SOLRURL_DEPRECATED = Option.builder("solrUrl").longOpt("solrUrl").deprecated(DeprecatedAttributes.builder().setForRemoval(true).setSince("9.6").setDescription("Use --solr-url instead").get()).argName("HOST").hasArg().required(false).desc("Base Solr URL, which can be used to determine the zk-host if that's not known; defaults to: " + getDefaultSolrUrl() + '.').build();

    public static final Option OPTION_SOLRURL = Option.builder("url").longOpt("solr-url").argName("HOST").hasArg().required(false).desc("Base Solr URL, which can be used to determine the zk-host if that's not known; defaults to: " + getDefaultSolrUrl() + '.').build();

    public static final Option OPTION_VERBOSE = Option.builder("v").longOpt("verbose").argName("verbose").required(false).desc("Enable more verbose command output.").build();

    public static final Option OPTION_HELP = Option.builder("h").longOpt("help").required(false).desc("Print this message.").build();

    public static final Option OPTION_RECURSE = Option.builder("r").longOpt("recurse").argName("recurse").hasArg().required(false).desc("Recurse (true|false), default is false.").build();

    public static final Option OPTION_CREDENTIALS = Option.builder("u").longOpt("credentials").argName("credentials").hasArg().required(false).desc("Credentials in the format username:password. Example: --credentials solr:SolrRocks").build();

    private static String getDefaultSolrUrl() {
        final String scheme = "http";
        final String host = "localhost";
        final String port = "8983";
        return String.format(Locale.ROOT, "%s://%s:%s", scheme.toLowerCase(Locale.ROOT), host, port);
    }

    @Test
    public void testOptions_1() {
        assertNotNull(DEFAULT_CONFIG_SET);
    }

    @Test
    public void testOptions_2() {
        assertNotNull(OPTION_CREDENTIALS);
    }

    @Test
    public void testOptions_3() {
        assertNotNull(OPTION_HELP);
    }

    @Test
    public void testOptions_4() {
        assertNotNull(OPTION_RECURSE);
    }

    @Test
    public void testOptions_5() {
        assertNotNull(OPTION_SOLRURL);
    }

    @Test
    public void testOptions_6() {
        assertNotNull(OPTION_SOLRURL_DEPRECATED);
    }

    @Test
    public void testOptions_7() {
        assertNotNull(OPTION_VERBOSE);
    }

    @Test
    public void testOptions_8() {
        assertNotNull(OPTION_ZKHOST);
    }

    @Test
    public void testOptions_9() {
        assertNotNull(OPTION_ZKHOST_DEPRECATED);
    }

    @Test
    public void testOptions_10() {
        assertNotNull(ZK_HOST);
    }

    @Test
    public void testOptions_11() {
        assertNotNull(getDefaultSolrUrl());
    }
}
