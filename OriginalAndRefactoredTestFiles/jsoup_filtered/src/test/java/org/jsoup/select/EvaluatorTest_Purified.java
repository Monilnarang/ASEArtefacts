package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluatorTest_Purified {

    @Test
    public void testIsNthChildToStringVariants_1() {
        Evaluator.IsNthChild evaluator1 = new Evaluator.IsNthChild(0, 3);
        assertEquals(":nth-child(3)", evaluator1.toString());
    }

    @Test
    public void testIsNthChildToStringVariants_2() {
        Evaluator.IsNthChild evaluator2 = new Evaluator.IsNthChild(2, 0);
        assertEquals(":nth-child(2n)", evaluator2.toString());
    }

    @Test
    public void testIsNthChildToStringVariants_3() {
        Evaluator.IsNthChild evaluator3 = new Evaluator.IsNthChild(2, 3);
        assertEquals(":nth-child(2n+3)", evaluator3.toString());
    }
}
