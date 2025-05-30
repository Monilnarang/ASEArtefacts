package com.iluwatar.parameter.object;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SearchServiceTest_Purified {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceTest.class);

    private ParameterObject parameterObject;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        parameterObject = ParameterObject.newBuilder().withType("sneakers").build();
        searchService = new SearchService();
    }

    @Test
    void testDefaultParametersMatch_1() {
        assertEquals(searchService.search(parameterObject), searchService.search("sneakers", SortOrder.ASC), "Default Parameter values do not not match.");
    }

    @Test
    void testDefaultParametersMatch_2() {
        assertEquals(searchService.search(parameterObject), searchService.search("sneakers", "price"), "Default Parameter values do not not match.");
    }
}
