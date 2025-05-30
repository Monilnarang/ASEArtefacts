package org.graylog.plugins.views.search.rest.scriptingapi.response;

import org.graylog2.indexer.fieldtypes.FieldTypeMapper;
import org.junit.jupiter.api.Test;
import static org.graylog.plugins.views.search.rest.scriptingapi.response.ResponseEntryDataType.DATE;
import static org.graylog.plugins.views.search.rest.scriptingapi.response.ResponseEntryDataType.GEO;
import static org.graylog.plugins.views.search.rest.scriptingapi.response.ResponseEntryDataType.NUMERIC;
import static org.graylog.plugins.views.search.rest.scriptingapi.response.ResponseEntryDataType.STRING;
import static org.graylog.plugins.views.search.rest.scriptingapi.response.ResponseEntryDataType.UNKNOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseEntryDataTypeTest_Purified {

    @Test
    void properTypeOnProperEsType_1() {
        assertEquals(NUMERIC, ResponseEntryDataType.fromFieldType(FieldTypeMapper.FLOAT_TYPE));
    }

    @Test
    void properTypeOnProperEsType_2() {
        assertEquals(NUMERIC, ResponseEntryDataType.fromFieldType(FieldTypeMapper.LONG_TYPE));
    }

    @Test
    void properTypeOnProperEsType_3() {
        assertEquals(STRING, ResponseEntryDataType.fromFieldType(FieldTypeMapper.STRING_FTS_TYPE));
    }

    @Test
    void properTypeOnProperEsType_4() {
        assertEquals(STRING, ResponseEntryDataType.fromFieldType(FieldTypeMapper.STRING_TYPE));
    }

    @Test
    void properTypeOnProperEsType_5() {
        assertEquals(DATE, ResponseEntryDataType.fromFieldType(FieldTypeMapper.DATE_TYPE));
    }
}
