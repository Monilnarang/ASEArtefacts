package org.apache.dubbo.rpc.support;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrieTreeTest_Purified {

    private TrieTree trie;

    @BeforeEach
    void setUp() {
        Set<String> words = new HashSet<>();
        words.add("apple");
        words.add("App-le");
        words.add("apply");
        words.add("app_le.juice");
        words.add("app-LE_juice");
        trie = new TrieTree(words);
    }

    @Test
    void testSearchValidWords_1() {
        assertTrue(trie.search("apple"));
    }

    @Test
    void testSearchValidWords_2() {
        assertTrue(trie.search("App-LE"));
    }

    @Test
    void testSearchValidWords_3() {
        assertTrue(trie.search("apply"));
    }

    @Test
    void testSearchValidWords_4() {
        assertTrue(trie.search("app_le.juice"));
    }

    @Test
    void testSearchValidWords_5() {
        assertTrue(trie.search("app-LE_juice"));
    }

    @Test
    void testSearchInvalidWords_1() {
        assertFalse(trie.search("app"));
    }

    @Test
    void testSearchInvalidWords_2() {
        assertFalse(trie.search("app%le"));
    }
}
