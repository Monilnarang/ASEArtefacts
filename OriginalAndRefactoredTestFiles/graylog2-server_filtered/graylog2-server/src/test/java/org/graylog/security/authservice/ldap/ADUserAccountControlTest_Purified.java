package org.graylog.security.authservice.ldap;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ADUserAccountControlTest_Purified {

    @Test
    void isSetIn_1() {
        assertThat(ADUserAccountControl.Flags.ACCOUNTDISABLE.isSetIn(1)).isFalse();
    }

    @Test
    void isSetIn_2() {
        assertThat(ADUserAccountControl.Flags.ACCOUNTDISABLE.isSetIn(2)).isTrue();
    }

    @Test
    void isSetIn_3() {
        assertThat(ADUserAccountControl.Flags.ACCOUNTDISABLE.isSetIn(66048)).isFalse();
    }

    @Test
    void isSetIn_4() {
        assertThat(ADUserAccountControl.Flags.ACCOUNTDISABLE.isSetIn(66050)).isTrue();
    }

    @Test
    void isSetIn_5() {
        assertThat(ADUserAccountControl.Flags.NORMAL_ACCOUNT.isSetIn(256)).isFalse();
    }

    @Test
    void isSetIn_6() {
        assertThat(ADUserAccountControl.Flags.NORMAL_ACCOUNT.isSetIn(512)).isTrue();
    }

    @Test
    void isSetIn_7() {
        assertThat(ADUserAccountControl.Flags.NORMAL_ACCOUNT.isSetIn(66048)).isTrue();
    }

    @Test
    void isSetIn_8() {
        assertThat(ADUserAccountControl.Flags.NORMAL_ACCOUNT.isSetIn(66050)).isTrue();
    }

    @Test
    void isSetIn_9() {
        assertThat(ADUserAccountControl.Flags.NORMAL_ACCOUNT.isSetIn(532480)).isFalse();
    }

    @Test
    void isAccountDisabled_1() {
        assertThat(ADUserAccountControl.create(1).accountIsDisabled()).isFalse();
    }

    @Test
    void isAccountDisabled_2() {
        assertThat(ADUserAccountControl.create(2).accountIsDisabled()).isTrue();
    }

    @Test
    void isAccountDisabled_3() {
        assertThat(ADUserAccountControl.create(66048).accountIsDisabled()).isFalse();
    }

    @Test
    void isAccountDisabled_4() {
        assertThat(ADUserAccountControl.create(66050).accountIsDisabled()).isTrue();
    }

    @Test
    void isUserAccount_1() {
        assertThat(ADUserAccountControl.create(256).isUserAccount()).isFalse();
    }

    @Test
    void isUserAccount_2() {
        assertThat(ADUserAccountControl.create(512).isUserAccount()).isTrue();
    }

    @Test
    void isUserAccount_3() {
        assertThat(ADUserAccountControl.create(66048).isUserAccount()).isTrue();
    }

    @Test
    void isUserAccount_4() {
        assertThat(ADUserAccountControl.create(66050).isUserAccount()).isTrue();
    }

    @Test
    void isUserAccount_5() {
        assertThat(ADUserAccountControl.create(532480).isUserAccount()).isFalse();
    }
}
