package org.apache.druid.query.expression;

import com.google.common.collect.Lists;
import org.apache.commons.compress.utils.Sets;
import org.apache.druid.math.expr.Expr;
import org.apache.druid.math.expr.ExprEval;
import org.apache.druid.query.lookup.LookupExtractorFactoryContainer;
import org.apache.druid.query.lookup.LookupExtractorFactoryContainerProvider;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LookupExprMacroTest_Purified extends MacroTestBase {

    public LookupExprMacroTest() {
        super(new LookupExprMacro(new LookupExtractorFactoryContainerProvider() {

            @Override
            public Set<String> getAllLookupNames() {
                return Sets.newHashSet("test_lookup");
            }

            @Override
            public Optional<LookupExtractorFactoryContainer> get(String lookupName) {
                return Optional.empty();
            }

            @Override
            public String getCanonicalLookupName(String lookupName) {
                return lookupName;
            }
        }));
    }

    private List<Expr> getArgs(List<Object> args) {
        return args.stream().map(a -> {
            if (a != null && a instanceof String) {
                return ExprEval.of(a.toString()).toExpr();
            }
            return ExprEval.bestEffortOf(null).toExpr();
        }).collect(Collectors.toList());
    }

    @Test
    public void testValidCalls_1() {
        Assert.assertNotNull(apply(getArgs(Lists.newArrayList("1", "test_lookup"))));
    }

    @Test
    public void testValidCalls_2() {
        Assert.assertNotNull(apply(getArgs(Lists.newArrayList("null", "test_lookup"))));
    }

    @Test
    public void testValidCalls_3() {
        Assert.assertNotNull(apply(getArgs(Lists.newArrayList("1", "test_lookup", null))));
    }

    @Test
    public void testValidCalls_4() {
        Assert.assertNotNull(apply(getArgs(Lists.newArrayList("1", "test_lookup", "N/A"))));
    }
}
