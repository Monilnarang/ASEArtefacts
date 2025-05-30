package org.apache.commons.configuration2.tree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.apache.commons.configuration2.BaseHierarchicalConfiguration;
import org.apache.commons.configuration2.ConfigurationAssert;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractCombinerTest_Purified {

    private static final File CONF1 = ConfigurationAssert.getTestFile("testcombine1.xml");

    private static final File CONF2 = ConfigurationAssert.getTestFile("testcombine2.xml");

    protected NodeCombiner combiner;

    protected BaseHierarchicalConfiguration createCombinedConfiguration() throws ConfigurationException {
        final XMLConfiguration conf1 = new XMLConfiguration();
        new FileHandler(conf1).load(CONF1);
        final XMLConfiguration conf2 = new XMLConfiguration();
        new FileHandler(conf2).load(CONF2);
        final ImmutableNode cn = combiner.combine(conf1.getNodeModel().getNodeHandler().getRootNode(), conf2.getNodeModel().getNodeHandler().getRootNode());
        final BaseHierarchicalConfiguration result = new BaseHierarchicalConfiguration();
        result.getNodeModel().setRootNode(cn);
        return result;
    }

    protected abstract NodeCombiner createCombiner();

    @BeforeEach
    public void setUp() throws Exception {
        combiner = createCombiner();
    }

    @Test
    public void testInit_1() {
        assertTrue(combiner.getListNodes().isEmpty());
    }

    @Test
    public void testInit_2() {
        assertFalse(combiner.isListNode(NodeStructureHelper.createNode("test", null)));
    }
}
