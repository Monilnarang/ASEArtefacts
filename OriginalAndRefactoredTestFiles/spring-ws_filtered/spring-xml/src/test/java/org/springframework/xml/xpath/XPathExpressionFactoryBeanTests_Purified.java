package org.springframework.xml.xpath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class XPathExpressionFactoryBeanTests_Purified {

    private XPathExpressionFactoryBean factoryBean;

    @BeforeEach
    void setUp() {
        this.factoryBean = new XPathExpressionFactoryBean();
    }

    @Test
    void testFactoryBean_1_testMerged_1() throws Exception {
        Object result = this.factoryBean.getObject();
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(XPathExpression.class);
    }

    @Test
    void testFactoryBean_3() throws Exception {
        assertThat(this.factoryBean.isSingleton()).isTrue();
    }

    @Test
    void testFactoryBean_4() throws Exception {
        assertThat(this.factoryBean.getObject()).isInstanceOf(XPathExpression.class);
    }
}
