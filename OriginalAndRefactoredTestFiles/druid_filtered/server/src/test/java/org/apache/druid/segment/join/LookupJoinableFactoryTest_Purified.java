package org.apache.druid.segment.join;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.query.LookupDataSource;
import org.apache.druid.query.TableDataSource;
import org.apache.druid.query.extraction.MapLookupExtractor;
import org.apache.druid.query.lookup.LookupExtractorFactoryContainer;
import org.apache.druid.query.lookup.LookupExtractorFactoryContainerProvider;
import org.apache.druid.query.lookup.MapLookupExtractorFactory;
import org.apache.druid.segment.join.lookup.LookupJoinable;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class LookupJoinableFactoryTest_Purified {

    private static final String PREFIX = "j.";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final LookupJoinableFactory factory;

    private final LookupDataSource lookupDataSource = new LookupDataSource("country_code_to_name");

    public LookupJoinableFactoryTest() {
        try {
            final MapLookupExtractor countryIsoCodeToNameLookup = JoinTestHelper.createCountryIsoCodeToNameLookup();
            this.factory = new LookupJoinableFactory(new LookupExtractorFactoryContainerProvider() {

                @Override
                public Set<String> getAllLookupNames() {
                    return ImmutableSet.of(lookupDataSource.getLookupName());
                }

                @Override
                public Optional<LookupExtractorFactoryContainer> get(String lookupName) {
                    if (lookupDataSource.getLookupName().equals(lookupName)) {
                        return Optional.of(new LookupExtractorFactoryContainer("v0", new MapLookupExtractorFactory(countryIsoCodeToNameLookup.getMap(), false)));
                    } else {
                        return Optional.empty();
                    }
                }

                @Override
                public String getCanonicalLookupName(String lookupName) {
                    return lookupName;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JoinConditionAnalysis makeCondition(final String condition) {
        return JoinConditionAnalysis.forExpression(condition, PREFIX, ExprMacroTable.nil());
    }

    @Test
    public void testIsDirectlyJoinable_1() {
        Assert.assertTrue(factory.isDirectlyJoinable(lookupDataSource));
    }

    @Test
    public void testIsDirectlyJoinable_2() {
        Assert.assertFalse(factory.isDirectlyJoinable(new TableDataSource("foo")));
    }
}
