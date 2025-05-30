package org.graylog.security.authservice.ldap;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ADUserAccountControlTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_isSetIn_1_3")
    void isSetIn_1_3(int param1) {
        assertThat(ADUserAccountControl.Flags.ACCOUNTDISABLE.isSetIn(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_isSetIn_1_3() {
        return Stream.of(arguments(1), arguments(66048));
    }

    @ParameterizedTest
    @MethodSource("Provider_isSetIn_2_4")
    void isSetIn_2_4(int param1) {
        assertThat(ADUserAccountControl.Flags.ACCOUNTDISABLE.isSetIn(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_isSetIn_2_4() {
        return Stream.of(arguments(2), arguments(66050));
    }

    @ParameterizedTest
    @MethodSource("Provider_isSetIn_5_9")
    void isSetIn_5_9(int param1) {
        assertThat(ADUserAccountControl.Flags.NORMAL_ACCOUNT.isSetIn(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_isSetIn_5_9() {
        return Stream.of(arguments(256), arguments(532480));
    }

    @ParameterizedTest
    @MethodSource("Provider_isSetIn_6to8")
    void isSetIn_6to8(int param1) {
        assertThat(ADUserAccountControl.Flags.NORMAL_ACCOUNT.isSetIn(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_isSetIn_6to8() {
        return Stream.of(arguments(512), arguments(66048), arguments(66050));
    }

    @ParameterizedTest
    @MethodSource("Provider_isAccountDisabled_1_3")
    void isAccountDisabled_1_3(int param1) {
        assertThat(ADUserAccountControl.create(param1).accountIsDisabled()).isFalse();
    }

    static public Stream<Arguments> Provider_isAccountDisabled_1_3() {
        return Stream.of(arguments(1), arguments(66048));
    }

    @ParameterizedTest
    @MethodSource("Provider_isAccountDisabled_2_4")
    void isAccountDisabled_2_4(int param1) {
        assertThat(ADUserAccountControl.create(param1).accountIsDisabled()).isTrue();
    }

    static public Stream<Arguments> Provider_isAccountDisabled_2_4() {
        return Stream.of(arguments(2), arguments(66050));
    }

    @ParameterizedTest
    @MethodSource("Provider_isUserAccount_1_5")
    void isUserAccount_1_5(int param1) {
        assertThat(ADUserAccountControl.create(param1).isUserAccount()).isFalse();
    }

    static public Stream<Arguments> Provider_isUserAccount_1_5() {
        return Stream.of(arguments(256), arguments(532480));
    }

    @ParameterizedTest
    @MethodSource("Provider_isUserAccount_2to4")
    void isUserAccount_2to4(int param1) {
        assertThat(ADUserAccountControl.create(param1).isUserAccount()).isTrue();
    }

    static public Stream<Arguments> Provider_isUserAccount_2to4() {
        return Stream.of(arguments(512), arguments(66048), arguments(66050));
    }
}
