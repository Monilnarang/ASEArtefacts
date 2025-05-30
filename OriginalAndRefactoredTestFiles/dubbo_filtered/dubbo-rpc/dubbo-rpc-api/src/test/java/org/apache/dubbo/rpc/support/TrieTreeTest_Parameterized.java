package org.apache.dubbo.rpc.support;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TrieTreeTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testSearchValidWords_1to5")
    void testSearchValidWords_1to5(String param1) {
        assertTrue(trie.search(param1));
    }

    static public Stream<Arguments> Provider_testSearchValidWords_1to5() {
        return Stream.of(arguments("apple"), arguments("App-LE"), arguments("apply"), arguments("app_le.juice"), arguments("app-LE_juice"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSearchInvalidWords_1to2")
    void testSearchInvalidWords_1to2(String param1) {
        assertFalse(trie.search(param1));
    }

    static public Stream<Arguments> Provider_testSearchInvalidWords_1to2() {
        return Stream.of(arguments("app"), arguments("app%le"));
    }
}
