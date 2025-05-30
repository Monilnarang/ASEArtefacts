package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.event.ConfigurationEvent;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.event.EventListenerTestImpl;
import org.apache.commons.configuration2.tree.DefaultConfigurationKey;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import org.apache.commons.configuration2.tree.ExpressionEngine;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.InMemoryNodeModel;
import org.apache.commons.configuration2.tree.NodeHandler;
import org.apache.commons.configuration2.tree.NodeModel;
import org.apache.commons.configuration2.tree.NodeStructureHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestAbstractHierarchicalConfiguration_Parameterized {

    private static final class AbstractHierarchicalConfigurationTestImpl extends AbstractHierarchicalConfiguration<ImmutableNode> {

        public AbstractHierarchicalConfigurationTestImpl(final InMemoryNodeModel model) {
            super(model);
        }

        @Override
        public List<HierarchicalConfiguration<ImmutableNode>> childConfigurationsAt(final String key) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public List<HierarchicalConfiguration<ImmutableNode>> childConfigurationsAt(final String key, final boolean supportUpdates) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        protected NodeModel<ImmutableNode> cloneNodeModel() {
            return new InMemoryNodeModel(getModel().getNodeHandler().getRootNode());
        }

        @Override
        public SubnodeConfiguration configurationAt(final String key) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public SubnodeConfiguration configurationAt(final String key, final boolean supportUpdates) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public List<HierarchicalConfiguration<ImmutableNode>> configurationsAt(final String key) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public List<HierarchicalConfiguration<ImmutableNode>> configurationsAt(final String key, final boolean supportUpdates) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public List<ImmutableHierarchicalConfiguration> immutableChildConfigurationsAt(final String key) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public ImmutableHierarchicalConfiguration immutableConfigurationAt(final String key) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public ImmutableHierarchicalConfiguration immutableConfigurationAt(final String key, final boolean supportUpdates) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }

        @Override
        public List<ImmutableHierarchicalConfiguration> immutableConfigurationsAt(final String key) {
            throw new UnsupportedOperationException("Unexpected method call!");
        }
    }

    private static void checkContent(final Configuration c) {
        for (int i = 0; i < NodeStructureHelper.tablesLength(); i++) {
            assertEquals(NodeStructureHelper.table(i), c.getString("tables.table(" + i + ").name"));
            for (int j = 0; j < NodeStructureHelper.fieldsLength(i); j++) {
                assertEquals(NodeStructureHelper.field(i, j), c.getString("tables.table(" + i + ").fields.field(" + j + ").name"));
            }
        }
    }

    private static void checkGetProperty(final AbstractHierarchicalConfiguration<?> testConfig) {
        assertNull(testConfig.getProperty("tables.table.resultset"));
        assertNull(testConfig.getProperty("tables.table.fields.field"));
        Object prop = testConfig.getProperty("tables.table(0).fields.field.name");
        Collection<?> collection = assertInstanceOf(Collection.class, prop);
        assertEquals(NodeStructureHelper.fieldsLength(0), collection.size());
        prop = testConfig.getProperty("tables.table.fields.field.name");
        collection = assertInstanceOf(Collection.class, prop);
        assertEquals(totalFieldCount(), collection.size());
        prop = testConfig.getProperty("tables.table.fields.field(3).name");
        collection = assertInstanceOf(Collection.class, prop);
        assertEquals(2, collection.size());
        prop = testConfig.getProperty("tables.table(1).fields.field(2).name");
        assertNotNull(prop);
        assertEquals("creationDate", prop.toString());
    }

    private static DefaultConfigurationKey createConfigurationKey() {
        return new DefaultConfigurationKey(DefaultExpressionEngine.INSTANCE);
    }

    private static int totalFieldCount() {
        int fieldCount = 0;
        for (int i = 0; i < NodeStructureHelper.tablesLength(); i++) {
            fieldCount += NodeStructureHelper.fieldsLength(i);
        }
        return fieldCount;
    }

    private AbstractHierarchicalConfiguration<ImmutableNode> config;

    private void checkAlternativeSyntax() {
        assertNull(config.getProperty("tables/table/resultset"));
        assertNull(config.getProperty("tables/table/fields/field"));
        Object prop = config.getProperty("tables/table[0]/fields/field/name");
        Collection<?> collection = assertInstanceOf(Collection.class, prop);
        assertEquals(NodeStructureHelper.fieldsLength(0), collection.size());
        prop = config.getProperty("tables/table/fields/field/name");
        collection = assertInstanceOf(Collection.class, prop);
        assertEquals(totalFieldCount(), collection.size());
        prop = config.getProperty("tables/table/fields/field[3]/name");
        collection = assertInstanceOf(Collection.class, prop);
        assertEquals(2, collection.size());
        prop = config.getProperty("tables/table[1]/fields/field[2]/name");
        assertNotNull(prop);
        assertEquals("creationDate", prop.toString());
        final Set<String> keys = ConfigurationAssert.keysToSet(config);
        assertEquals(new HashSet<>(Arrays.asList("tables/table/name", "tables/table/fields/field/name")), keys);
    }

    private void checkKeys(final String prefix, final String[] expected) {
        final Set<String> expectedKeys = new HashSet<>();
        for (final String anExpected : expected) {
            expectedKeys.add(anExpected.startsWith(prefix) ? anExpected : prefix + "." + anExpected);
        }
        final Set<String> keys = new HashSet<>();
        final Iterator<String> itKeys = config.getKeys(prefix);
        while (itKeys.hasNext()) {
            final String key = itKeys.next();
            keys.add(key);
        }
        assertEquals(expectedKeys, keys);
    }

    private ExpressionEngine createAlternativeExpressionEngine() {
        return new DefaultExpressionEngine(new DefaultExpressionEngineSymbols.Builder(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS).setPropertyDelimiter("/").setIndexStart("[").setIndexEnd("]").create());
    }

    private ImmutableNode getRootNode() {
        return config.getModel().getNodeHandler().getRootNode();
    }

    @BeforeEach
    public void setUp() throws Exception {
        final ImmutableNode root = new ImmutableNode.Builder(1).addChild(NodeStructureHelper.ROOT_TABLES_TREE).create();
        config = new AbstractHierarchicalConfigurationTestImpl(new InMemoryNodeModel(root));
    }

    @Test
    public void testContainsKey_6_testMerged_6() {
        config.clearTree("tables.table(0).fields");
        assertFalse(config.containsKey("tables.table(0).fields.field.name"));
        assertTrue(config.containsKey("tables.table.fields.field.name"));
    }

    @Test
    public void testContainsValue_1() {
        assertFalse(config.containsValue(null));
    }

    @Test
    public void testContainsValue_2() {
        assertFalse(config.containsValue(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testContainsKey_1to2_4")
    public void testContainsKey_1to2_4(String param1) {
        assertTrue(config.containsKey(param1));
    }

    static public Stream<Arguments> Provider_testContainsKey_1to2_4() {
        return Stream.of(arguments("tables.table(0).name"), arguments("tables.table(1).name"), arguments("tables.table(0).fields.field.name"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testContainsKey_3_5")
    public void testContainsKey_3_5(String param1) {
        assertFalse(config.containsKey(param1));
    }

    static public Stream<Arguments> Provider_testContainsKey_3_5() {
        return Stream.of(arguments("tables.table(2).name"), arguments("tables.table(0).fields.field"));
    }
}
