package org.apache.commons.configuration2.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDefaultExpressionEngine_Purified {

    private static final String[] TABLES = { "users", "documents" };

    private static final String[] TAB_TYPES = { "system", "application" };

    private static final String[][] FIELDS = { { "uid", "uname", "firstName", "lastName", "email" }, { "docid", "name", "creationDate", "authorID", "version" } };

    private static ImmutableNode root;

    private static NodeHandler<ImmutableNode> handler;

    private static ImmutableNode createFieldNode(final String name) {
        final ImmutableNode.Builder nodeFieldBuilder = new ImmutableNode.Builder(1);
        nodeFieldBuilder.addChild(createNode("name", name));
        return nodeFieldBuilder.name("field").create();
    }

    private static ImmutableNode createNode(final String name, final Object value) {
        return new ImmutableNode.Builder().name(name).value(value).create();
    }

    @BeforeAll
    public static void setUpBeforeClass() {
        root = setUpNodes();
        handler = new InMemoryNodeModel(root).getNodeHandler();
    }

    private static ImmutableNode setUpNodes() {
        final ImmutableNode.Builder nodeTablesBuilder = new ImmutableNode.Builder(TABLES.length);
        nodeTablesBuilder.name("tables");
        for (int i = 0; i < TABLES.length; i++) {
            final ImmutableNode.Builder nodeTableBuilder = new ImmutableNode.Builder(2);
            nodeTableBuilder.name("table");
            nodeTableBuilder.addChild(new ImmutableNode.Builder().name("name").value(TABLES[i]).create());
            nodeTableBuilder.addAttribute("type", TAB_TYPES[i]);
            final ImmutableNode.Builder nodeFieldsBuilder = new ImmutableNode.Builder(FIELDS[i].length);
            for (int j = 0; j < FIELDS[i].length; j++) {
                nodeFieldsBuilder.addChild(createFieldNode(FIELDS[i][j]));
            }
            nodeTableBuilder.addChild(nodeFieldsBuilder.name("fields").create());
            nodeTablesBuilder.addChild(nodeTableBuilder.create());
        }
        final ImmutableNode.Builder rootBuilder = new ImmutableNode.Builder();
        rootBuilder.addChild(nodeTablesBuilder.create());
        final ImmutableNode.Builder nodeConnBuilder = new ImmutableNode.Builder();
        nodeConnBuilder.name("connection.settings");
        nodeConnBuilder.addChild(createNode("usr.name", "scott"));
        nodeConnBuilder.addChild(createNode("usr.pwd", "tiger"));
        rootBuilder.addAttribute("test", "true");
        rootBuilder.addChild(nodeConnBuilder.create());
        return rootBuilder.create();
    }

    private DefaultExpressionEngine engine;

    private void checkAttributeValue(final String key, final String attr, final Object expValue) {
        final List<QueryResult<ImmutableNode>> results = checkKey(key, attr, 1);
        final QueryResult<ImmutableNode> result = results.get(0);
        assertTrue(result.isAttributeResult());
        assertEquals(expValue, result.getAttributeValue(handler), "Wrong attribute value for key " + key);
    }

    private List<QueryResult<ImmutableNode>> checkKey(final String key, final String name, final int count) {
        final List<QueryResult<ImmutableNode>> nodes = query(key, count);
        for (final QueryResult<ImmutableNode> result : nodes) {
            if (result.isAttributeResult()) {
                assertEquals(name, result.getAttributeName(), "Wrong attribute name for key " + key);
            } else {
                assertEquals(name, result.getNode().getNodeName(), "Wrong result node for key " + key);
            }
        }
        return nodes;
    }

    private void checkKeyValue(final String key, final String name, final String value) {
        final List<QueryResult<ImmutableNode>> results = checkKey(key, name, 1);
        final QueryResult<ImmutableNode> result = results.get(0);
        assertFalse(result.isAttributeResult());
        assertEquals(value, result.getNode().getValue(), "Wrong value for key " + key);
    }

    private void checkNodePath(final NodeAddData<ImmutableNode> data, final String... expected) {
        assertEquals(Arrays.asList(expected), data.getPathNodes());
    }

    private void checkQueryRootNode(final String key) {
        final List<QueryResult<ImmutableNode>> results = checkKey(key, null, 1);
        final QueryResult<ImmutableNode> result = results.get(0);
        assertFalse(result.isAttributeResult());
        assertSame(root, result.getNode());
    }

    private ImmutableNode fetchNode(final String key) {
        final QueryResult<ImmutableNode> result = query(key, 1).get(0);
        assertFalse(result.isAttributeResult());
        return result.getNode();
    }

    private List<QueryResult<ImmutableNode>> query(final String key, final int expCount) {
        final List<QueryResult<ImmutableNode>> nodes = engine.query(root, key, handler);
        assertEquals(expCount, nodes.size());
        return nodes;
    }

    @BeforeEach
    public void setUp() throws Exception {
        engine = DefaultExpressionEngine.INSTANCE;
    }

    private void setUpAlternativeMatcher() {
        final NodeMatcher<String> matcher = new NodeMatcher<String>() {

            @Override
            public <T> boolean matches(final T node, final NodeHandler<T> handler, final String criterion) {
                return handler.nodeName(node).equals(StringUtils.remove(criterion, '_'));
            }
        };
        engine = new DefaultExpressionEngine(engine.getSymbols(), matcher);
    }

    private void setUpAlternativeSyntax() {
        final DefaultExpressionEngineSymbols symbols = new DefaultExpressionEngineSymbols.Builder().setAttributeEnd(null).setAttributeStart("@").setPropertyDelimiter("/").setEscapedDelimiter(null).setIndexStart("[").setIndexEnd("]").create();
        engine = new DefaultExpressionEngine(symbols);
    }

    @Test
    public void testCanonicalKeyWithDuplicates_1() {
        final ImmutableNode tab1 = fetchNode("tables.table(0)");
        assertEquals("tables.table(0)", engine.canonicalKey(tab1, "tables", handler));
    }

    @Test
    public void testCanonicalKeyWithDuplicates_2() {
        final ImmutableNode tab2 = fetchNode("tables.table(1)");
        assertEquals("tables.table(1)", engine.canonicalKey(tab2, "tables", handler));
    }

    @Test
    public void testNodeKeyWithRoot_1() {
        assertEquals("", engine.nodeKey(root, null, handler));
    }

    @Test
    public void testNodeKeyWithRoot_2() {
        assertEquals("test", engine.nodeKey(root, "test", handler));
    }
}
