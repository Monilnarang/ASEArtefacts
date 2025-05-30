package org.apache.dubbo.common.utils;

import org.apache.dubbo.common.vo.UserVo;
import org.apache.dubbo.rpc.model.HelloReply;
import org.apache.dubbo.rpc.model.HelloRequest;
import org.apache.dubbo.rpc.model.Person;
import org.apache.dubbo.rpc.model.SerializablePerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProtobufUtilsTest_Purified {

    @Test
    void testIsProtobufClass_1() {
        Assertions.assertTrue(ProtobufUtils.isProtobufClass(HelloRequest.class));
    }

    @Test
    void testIsProtobufClass_2() {
        Assertions.assertTrue(ProtobufUtils.isProtobufClass(HelloReply.class));
    }

    @Test
    void testIsProtobufClass_3() {
        Assertions.assertFalse(ProtobufUtils.isProtobufClass(Person.class));
    }

    @Test
    void testIsProtobufClass_4() {
        Assertions.assertFalse(ProtobufUtils.isProtobufClass(SerializablePerson.class));
    }

    @Test
    void testIsProtobufClass_5() {
        Assertions.assertFalse(ProtobufUtils.isProtobufClass(UserVo.class));
    }
}
