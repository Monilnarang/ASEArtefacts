package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InetAddressValidatorTest_Purified {

    private InetAddressValidator validator;

    @BeforeEach
    protected void setUp() {
        validator = new InetAddressValidator();
    }

    @Test
    public void testBrokenInetAddresses_1() {
        assertFalse(validator.isValid("124.14.32.abc"), "IP with characters should be invalid");
    }

    @Test
    public void testBrokenInetAddresses_2() {
        assertFalse(validator.isValid("124.14.32.01"), "IP with leading zeroes should be invalid");
    }

    @Test
    public void testBrokenInetAddresses_3() {
        assertFalse(validator.isValid("23.64.12"), "IP with three groups should be invalid");
    }

    @Test
    public void testBrokenInetAddresses_4() {
        assertFalse(validator.isValid("26.34.23.77.234"), "IP with five groups should be invalid");
    }

    @Test
    public void testBrokenInetAddresses_5() {
        assertFalse(validator.isValidInet6Address(""), "IP empty string should be invalid");
    }

    @Test
    public void testInetAddressesByClass_1() {
        assertTrue(validator.isValid("24.25.231.12"), "class A IP should be valid");
    }

    @Test
    public void testInetAddressesByClass_2() {
        assertFalse(validator.isValid("2.41.32.324"), "illegal class A IP should be invalid");
    }

    @Test
    public void testInetAddressesByClass_3() {
        assertTrue(validator.isValid("135.14.44.12"), "class B IP should be valid");
    }

    @Test
    public void testInetAddressesByClass_4() {
        assertFalse(validator.isValid("154.123.441.123"), "illegal class B IP should be invalid");
    }

    @Test
    public void testInetAddressesByClass_5() {
        assertTrue(validator.isValid("213.25.224.32"), "class C IP should be valid");
    }

    @Test
    public void testInetAddressesByClass_6() {
        assertFalse(validator.isValid("201.543.23.11"), "illegal class C IP should be invalid");
    }

    @Test
    public void testInetAddressesByClass_7() {
        assertTrue(validator.isValid("229.35.159.6"), "class D IP should be valid");
    }

    @Test
    public void testInetAddressesByClass_8() {
        assertFalse(validator.isValid("231.54.11.987"), "illegal class D IP should be invalid");
    }

    @Test
    public void testInetAddressesByClass_9() {
        assertTrue(validator.isValid("248.85.24.92"), "class E IP should be valid");
    }

    @Test
    public void testInetAddressesByClass_10() {
        assertFalse(validator.isValid("250.21.323.48"), "illegal class E IP should be invalid");
    }

    @Test
    public void testInetAddressesFromTheWild_1() {
        assertTrue(validator.isValid("140.211.11.130"), "www.apache.org IP should be valid");
    }

    @Test
    public void testInetAddressesFromTheWild_2() {
        assertTrue(validator.isValid("72.14.253.103"), "www.l.google.com IP should be valid");
    }

    @Test
    public void testInetAddressesFromTheWild_3() {
        assertTrue(validator.isValid("199.232.41.5"), "fsf.org IP should be valid");
    }

    @Test
    public void testInetAddressesFromTheWild_4() {
        assertTrue(validator.isValid("216.35.123.87"), "appscs.ign.com IP should be valid");
    }

    @Test
    public void testIPv6_1() {
        assertFalse(validator.isValidInet6Address(""), "IPV6 empty string should be invalid");
    }

    @Test
    public void testIPv6_2() {
        assertTrue(validator.isValidInet6Address("::1"), "IPV6 ::1 should be valid");
    }

    @Test
    public void testIPv6_3() {
        assertTrue(validator.isValidInet6Address("::"), "IPV6 :: should be valid");
    }

    @Test
    public void testIPv6_4() {
        assertTrue(validator.isValidInet6Address("0:0:0:0:0:0:0:1"), "IPV6 0:0:0:0:0:0:0:1 should be valid");
    }

    @Test
    public void testIPv6_5() {
        assertTrue(validator.isValidInet6Address("0:0:0:0:0:0:0:0"), "IPV6 0:0:0:0:0:0:0:0 should be valid");
    }

    @Test
    public void testIPv6_6() {
        assertTrue(validator.isValidInet6Address("2001:DB8:0:0:8:800:200C:417A"), "IPV6 2001:DB8:0:0:8:800:200C:417A should be valid");
    }

    @Test
    public void testIPv6_7() {
        assertTrue(validator.isValidInet6Address("FF01:0:0:0:0:0:0:101"), "IPV6 FF01:0:0:0:0:0:0:101 should be valid");
    }

    @Test
    public void testIPv6_8() {
        assertTrue(validator.isValidInet6Address("2001:DB8::8:800:200C:417A"), "IPV6 2001:DB8::8:800:200C:417A should be valid");
    }

    @Test
    public void testIPv6_9() {
        assertTrue(validator.isValidInet6Address("FF01::101"), "IPV6 FF01::101 should be valid");
    }

    @Test
    public void testIPv6_10() {
        assertFalse(validator.isValidInet6Address("2001:DB8:0:0:8:800:200C:417A:221"), "IPV6 2001:DB8:0:0:8:800:200C:417A:221 should be invalid");
    }

    @Test
    public void testIPv6_11() {
        assertFalse(validator.isValidInet6Address("FF01::101::2"), "IPV6 FF01::101::2 should be invalid");
    }

    @Test
    public void testIPv6_12() {
        assertTrue(validator.isValidInet6Address("fe80::217:f2ff:fe07:ed62"), "IPV6 fe80::217:f2ff:fe07:ed62 should be valid");
    }

    @Test
    public void testIPv6_13() {
        assertTrue(validator.isValidInet6Address("2001:0000:1234:0000:0000:C1C0:ABCD:0876"), "IPV6 2001:0000:1234:0000:0000:C1C0:ABCD:0876 should be valid");
    }

    @Test
    public void testIPv6_14() {
        assertTrue(validator.isValidInet6Address("3ffe:0b00:0000:0000:0001:0000:0000:000a"), "IPV6 3ffe:0b00:0000:0000:0001:0000:0000:000a should be valid");
    }

    @Test
    public void testIPv6_15() {
        assertTrue(validator.isValidInet6Address("FF02:0000:0000:0000:0000:0000:0000:0001"), "IPV6 FF02:0000:0000:0000:0000:0000:0000:0001 should be valid");
    }

    @Test
    public void testIPv6_16() {
        assertTrue(validator.isValidInet6Address("0000:0000:0000:0000:0000:0000:0000:0001"), "IPV6 0000:0000:0000:0000:0000:0000:0000:0001 should be valid");
    }

    @Test
    public void testIPv6_17() {
        assertTrue(validator.isValidInet6Address("0000:0000:0000:0000:0000:0000:0000:0000"), "IPV6 0000:0000:0000:0000:0000:0000:0000:0000 should be valid");
    }

    @Test
    public void testIPv6_18() {
        assertFalse(validator.isValidInet6Address("02001:0000:1234:0000:0000:C1C0:ABCD:0876"), "IPV6 02001:0000:1234:0000:0000:C1C0:ABCD:0876 should be invalid");
    }

    @Test
    public void testIPv6_19() {
        assertFalse(validator.isValidInet6Address("2001:0000:1234:0000:00001:C1C0:ABCD:0876"), "IPV6 2001:0000:1234:0000:00001:C1C0:ABCD:0876 should be invalid");
    }

    @Test
    public void testIPv6_20() {
        assertFalse(validator.isValidInet6Address("2001:0000:1234:0000:0000:C1C0:ABCD:0876 0"), "IPV6 2001:0000:1234:0000:0000:C1C0:ABCD:0876 0 should be invalid");
    }

    @Test
    public void testIPv6_21() {
        assertFalse(validator.isValidInet6Address("2001:0000:1234: 0000:0000:C1C0:ABCD:0876"), "IPV6 2001:0000:1234: 0000:0000:C1C0:ABCD:0876 should be invalid");
    }

    @Test
    public void testIPv6_22() {
        assertFalse(validator.isValidInet6Address("3ffe:0b00:0000:0001:0000:0000:000a"), "IPV6 3ffe:0b00:0000:0001:0000:0000:000a should be invalid");
    }

    @Test
    public void testIPv6_23() {
        assertFalse(validator.isValidInet6Address("FF02:0000:0000:0000:0000:0000:0000:0000:0001"), "IPV6 FF02:0000:0000:0000:0000:0000:0000:0000:0001 should be invalid");
    }

    @Test
    public void testIPv6_24() {
        assertFalse(validator.isValidInet6Address("3ffe:b00::1::a"), "IPV6 3ffe:b00::1::a should be invalid");
    }

    @Test
    public void testIPv6_25() {
        assertFalse(validator.isValidInet6Address("::1111:2222:3333:4444:5555:6666::"), "IPV6 ::1111:2222:3333:4444:5555:6666:: should be invalid");
    }

    @Test
    public void testIPv6_26() {
        assertTrue(validator.isValidInet6Address("2::10"), "IPV6 2::10 should be valid");
    }

    @Test
    public void testIPv6_27() {
        assertTrue(validator.isValidInet6Address("ff02::1"), "IPV6 ff02::1 should be valid");
    }

    @Test
    public void testIPv6_28() {
        assertTrue(validator.isValidInet6Address("fe80::"), "IPV6 fe80:: should be valid");
    }

    @Test
    public void testIPv6_29() {
        assertTrue(validator.isValidInet6Address("2002::"), "IPV6 2002:: should be valid");
    }

    @Test
    public void testIPv6_30() {
        assertTrue(validator.isValidInet6Address("2001:db8::"), "IPV6 2001:db8:: should be valid");
    }

    @Test
    public void testIPv6_31() {
        assertTrue(validator.isValidInet6Address("2001:0db8:1234::"), "IPV6 2001:0db8:1234:: should be valid");
    }

    @Test
    public void testIPv6_32() {
        assertTrue(validator.isValidInet6Address("::ffff:0:0"), "IPV6 ::ffff:0:0 should be valid");
    }

    @Test
    public void testIPv6_33() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5:6:7:8"), "IPV6 1:2:3:4:5:6:7:8 should be valid");
    }

    @Test
    public void testIPv6_34() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5:6::8"), "IPV6 1:2:3:4:5:6::8 should be valid");
    }

    @Test
    public void testIPv6_35() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5::8"), "IPV6 1:2:3:4:5::8 should be valid");
    }

    @Test
    public void testIPv6_36() {
        assertTrue(validator.isValidInet6Address("1:2:3:4::8"), "IPV6 1:2:3:4::8 should be valid");
    }

    @Test
    public void testIPv6_37() {
        assertTrue(validator.isValidInet6Address("1:2:3::8"), "IPV6 1:2:3::8 should be valid");
    }

    @Test
    public void testIPv6_38() {
        assertTrue(validator.isValidInet6Address("1:2::8"), "IPV6 1:2::8 should be valid");
    }

    @Test
    public void testIPv6_39() {
        assertTrue(validator.isValidInet6Address("1::8"), "IPV6 1::8 should be valid");
    }

    @Test
    public void testIPv6_40() {
        assertTrue(validator.isValidInet6Address("1::2:3:4:5:6:7"), "IPV6 1::2:3:4:5:6:7 should be valid");
    }

    @Test
    public void testIPv6_41() {
        assertTrue(validator.isValidInet6Address("1::2:3:4:5:6"), "IPV6 1::2:3:4:5:6 should be valid");
    }

    @Test
    public void testIPv6_42() {
        assertTrue(validator.isValidInet6Address("1::2:3:4:5"), "IPV6 1::2:3:4:5 should be valid");
    }

    @Test
    public void testIPv6_43() {
        assertTrue(validator.isValidInet6Address("1::2:3:4"), "IPV6 1::2:3:4 should be valid");
    }

    @Test
    public void testIPv6_44() {
        assertTrue(validator.isValidInet6Address("1::2:3"), "IPV6 1::2:3 should be valid");
    }

    @Test
    public void testIPv6_45() {
        assertTrue(validator.isValidInet6Address("::2:3:4:5:6:7:8"), "IPV6 ::2:3:4:5:6:7:8 should be valid");
    }

    @Test
    public void testIPv6_46() {
        assertTrue(validator.isValidInet6Address("::2:3:4:5:6:7"), "IPV6 ::2:3:4:5:6:7 should be valid");
    }

    @Test
    public void testIPv6_47() {
        assertTrue(validator.isValidInet6Address("::2:3:4:5:6"), "IPV6 ::2:3:4:5:6 should be valid");
    }

    @Test
    public void testIPv6_48() {
        assertTrue(validator.isValidInet6Address("::2:3:4:5"), "IPV6 ::2:3:4:5 should be valid");
    }

    @Test
    public void testIPv6_49() {
        assertTrue(validator.isValidInet6Address("::2:3:4"), "IPV6 ::2:3:4 should be valid");
    }

    @Test
    public void testIPv6_50() {
        assertTrue(validator.isValidInet6Address("::2:3"), "IPV6 ::2:3 should be valid");
    }

    @Test
    public void testIPv6_51() {
        assertTrue(validator.isValidInet6Address("::8"), "IPV6 ::8 should be valid");
    }

    @Test
    public void testIPv6_52() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5:6::"), "IPV6 1:2:3:4:5:6:: should be valid");
    }

    @Test
    public void testIPv6_53() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5::"), "IPV6 1:2:3:4:5:: should be valid");
    }

    @Test
    public void testIPv6_54() {
        assertTrue(validator.isValidInet6Address("1:2:3:4::"), "IPV6 1:2:3:4:: should be valid");
    }

    @Test
    public void testIPv6_55() {
        assertTrue(validator.isValidInet6Address("1:2:3::"), "IPV6 1:2:3:: should be valid");
    }

    @Test
    public void testIPv6_56() {
        assertTrue(validator.isValidInet6Address("1:2::"), "IPV6 1:2:: should be valid");
    }

    @Test
    public void testIPv6_57() {
        assertTrue(validator.isValidInet6Address("1::"), "IPV6 1:: should be valid");
    }

    @Test
    public void testIPv6_58() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5::7:8"), "IPV6 1:2:3:4:5::7:8 should be valid");
    }

    @Test
    public void testIPv6_59() {
        assertFalse(validator.isValidInet6Address("1:2:3::4:5::7:8"), "IPV6 1:2:3::4:5::7:8 should be invalid");
    }

    @Test
    public void testIPv6_60() {
        assertFalse(validator.isValidInet6Address("12345::6:7:8"), "IPV6 12345::6:7:8 should be invalid");
    }

    @Test
    public void testIPv6_61() {
        assertTrue(validator.isValidInet6Address("1:2:3:4::7:8"), "IPV6 1:2:3:4::7:8 should be valid");
    }

    @Test
    public void testIPv6_62() {
        assertTrue(validator.isValidInet6Address("1:2:3::7:8"), "IPV6 1:2:3::7:8 should be valid");
    }

    @Test
    public void testIPv6_63() {
        assertTrue(validator.isValidInet6Address("1:2::7:8"), "IPV6 1:2::7:8 should be valid");
    }

    @Test
    public void testIPv6_64() {
        assertTrue(validator.isValidInet6Address("1::7:8"), "IPV6 1::7:8 should be valid");
    }

    @Test
    public void testIPv6_65() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5:6:1.2.3.4"), "IPV6 1:2:3:4:5:6:1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_66() {
        assertTrue(validator.isValidInet6Address("1:2:3:4:5::1.2.3.4"), "IPV6 1:2:3:4:5::1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_67() {
        assertTrue(validator.isValidInet6Address("1:2:3:4::1.2.3.4"), "IPV6 1:2:3:4::1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_68() {
        assertTrue(validator.isValidInet6Address("1:2:3::1.2.3.4"), "IPV6 1:2:3::1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_69() {
        assertTrue(validator.isValidInet6Address("1:2::1.2.3.4"), "IPV6 1:2::1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_70() {
        assertTrue(validator.isValidInet6Address("1::1.2.3.4"), "IPV6 1::1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_71() {
        assertTrue(validator.isValidInet6Address("1:2:3:4::5:1.2.3.4"), "IPV6 1:2:3:4::5:1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_72() {
        assertTrue(validator.isValidInet6Address("1:2:3::5:1.2.3.4"), "IPV6 1:2:3::5:1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_73() {
        assertTrue(validator.isValidInet6Address("1:2::5:1.2.3.4"), "IPV6 1:2::5:1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_74() {
        assertTrue(validator.isValidInet6Address("1::5:1.2.3.4"), "IPV6 1::5:1.2.3.4 should be valid");
    }

    @Test
    public void testIPv6_75() {
        assertTrue(validator.isValidInet6Address("1::5:11.22.33.44"), "IPV6 1::5:11.22.33.44 should be valid");
    }

    @Test
    public void testIPv6_76() {
        assertFalse(validator.isValidInet6Address("1::5:400.2.3.4"), "IPV6 1::5:400.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_77() {
        assertFalse(validator.isValidInet6Address("1::5:260.2.3.4"), "IPV6 1::5:260.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_78() {
        assertFalse(validator.isValidInet6Address("1::5:256.2.3.4"), "IPV6 1::5:256.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_79() {
        assertFalse(validator.isValidInet6Address("1::5:1.256.3.4"), "IPV6 1::5:1.256.3.4 should be invalid");
    }

    @Test
    public void testIPv6_80() {
        assertFalse(validator.isValidInet6Address("1::5:1.2.256.4"), "IPV6 1::5:1.2.256.4 should be invalid");
    }

    @Test
    public void testIPv6_81() {
        assertFalse(validator.isValidInet6Address("1::5:1.2.3.256"), "IPV6 1::5:1.2.3.256 should be invalid");
    }

    @Test
    public void testIPv6_82() {
        assertFalse(validator.isValidInet6Address("1::5:300.2.3.4"), "IPV6 1::5:300.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_83() {
        assertFalse(validator.isValidInet6Address("1::5:1.300.3.4"), "IPV6 1::5:1.300.3.4 should be invalid");
    }

    @Test
    public void testIPv6_84() {
        assertFalse(validator.isValidInet6Address("1::5:1.2.300.4"), "IPV6 1::5:1.2.300.4 should be invalid");
    }

    @Test
    public void testIPv6_85() {
        assertFalse(validator.isValidInet6Address("1::5:1.2.3.300"), "IPV6 1::5:1.2.3.300 should be invalid");
    }

    @Test
    public void testIPv6_86() {
        assertFalse(validator.isValidInet6Address("1::5:900.2.3.4"), "IPV6 1::5:900.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_87() {
        assertFalse(validator.isValidInet6Address("1::5:1.900.3.4"), "IPV6 1::5:1.900.3.4 should be invalid");
    }

    @Test
    public void testIPv6_88() {
        assertFalse(validator.isValidInet6Address("1::5:1.2.900.4"), "IPV6 1::5:1.2.900.4 should be invalid");
    }

    @Test
    public void testIPv6_89() {
        assertFalse(validator.isValidInet6Address("1::5:1.2.3.900"), "IPV6 1::5:1.2.3.900 should be invalid");
    }

    @Test
    public void testIPv6_90() {
        assertFalse(validator.isValidInet6Address("1::5:300.300.300.300"), "IPV6 1::5:300.300.300.300 should be invalid");
    }

    @Test
    public void testIPv6_91() {
        assertFalse(validator.isValidInet6Address("1::5:3000.30.30.30"), "IPV6 1::5:3000.30.30.30 should be invalid");
    }

    @Test
    public void testIPv6_92() {
        assertFalse(validator.isValidInet6Address("1::400.2.3.4"), "IPV6 1::400.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_93() {
        assertFalse(validator.isValidInet6Address("1::260.2.3.4"), "IPV6 1::260.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_94() {
        assertFalse(validator.isValidInet6Address("1::256.2.3.4"), "IPV6 1::256.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_95() {
        assertFalse(validator.isValidInet6Address("1::1.256.3.4"), "IPV6 1::1.256.3.4 should be invalid");
    }

    @Test
    public void testIPv6_96() {
        assertFalse(validator.isValidInet6Address("1::1.2.256.4"), "IPV6 1::1.2.256.4 should be invalid");
    }

    @Test
    public void testIPv6_97() {
        assertFalse(validator.isValidInet6Address("1::1.2.3.256"), "IPV6 1::1.2.3.256 should be invalid");
    }

    @Test
    public void testIPv6_98() {
        assertFalse(validator.isValidInet6Address("1::300.2.3.4"), "IPV6 1::300.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_99() {
        assertFalse(validator.isValidInet6Address("1::1.300.3.4"), "IPV6 1::1.300.3.4 should be invalid");
    }

    @Test
    public void testIPv6_100() {
        assertFalse(validator.isValidInet6Address("1::1.2.300.4"), "IPV6 1::1.2.300.4 should be invalid");
    }

    @Test
    public void testIPv6_101() {
        assertFalse(validator.isValidInet6Address("1::1.2.3.300"), "IPV6 1::1.2.3.300 should be invalid");
    }

    @Test
    public void testIPv6_102() {
        assertFalse(validator.isValidInet6Address("1::900.2.3.4"), "IPV6 1::900.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_103() {
        assertFalse(validator.isValidInet6Address("1::1.900.3.4"), "IPV6 1::1.900.3.4 should be invalid");
    }

    @Test
    public void testIPv6_104() {
        assertFalse(validator.isValidInet6Address("1::1.2.900.4"), "IPV6 1::1.2.900.4 should be invalid");
    }

    @Test
    public void testIPv6_105() {
        assertFalse(validator.isValidInet6Address("1::1.2.3.900"), "IPV6 1::1.2.3.900 should be invalid");
    }

    @Test
    public void testIPv6_106() {
        assertFalse(validator.isValidInet6Address("1::300.300.300.300"), "IPV6 1::300.300.300.300 should be invalid");
    }

    @Test
    public void testIPv6_107() {
        assertFalse(validator.isValidInet6Address("1::3000.30.30.30"), "IPV6 1::3000.30.30.30 should be invalid");
    }

    @Test
    public void testIPv6_108() {
        assertFalse(validator.isValidInet6Address("::400.2.3.4"), "IPV6 ::400.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_109() {
        assertFalse(validator.isValidInet6Address("::260.2.3.4"), "IPV6 ::260.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_110() {
        assertFalse(validator.isValidInet6Address("::256.2.3.4"), "IPV6 ::256.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_111() {
        assertFalse(validator.isValidInet6Address("::1.256.3.4"), "IPV6 ::1.256.3.4 should be invalid");
    }

    @Test
    public void testIPv6_112() {
        assertFalse(validator.isValidInet6Address("::1.2.256.4"), "IPV6 ::1.2.256.4 should be invalid");
    }

    @Test
    public void testIPv6_113() {
        assertFalse(validator.isValidInet6Address("::1.2.3.256"), "IPV6 ::1.2.3.256 should be invalid");
    }

    @Test
    public void testIPv6_114() {
        assertFalse(validator.isValidInet6Address("::300.2.3.4"), "IPV6 ::300.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_115() {
        assertFalse(validator.isValidInet6Address("::1.300.3.4"), "IPV6 ::1.300.3.4 should be invalid");
    }

    @Test
    public void testIPv6_116() {
        assertFalse(validator.isValidInet6Address("::1.2.300.4"), "IPV6 ::1.2.300.4 should be invalid");
    }

    @Test
    public void testIPv6_117() {
        assertFalse(validator.isValidInet6Address("::1.2.3.300"), "IPV6 ::1.2.3.300 should be invalid");
    }

    @Test
    public void testIPv6_118() {
        assertFalse(validator.isValidInet6Address("::900.2.3.4"), "IPV6 ::900.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_119() {
        assertFalse(validator.isValidInet6Address("::1.900.3.4"), "IPV6 ::1.900.3.4 should be invalid");
    }

    @Test
    public void testIPv6_120() {
        assertFalse(validator.isValidInet6Address("::1.2.900.4"), "IPV6 ::1.2.900.4 should be invalid");
    }

    @Test
    public void testIPv6_121() {
        assertFalse(validator.isValidInet6Address("::1.2.3.900"), "IPV6 ::1.2.3.900 should be invalid");
    }

    @Test
    public void testIPv6_122() {
        assertFalse(validator.isValidInet6Address("::300.300.300.300"), "IPV6 ::300.300.300.300 should be invalid");
    }

    @Test
    public void testIPv6_123() {
        assertFalse(validator.isValidInet6Address("::3000.30.30.30"), "IPV6 ::3000.30.30.30 should be invalid");
    }

    @Test
    public void testIPv6_124() {
        assertTrue(validator.isValidInet6Address("fe80::217:f2ff:254.7.237.98"), "IPV6 fe80::217:f2ff:254.7.237.98 should be valid");
    }

    @Test
    public void testIPv6_125() {
        assertTrue(validator.isValidInet6Address("::ffff:192.168.1.26"), "IPV6 ::ffff:192.168.1.26 should be valid");
    }

    @Test
    public void testIPv6_126() {
        assertFalse(validator.isValidInet6Address("2001:1:1:1:1:1:255Z255X255Y255"), "IPV6 2001:1:1:1:1:1:255Z255X255Y255 should be invalid");
    }

    @Test
    public void testIPv6_127() {
        assertFalse(validator.isValidInet6Address("::ffff:192x168.1.26"), "IPV6 ::ffff:192x168.1.26 should be invalid");
    }

    @Test
    public void testIPv6_128() {
        assertTrue(validator.isValidInet6Address("::ffff:192.168.1.1"), "IPV6 ::ffff:192.168.1.1 should be valid");
    }

    @Test
    public void testIPv6_129() {
        assertTrue(validator.isValidInet6Address("0:0:0:0:0:0:13.1.68.3"), "IPV6 0:0:0:0:0:0:13.1.68.3 should be valid");
    }

    @Test
    public void testIPv6_130() {
        assertTrue(validator.isValidInet6Address("0:0:0:0:0:FFFF:129.144.52.38"), "IPV6 0:0:0:0:0:FFFF:129.144.52.38 should be valid");
    }

    @Test
    public void testIPv6_131() {
        assertTrue(validator.isValidInet6Address("::13.1.68.3"), "IPV6 ::13.1.68.3 should be valid");
    }

    @Test
    public void testIPv6_132() {
        assertTrue(validator.isValidInet6Address("::FFFF:129.144.52.38"), "IPV6 ::FFFF:129.144.52.38 should be valid");
    }

    @Test
    public void testIPv6_133() {
        assertTrue(validator.isValidInet6Address("fe80:0:0:0:204:61ff:254.157.241.86"), "IPV6 fe80:0:0:0:204:61ff:254.157.241.86 should be valid");
    }

    @Test
    public void testIPv6_134() {
        assertTrue(validator.isValidInet6Address("fe80::204:61ff:254.157.241.86"), "IPV6 fe80::204:61ff:254.157.241.86 should be valid");
    }

    @Test
    public void testIPv6_135() {
        assertTrue(validator.isValidInet6Address("::ffff:12.34.56.78"), "IPV6 ::ffff:12.34.56.78 should be valid");
    }

    @Test
    public void testIPv6_136() {
        assertFalse(validator.isValidInet6Address("::ffff:2.3.4"), "IPV6 ::ffff:2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_137() {
        assertFalse(validator.isValidInet6Address("::ffff:257.1.2.3"), "IPV6 ::ffff:257.1.2.3 should be invalid");
    }

    @Test
    public void testIPv6_138() {
        assertFalse(validator.isValidInet6Address("1.2.3.4"), "IPV6 1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_139() {
        assertFalse(validator.isValidInet6Address("1.2.3.4:1111:2222:3333:4444::5555"), "IPV6 1.2.3.4:1111:2222:3333:4444::5555 should be invalid");
    }

    @Test
    public void testIPv6_140() {
        assertFalse(validator.isValidInet6Address("1.2.3.4:1111:2222:3333::5555"), "IPV6 1.2.3.4:1111:2222:3333::5555 should be invalid");
    }

    @Test
    public void testIPv6_141() {
        assertFalse(validator.isValidInet6Address("1.2.3.4:1111:2222::5555"), "IPV6 1.2.3.4:1111:2222::5555 should be invalid");
    }

    @Test
    public void testIPv6_142() {
        assertFalse(validator.isValidInet6Address("1.2.3.4:1111::5555"), "IPV6 1.2.3.4:1111::5555 should be invalid");
    }

    @Test
    public void testIPv6_143() {
        assertFalse(validator.isValidInet6Address("1.2.3.4::5555"), "IPV6 1.2.3.4::5555 should be invalid");
    }

    @Test
    public void testIPv6_144() {
        assertFalse(validator.isValidInet6Address("1.2.3.4::"), "IPV6 1.2.3.4:: should be invalid");
    }

    @Test
    public void testIPv6_145() {
        assertFalse(validator.isValidInet6Address("fe80:0000:0000:0000:0204:61ff:254.157.241.086"), "IPV6 fe80:0000:0000:0000:0204:61ff:254.157.241.086 should be invalid");
    }

    @Test
    public void testIPv6_146() {
        assertTrue(validator.isValidInet6Address("::ffff:192.0.2.128"), "IPV6 ::ffff:192.0.2.128 should be valid");
    }

    @Test
    public void testIPv6_147() {
        assertFalse(validator.isValidInet6Address("XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:1.2.3.4"), "IPV6 XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_148() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:00.00.00.00"), "IPV6 1111:2222:3333:4444:5555:6666:00.00.00.00 should be invalid");
    }

    @Test
    public void testIPv6_149() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:000.000.000.000"), "IPV6 1111:2222:3333:4444:5555:6666:000.000.000.000 should be invalid");
    }

    @Test
    public void testIPv6_150() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:256.256.256.256"), "IPV6 1111:2222:3333:4444:5555:6666:256.256.256.256 should be invalid");
    }

    @Test
    public void testIPv6_151() {
        assertTrue(validator.isValidInet6Address("fe80:0000:0000:0000:0204:61ff:fe9d:f156"), "IPV6 fe80:0000:0000:0000:0204:61ff:fe9d:f156 should be valid");
    }

    @Test
    public void testIPv6_152() {
        assertTrue(validator.isValidInet6Address("fe80:0:0:0:204:61ff:fe9d:f156"), "IPV6 fe80:0:0:0:204:61ff:fe9d:f156 should be valid");
    }

    @Test
    public void testIPv6_153() {
        assertTrue(validator.isValidInet6Address("fe80::204:61ff:fe9d:f156"), "IPV6 fe80::204:61ff:fe9d:f156 should be valid");
    }

    @Test
    public void testIPv6_154() {
        assertFalse(validator.isValidInet6Address(":"), "IPV6 : should be invalid");
    }

    @Test
    public void testIPv6_155() {
        assertTrue(validator.isValidInet6Address("::ffff:c000:280"), "IPV6 ::ffff:c000:280 should be valid");
    }

    @Test
    public void testIPv6_156() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444::5555:"), "IPV6 1111:2222:3333:4444::5555: should be invalid");
    }

    @Test
    public void testIPv6_157() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::5555:"), "IPV6 1111:2222:3333::5555: should be invalid");
    }

    @Test
    public void testIPv6_158() {
        assertFalse(validator.isValidInet6Address("1111:2222::5555:"), "IPV6 1111:2222::5555: should be invalid");
    }

    @Test
    public void testIPv6_159() {
        assertFalse(validator.isValidInet6Address("1111::5555:"), "IPV6 1111::5555: should be invalid");
    }

    @Test
    public void testIPv6_160() {
        assertFalse(validator.isValidInet6Address("::5555:"), "IPV6 ::5555: should be invalid");
    }

    @Test
    public void testIPv6_161() {
        assertFalse(validator.isValidInet6Address(":::"), "IPV6 ::: should be invalid");
    }

    @Test
    public void testIPv6_162() {
        assertFalse(validator.isValidInet6Address("1111:"), "IPV6 1111: should be invalid");
    }

    @Test
    public void testIPv6_163() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444::5555"), "IPV6 :1111:2222:3333:4444::5555 should be invalid");
    }

    @Test
    public void testIPv6_164() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::5555"), "IPV6 :1111:2222:3333::5555 should be invalid");
    }

    @Test
    public void testIPv6_165() {
        assertFalse(validator.isValidInet6Address(":1111:2222::5555"), "IPV6 :1111:2222::5555 should be invalid");
    }

    @Test
    public void testIPv6_166() {
        assertFalse(validator.isValidInet6Address(":1111::5555"), "IPV6 :1111::5555 should be invalid");
    }

    @Test
    public void testIPv6_167() {
        assertFalse(validator.isValidInet6Address(":::5555"), "IPV6 :::5555 should be invalid");
    }

    @Test
    public void testIPv6_168() {
        assertTrue(validator.isValidInet6Address("2001:0db8:85a3:0000:0000:8a2e:0370:7334"), "IPV6 2001:0db8:85a3:0000:0000:8a2e:0370:7334 should be valid");
    }

    @Test
    public void testIPv6_169() {
        assertTrue(validator.isValidInet6Address("2001:db8:85a3:0:0:8a2e:370:7334"), "IPV6 2001:db8:85a3:0:0:8a2e:370:7334 should be valid");
    }

    @Test
    public void testIPv6_170() {
        assertTrue(validator.isValidInet6Address("2001:db8:85a3::8a2e:370:7334"), "IPV6 2001:db8:85a3::8a2e:370:7334 should be valid");
    }

    @Test
    public void testIPv6_171() {
        assertTrue(validator.isValidInet6Address("2001:0db8:0000:0000:0000:0000:1428:57ab"), "IPV6 2001:0db8:0000:0000:0000:0000:1428:57ab should be valid");
    }

    @Test
    public void testIPv6_172() {
        assertTrue(validator.isValidInet6Address("2001:0db8:0000:0000:0000::1428:57ab"), "IPV6 2001:0db8:0000:0000:0000::1428:57ab should be valid");
    }

    @Test
    public void testIPv6_173() {
        assertTrue(validator.isValidInet6Address("2001:0db8:0:0:0:0:1428:57ab"), "IPV6 2001:0db8:0:0:0:0:1428:57ab should be valid");
    }

    @Test
    public void testIPv6_174() {
        assertTrue(validator.isValidInet6Address("2001:0db8:0:0::1428:57ab"), "IPV6 2001:0db8:0:0::1428:57ab should be valid");
    }

    @Test
    public void testIPv6_175() {
        assertTrue(validator.isValidInet6Address("2001:0db8::1428:57ab"), "IPV6 2001:0db8::1428:57ab should be valid");
    }

    @Test
    public void testIPv6_176() {
        assertTrue(validator.isValidInet6Address("2001:db8::1428:57ab"), "IPV6 2001:db8::1428:57ab should be valid");
    }

    @Test
    public void testIPv6_177() {
        assertTrue(validator.isValidInet6Address("::ffff:0c22:384e"), "IPV6 ::ffff:0c22:384e should be valid");
    }

    @Test
    public void testIPv6_178() {
        assertTrue(validator.isValidInet6Address("2001:0db8:1234:0000:0000:0000:0000:0000"), "IPV6 2001:0db8:1234:0000:0000:0000:0000:0000 should be valid");
    }

    @Test
    public void testIPv6_179() {
        assertTrue(validator.isValidInet6Address("2001:0db8:1234:ffff:ffff:ffff:ffff:ffff"), "IPV6 2001:0db8:1234:ffff:ffff:ffff:ffff:ffff should be valid");
    }

    @Test
    public void testIPv6_180() {
        assertTrue(validator.isValidInet6Address("2001:db8:a::123"), "IPV6 2001:db8:a::123 should be valid");
    }

    @Test
    public void testIPv6_181() {
        assertFalse(validator.isValidInet6Address("123"), "IPV6 123 should be invalid");
    }

    @Test
    public void testIPv6_182() {
        assertFalse(validator.isValidInet6Address("ldkfj"), "IPV6 ldkfj should be invalid");
    }

    @Test
    public void testIPv6_183() {
        assertFalse(validator.isValidInet6Address("2001::FFD3::57ab"), "IPV6 2001::FFD3::57ab should be invalid");
    }

    @Test
    public void testIPv6_184() {
        assertFalse(validator.isValidInet6Address("2001:db8:85a3::8a2e:37023:7334"), "IPV6 2001:db8:85a3::8a2e:37023:7334 should be invalid");
    }

    @Test
    public void testIPv6_185() {
        assertFalse(validator.isValidInet6Address("2001:db8:85a3::8a2e:370k:7334"), "IPV6 2001:db8:85a3::8a2e:370k:7334 should be invalid");
    }

    @Test
    public void testIPv6_186() {
        assertFalse(validator.isValidInet6Address("1:2:3:4:5:6:7:8:9"), "IPV6 1:2:3:4:5:6:7:8:9 should be invalid");
    }

    @Test
    public void testIPv6_187() {
        assertFalse(validator.isValidInet6Address("1::2::3"), "IPV6 1::2::3 should be invalid");
    }

    @Test
    public void testIPv6_188() {
        assertFalse(validator.isValidInet6Address("1:::3:4:5"), "IPV6 1:::3:4:5 should be invalid");
    }

    @Test
    public void testIPv6_189() {
        assertFalse(validator.isValidInet6Address("1:2:3::4:5:6:7:8:9"), "IPV6 1:2:3::4:5:6:7:8:9 should be invalid");
    }

    @Test
    public void testIPv6_190() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:8888"), "IPV6 1111:2222:3333:4444:5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_191() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777::"), "IPV6 1111:2222:3333:4444:5555:6666:7777:: should be valid");
    }

    @Test
    public void testIPv6_192() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666::"), "IPV6 1111:2222:3333:4444:5555:6666:: should be valid");
    }

    @Test
    public void testIPv6_193() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555::"), "IPV6 1111:2222:3333:4444:5555:: should be valid");
    }

    @Test
    public void testIPv6_194() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444::"), "IPV6 1111:2222:3333:4444:: should be valid");
    }

    @Test
    public void testIPv6_195() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::"), "IPV6 1111:2222:3333:: should be valid");
    }

    @Test
    public void testIPv6_196() {
        assertTrue(validator.isValidInet6Address("1111:2222::"), "IPV6 1111:2222:: should be valid");
    }

    @Test
    public void testIPv6_197() {
        assertTrue(validator.isValidInet6Address("1111::"), "IPV6 1111:: should be valid");
    }

    @Test
    public void testIPv6_198() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666::8888"), "IPV6 1111:2222:3333:4444:5555:6666::8888 should be valid");
    }

    @Test
    public void testIPv6_199() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555::8888"), "IPV6 1111:2222:3333:4444:5555::8888 should be valid");
    }

    @Test
    public void testIPv6_200() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444::8888"), "IPV6 1111:2222:3333:4444::8888 should be valid");
    }

    @Test
    public void testIPv6_201() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::8888"), "IPV6 1111:2222:3333::8888 should be valid");
    }

    @Test
    public void testIPv6_202() {
        assertTrue(validator.isValidInet6Address("1111:2222::8888"), "IPV6 1111:2222::8888 should be valid");
    }

    @Test
    public void testIPv6_203() {
        assertTrue(validator.isValidInet6Address("1111::8888"), "IPV6 1111::8888 should be valid");
    }

    @Test
    public void testIPv6_204() {
        assertTrue(validator.isValidInet6Address("::8888"), "IPV6 ::8888 should be valid");
    }

    @Test
    public void testIPv6_205() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555::7777:8888"), "IPV6 1111:2222:3333:4444:5555::7777:8888 should be valid");
    }

    @Test
    public void testIPv6_206() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444::7777:8888"), "IPV6 1111:2222:3333:4444::7777:8888 should be valid");
    }

    @Test
    public void testIPv6_207() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::7777:8888"), "IPV6 1111:2222:3333::7777:8888 should be valid");
    }

    @Test
    public void testIPv6_208() {
        assertTrue(validator.isValidInet6Address("1111:2222::7777:8888"), "IPV6 1111:2222::7777:8888 should be valid");
    }

    @Test
    public void testIPv6_209() {
        assertTrue(validator.isValidInet6Address("1111::7777:8888"), "IPV6 1111::7777:8888 should be valid");
    }

    @Test
    public void testIPv6_210() {
        assertTrue(validator.isValidInet6Address("::7777:8888"), "IPV6 ::7777:8888 should be valid");
    }

    @Test
    public void testIPv6_211() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444::6666:7777:8888"), "IPV6 1111:2222:3333:4444::6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_212() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::6666:7777:8888"), "IPV6 1111:2222:3333::6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_213() {
        assertTrue(validator.isValidInet6Address("1111:2222::6666:7777:8888"), "IPV6 1111:2222::6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_214() {
        assertTrue(validator.isValidInet6Address("1111::6666:7777:8888"), "IPV6 1111::6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_215() {
        assertTrue(validator.isValidInet6Address("::6666:7777:8888"), "IPV6 ::6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_216() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::5555:6666:7777:8888"), "IPV6 1111:2222:3333::5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_217() {
        assertTrue(validator.isValidInet6Address("1111:2222::5555:6666:7777:8888"), "IPV6 1111:2222::5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_218() {
        assertTrue(validator.isValidInet6Address("1111::5555:6666:7777:8888"), "IPV6 1111::5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_219() {
        assertTrue(validator.isValidInet6Address("::5555:6666:7777:8888"), "IPV6 ::5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_220() {
        assertTrue(validator.isValidInet6Address("1111:2222::4444:5555:6666:7777:8888"), "IPV6 1111:2222::4444:5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_221() {
        assertTrue(validator.isValidInet6Address("1111::4444:5555:6666:7777:8888"), "IPV6 1111::4444:5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_222() {
        assertTrue(validator.isValidInet6Address("::4444:5555:6666:7777:8888"), "IPV6 ::4444:5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_223() {
        assertTrue(validator.isValidInet6Address("1111::3333:4444:5555:6666:7777:8888"), "IPV6 1111::3333:4444:5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_224() {
        assertTrue(validator.isValidInet6Address("::3333:4444:5555:6666:7777:8888"), "IPV6 ::3333:4444:5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_225() {
        assertTrue(validator.isValidInet6Address("::2222:3333:4444:5555:6666:7777:8888"), "IPV6 ::2222:3333:4444:5555:6666:7777:8888 should be valid");
    }

    @Test
    public void testIPv6_226() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:123.123.123.123"), "IPV6 1111:2222:3333:4444:5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_227() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444:5555::123.123.123.123"), "IPV6 1111:2222:3333:4444:5555::123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_228() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444::123.123.123.123"), "IPV6 1111:2222:3333:4444::123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_229() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::123.123.123.123"), "IPV6 1111:2222:3333::123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_230() {
        assertTrue(validator.isValidInet6Address("1111:2222::123.123.123.123"), "IPV6 1111:2222::123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_231() {
        assertTrue(validator.isValidInet6Address("1111::123.123.123.123"), "IPV6 1111::123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_232() {
        assertTrue(validator.isValidInet6Address("::123.123.123.123"), "IPV6 ::123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_233() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333:4444::6666:123.123.123.123"), "IPV6 1111:2222:3333:4444::6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_234() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::6666:123.123.123.123"), "IPV6 1111:2222:3333::6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_235() {
        assertTrue(validator.isValidInet6Address("1111:2222::6666:123.123.123.123"), "IPV6 1111:2222::6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_236() {
        assertTrue(validator.isValidInet6Address("1111::6666:123.123.123.123"), "IPV6 1111::6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_237() {
        assertTrue(validator.isValidInet6Address("::6666:123.123.123.123"), "IPV6 ::6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_238() {
        assertTrue(validator.isValidInet6Address("1111:2222:3333::5555:6666:123.123.123.123"), "IPV6 1111:2222:3333::5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_239() {
        assertTrue(validator.isValidInet6Address("1111:2222::5555:6666:123.123.123.123"), "IPV6 1111:2222::5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_240() {
        assertTrue(validator.isValidInet6Address("1111::5555:6666:123.123.123.123"), "IPV6 1111::5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_241() {
        assertTrue(validator.isValidInet6Address("::5555:6666:123.123.123.123"), "IPV6 ::5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_242() {
        assertTrue(validator.isValidInet6Address("1111:2222::4444:5555:6666:123.123.123.123"), "IPV6 1111:2222::4444:5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_243() {
        assertTrue(validator.isValidInet6Address("1111::4444:5555:6666:123.123.123.123"), "IPV6 1111::4444:5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_244() {
        assertTrue(validator.isValidInet6Address("::4444:5555:6666:123.123.123.123"), "IPV6 ::4444:5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_245() {
        assertTrue(validator.isValidInet6Address("1111::3333:4444:5555:6666:123.123.123.123"), "IPV6 1111::3333:4444:5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_246() {
        assertTrue(validator.isValidInet6Address("::2222:3333:4444:5555:6666:123.123.123.123"), "IPV6 ::2222:3333:4444:5555:6666:123.123.123.123 should be valid");
    }

    @Test
    public void testIPv6_247() {
        assertTrue(validator.isValidInet6Address("::0:0:0:0:0:0:0"), "IPV6 ::0:0:0:0:0:0:0 should be valid");
    }

    @Test
    public void testIPv6_248() {
        assertTrue(validator.isValidInet6Address("::0:0:0:0:0:0"), "IPV6 ::0:0:0:0:0:0 should be valid");
    }

    @Test
    public void testIPv6_249() {
        assertTrue(validator.isValidInet6Address("::0:0:0:0:0"), "IPV6 ::0:0:0:0:0 should be valid");
    }

    @Test
    public void testIPv6_250() {
        assertTrue(validator.isValidInet6Address("::0:0:0:0"), "IPV6 ::0:0:0:0 should be valid");
    }

    @Test
    public void testIPv6_251() {
        assertTrue(validator.isValidInet6Address("::0:0:0"), "IPV6 ::0:0:0 should be valid");
    }

    @Test
    public void testIPv6_252() {
        assertTrue(validator.isValidInet6Address("::0:0"), "IPV6 ::0:0 should be valid");
    }

    @Test
    public void testIPv6_253() {
        assertTrue(validator.isValidInet6Address("::0"), "IPV6 ::0 should be valid");
    }

    @Test
    public void testIPv6_254() {
        assertTrue(validator.isValidInet6Address("0:0:0:0:0:0:0::"), "IPV6 0:0:0:0:0:0:0:: should be valid");
    }

    @Test
    public void testIPv6_255() {
        assertTrue(validator.isValidInet6Address("0:0:0:0:0:0::"), "IPV6 0:0:0:0:0:0:: should be valid");
    }

    @Test
    public void testIPv6_256() {
        assertTrue(validator.isValidInet6Address("0:0:0:0:0::"), "IPV6 0:0:0:0:0:: should be valid");
    }

    @Test
    public void testIPv6_257() {
        assertTrue(validator.isValidInet6Address("0:0:0:0::"), "IPV6 0:0:0:0:: should be valid");
    }

    @Test
    public void testIPv6_258() {
        assertTrue(validator.isValidInet6Address("0:0:0::"), "IPV6 0:0:0:: should be valid");
    }

    @Test
    public void testIPv6_259() {
        assertTrue(validator.isValidInet6Address("0:0::"), "IPV6 0:0:: should be valid");
    }

    @Test
    public void testIPv6_260() {
        assertTrue(validator.isValidInet6Address("0::"), "IPV6 0:: should be valid");
    }

    @Test
    public void testIPv6_261() {
        assertFalse(validator.isValidInet6Address("XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX"), "IPV6 XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX:XXXX should be invalid");
    }

    @Test
    public void testIPv6_262() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:8888:9999"), "IPV6 1111:2222:3333:4444:5555:6666:7777:8888:9999 should be invalid");
    }

    @Test
    public void testIPv6_263() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:8888::"), "IPV6 1111:2222:3333:4444:5555:6666:7777:8888:: should be invalid");
    }

    @Test
    public void testIPv6_264() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444:5555:6666:7777:8888:9999"), "IPV6 ::2222:3333:4444:5555:6666:7777:8888:9999 should be invalid");
    }

    @Test
    public void testIPv6_265() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777"), "IPV6 1111:2222:3333:4444:5555:6666:7777 should be invalid");
    }

    @Test
    public void testIPv6_266() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666"), "IPV6 1111:2222:3333:4444:5555:6666 should be invalid");
    }

    @Test
    public void testIPv6_267() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555"), "IPV6 1111:2222:3333:4444:5555 should be invalid");
    }

    @Test
    public void testIPv6_268() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444"), "IPV6 1111:2222:3333:4444 should be invalid");
    }

    @Test
    public void testIPv6_269() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333"), "IPV6 1111:2222:3333 should be invalid");
    }

    @Test
    public void testIPv6_270() {
        assertFalse(validator.isValidInet6Address("1111:2222"), "IPV6 1111:2222 should be invalid");
    }

    @Test
    public void testIPv6_271() {
        assertFalse(validator.isValidInet6Address("1111"), "IPV6 1111 should be invalid");
    }

    @Test
    public void testIPv6_272() {
        assertFalse(validator.isValidInet6Address("11112222:3333:4444:5555:6666:7777:8888"), "IPV6 11112222:3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_273() {
        assertFalse(validator.isValidInet6Address("1111:22223333:4444:5555:6666:7777:8888"), "IPV6 1111:22223333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_274() {
        assertFalse(validator.isValidInet6Address("1111:2222:33334444:5555:6666:7777:8888"), "IPV6 1111:2222:33334444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_275() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:44445555:6666:7777:8888"), "IPV6 1111:2222:3333:44445555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_276() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:55556666:7777:8888"), "IPV6 1111:2222:3333:4444:55556666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_277() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:66667777:8888"), "IPV6 1111:2222:3333:4444:5555:66667777:8888 should be invalid");
    }

    @Test
    public void testIPv6_278() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:77778888"), "IPV6 1111:2222:3333:4444:5555:6666:77778888 should be invalid");
    }

    @Test
    public void testIPv6_279() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:8888:"), "IPV6 1111:2222:3333:4444:5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_280() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:"), "IPV6 1111:2222:3333:4444:5555:6666:7777: should be invalid");
    }

    @Test
    public void testIPv6_281() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:"), "IPV6 1111:2222:3333:4444:5555:6666: should be invalid");
    }

    @Test
    public void testIPv6_282() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:"), "IPV6 1111:2222:3333:4444:5555: should be invalid");
    }

    @Test
    public void testIPv6_283() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:"), "IPV6 1111:2222:3333:4444: should be invalid");
    }

    @Test
    public void testIPv6_284() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:"), "IPV6 1111:2222:3333: should be invalid");
    }

    @Test
    public void testIPv6_285() {
        assertFalse(validator.isValidInet6Address("1111:2222:"), "IPV6 1111:2222: should be invalid");
    }

    @Test
    public void testIPv6_286() {
        assertFalse(validator.isValidInet6Address(":8888"), "IPV6 :8888 should be invalid");
    }

    @Test
    public void testIPv6_287() {
        assertFalse(validator.isValidInet6Address(":7777:8888"), "IPV6 :7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_288() {
        assertFalse(validator.isValidInet6Address(":6666:7777:8888"), "IPV6 :6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_289() {
        assertFalse(validator.isValidInet6Address(":5555:6666:7777:8888"), "IPV6 :5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_290() {
        assertFalse(validator.isValidInet6Address(":4444:5555:6666:7777:8888"), "IPV6 :4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_291() {
        assertFalse(validator.isValidInet6Address(":3333:4444:5555:6666:7777:8888"), "IPV6 :3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_292() {
        assertFalse(validator.isValidInet6Address(":2222:3333:4444:5555:6666:7777:8888"), "IPV6 :2222:3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_293() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555:6666:7777:8888"), "IPV6 :1111:2222:3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_294() {
        assertFalse(validator.isValidInet6Address(":::2222:3333:4444:5555:6666:7777:8888"), "IPV6 :::2222:3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_295() {
        assertFalse(validator.isValidInet6Address("1111:::3333:4444:5555:6666:7777:8888"), "IPV6 1111:::3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_296() {
        assertFalse(validator.isValidInet6Address("1111:2222:::4444:5555:6666:7777:8888"), "IPV6 1111:2222:::4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_297() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:::5555:6666:7777:8888"), "IPV6 1111:2222:3333:::5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_298() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:::6666:7777:8888"), "IPV6 1111:2222:3333:4444:::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_299() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:::7777:8888"), "IPV6 1111:2222:3333:4444:5555:::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_300() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:::8888"), "IPV6 1111:2222:3333:4444:5555:6666:::8888 should be invalid");
    }

    @Test
    public void testIPv6_301() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:::"), "IPV6 1111:2222:3333:4444:5555:6666:7777::: should be invalid");
    }

    @Test
    public void testIPv6_302() {
        assertFalse(validator.isValidInet6Address("::2222::4444:5555:6666:7777:8888"), "IPV6 ::2222::4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_303() {
        assertFalse(validator.isValidInet6Address("::2222:3333::5555:6666:7777:8888"), "IPV6 ::2222:3333::5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_304() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444::6666:7777:8888"), "IPV6 ::2222:3333:4444::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_305() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444:5555::7777:8888"), "IPV6 ::2222:3333:4444:5555::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_306() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444:5555:7777::8888"), "IPV6 ::2222:3333:4444:5555:7777::8888 should be invalid");
    }

    @Test
    public void testIPv6_307() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444:5555:7777:8888::"), "IPV6 ::2222:3333:4444:5555:7777:8888:: should be invalid");
    }

    @Test
    public void testIPv6_308() {
        assertFalse(validator.isValidInet6Address("1111::3333::5555:6666:7777:8888"), "IPV6 1111::3333::5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_309() {
        assertFalse(validator.isValidInet6Address("1111::3333:4444::6666:7777:8888"), "IPV6 1111::3333:4444::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_310() {
        assertFalse(validator.isValidInet6Address("1111::3333:4444:5555::7777:8888"), "IPV6 1111::3333:4444:5555::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_311() {
        assertFalse(validator.isValidInet6Address("1111::3333:4444:5555:6666::8888"), "IPV6 1111::3333:4444:5555:6666::8888 should be invalid");
    }

    @Test
    public void testIPv6_312() {
        assertFalse(validator.isValidInet6Address("1111::3333:4444:5555:6666:7777::"), "IPV6 1111::3333:4444:5555:6666:7777:: should be invalid");
    }

    @Test
    public void testIPv6_313() {
        assertFalse(validator.isValidInet6Address("1111:2222::4444::6666:7777:8888"), "IPV6 1111:2222::4444::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_314() {
        assertFalse(validator.isValidInet6Address("1111:2222::4444:5555::7777:8888"), "IPV6 1111:2222::4444:5555::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_315() {
        assertFalse(validator.isValidInet6Address("1111:2222::4444:5555:6666::8888"), "IPV6 1111:2222::4444:5555:6666::8888 should be invalid");
    }

    @Test
    public void testIPv6_316() {
        assertFalse(validator.isValidInet6Address("1111:2222::4444:5555:6666:7777::"), "IPV6 1111:2222::4444:5555:6666:7777:: should be invalid");
    }

    @Test
    public void testIPv6_317() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::5555::7777:8888"), "IPV6 1111:2222:3333::5555::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_318() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::5555:6666::8888"), "IPV6 1111:2222:3333::5555:6666::8888 should be invalid");
    }

    @Test
    public void testIPv6_319() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::5555:6666:7777::"), "IPV6 1111:2222:3333::5555:6666:7777:: should be invalid");
    }

    @Test
    public void testIPv6_320() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444::6666::8888"), "IPV6 1111:2222:3333:4444::6666::8888 should be invalid");
    }

    @Test
    public void testIPv6_321() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444::6666:7777::"), "IPV6 1111:2222:3333:4444::6666:7777:: should be invalid");
    }

    @Test
    public void testIPv6_322() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555::7777::"), "IPV6 1111:2222:3333:4444:5555::7777:: should be invalid");
    }

    @Test
    public void testIPv6_323() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:8888:1.2.3.4"), "IPV6 1111:2222:3333:4444:5555:6666:7777:8888:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_324() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:1.2.3.4"), "IPV6 1111:2222:3333:4444:5555:6666:7777:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_325() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666::1.2.3.4"), "IPV6 1111:2222:3333:4444:5555:6666::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_326() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444:5555:6666:7777:1.2.3.4"), "IPV6 ::2222:3333:4444:5555:6666:7777:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_327() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:1.2.3.4.5"), "IPV6 1111:2222:3333:4444:5555:6666:1.2.3.4.5 should be invalid");
    }

    @Test
    public void testIPv6_328() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:1.2.3.4"), "IPV6 1111:2222:3333:4444:5555:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_329() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:1.2.3.4"), "IPV6 1111:2222:3333:4444:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_330() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:1.2.3.4"), "IPV6 1111:2222:3333:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_331() {
        assertFalse(validator.isValidInet6Address("1111:2222:1.2.3.4"), "IPV6 1111:2222:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_332() {
        assertFalse(validator.isValidInet6Address("1111:1.2.3.4"), "IPV6 1111:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_333() {
        assertFalse(validator.isValidInet6Address("1.2.3.4"), "IPV6 1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_334() {
        assertFalse(validator.isValidInet6Address("11112222:3333:4444:5555:6666:1.2.3.4"), "IPV6 11112222:3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_335() {
        assertFalse(validator.isValidInet6Address("1111:22223333:4444:5555:6666:1.2.3.4"), "IPV6 1111:22223333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_336() {
        assertFalse(validator.isValidInet6Address("1111:2222:33334444:5555:6666:1.2.3.4"), "IPV6 1111:2222:33334444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_337() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:44445555:6666:1.2.3.4"), "IPV6 1111:2222:3333:44445555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_338() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:55556666:1.2.3.4"), "IPV6 1111:2222:3333:4444:55556666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_339() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:66661.2.3.4"), "IPV6 1111:2222:3333:4444:5555:66661.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_340() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:255255.255.255"), "IPV6 1111:2222:3333:4444:5555:6666:255255.255.255 should be invalid");
    }

    @Test
    public void testIPv6_341() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:255.255255.255"), "IPV6 1111:2222:3333:4444:5555:6666:255.255255.255 should be invalid");
    }

    @Test
    public void testIPv6_342() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:255.255.255255"), "IPV6 1111:2222:3333:4444:5555:6666:255.255.255255 should be invalid");
    }

    @Test
    public void testIPv6_343() {
        assertFalse(validator.isValidInet6Address(":1.2.3.4"), "IPV6 :1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_344() {
        assertFalse(validator.isValidInet6Address(":6666:1.2.3.4"), "IPV6 :6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_345() {
        assertFalse(validator.isValidInet6Address(":5555:6666:1.2.3.4"), "IPV6 :5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_346() {
        assertFalse(validator.isValidInet6Address(":4444:5555:6666:1.2.3.4"), "IPV6 :4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_347() {
        assertFalse(validator.isValidInet6Address(":3333:4444:5555:6666:1.2.3.4"), "IPV6 :3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_348() {
        assertFalse(validator.isValidInet6Address(":2222:3333:4444:5555:6666:1.2.3.4"), "IPV6 :2222:3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_349() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555:6666:1.2.3.4"), "IPV6 :1111:2222:3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_350() {
        assertFalse(validator.isValidInet6Address(":::2222:3333:4444:5555:6666:1.2.3.4"), "IPV6 :::2222:3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_351() {
        assertFalse(validator.isValidInet6Address("1111:::3333:4444:5555:6666:1.2.3.4"), "IPV6 1111:::3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_352() {
        assertFalse(validator.isValidInet6Address("1111:2222:::4444:5555:6666:1.2.3.4"), "IPV6 1111:2222:::4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_353() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:::5555:6666:1.2.3.4"), "IPV6 1111:2222:3333:::5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_354() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:::6666:1.2.3.4"), "IPV6 1111:2222:3333:4444:::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_355() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:::1.2.3.4"), "IPV6 1111:2222:3333:4444:5555:::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_356() {
        assertFalse(validator.isValidInet6Address("::2222::4444:5555:6666:1.2.3.4"), "IPV6 ::2222::4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_357() {
        assertFalse(validator.isValidInet6Address("::2222:3333::5555:6666:1.2.3.4"), "IPV6 ::2222:3333::5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_358() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444::6666:1.2.3.4"), "IPV6 ::2222:3333:4444::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_359() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444:5555::1.2.3.4"), "IPV6 ::2222:3333:4444:5555::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_360() {
        assertFalse(validator.isValidInet6Address("1111::3333::5555:6666:1.2.3.4"), "IPV6 1111::3333::5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_361() {
        assertFalse(validator.isValidInet6Address("1111::3333:4444::6666:1.2.3.4"), "IPV6 1111::3333:4444::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_362() {
        assertFalse(validator.isValidInet6Address("1111::3333:4444:5555::1.2.3.4"), "IPV6 1111::3333:4444:5555::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_363() {
        assertFalse(validator.isValidInet6Address("1111:2222::4444::6666:1.2.3.4"), "IPV6 1111:2222::4444::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_364() {
        assertFalse(validator.isValidInet6Address("1111:2222::4444:5555::1.2.3.4"), "IPV6 1111:2222::4444:5555::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_365() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::5555::1.2.3.4"), "IPV6 1111:2222:3333::5555::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_366() {
        assertFalse(validator.isValidInet6Address("::."), "IPV6 ::. should be invalid");
    }

    @Test
    public void testIPv6_367() {
        assertFalse(validator.isValidInet6Address("::.."), "IPV6 ::.. should be invalid");
    }

    @Test
    public void testIPv6_368() {
        assertFalse(validator.isValidInet6Address("::..."), "IPV6 ::... should be invalid");
    }

    @Test
    public void testIPv6_369() {
        assertFalse(validator.isValidInet6Address("::1..."), "IPV6 ::1... should be invalid");
    }

    @Test
    public void testIPv6_370() {
        assertFalse(validator.isValidInet6Address("::1.2.."), "IPV6 ::1.2.. should be invalid");
    }

    @Test
    public void testIPv6_371() {
        assertFalse(validator.isValidInet6Address("::1.2.3."), "IPV6 ::1.2.3. should be invalid");
    }

    @Test
    public void testIPv6_372() {
        assertFalse(validator.isValidInet6Address("::.2.."), "IPV6 ::.2.. should be invalid");
    }

    @Test
    public void testIPv6_373() {
        assertFalse(validator.isValidInet6Address("::.2.3."), "IPV6 ::.2.3. should be invalid");
    }

    @Test
    public void testIPv6_374() {
        assertFalse(validator.isValidInet6Address("::.2.3.4"), "IPV6 ::.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_375() {
        assertFalse(validator.isValidInet6Address("::..3."), "IPV6 ::..3. should be invalid");
    }

    @Test
    public void testIPv6_376() {
        assertFalse(validator.isValidInet6Address("::..3.4"), "IPV6 ::..3.4 should be invalid");
    }

    @Test
    public void testIPv6_377() {
        assertFalse(validator.isValidInet6Address("::...4"), "IPV6 ::...4 should be invalid");
    }

    @Test
    public void testIPv6_378() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555:6666:7777::"), "IPV6 :1111:2222:3333:4444:5555:6666:7777:: should be invalid");
    }

    @Test
    public void testIPv6_379() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555:6666::"), "IPV6 :1111:2222:3333:4444:5555:6666:: should be invalid");
    }

    @Test
    public void testIPv6_380() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555::"), "IPV6 :1111:2222:3333:4444:5555:: should be invalid");
    }

    @Test
    public void testIPv6_381() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444::"), "IPV6 :1111:2222:3333:4444:: should be invalid");
    }

    @Test
    public void testIPv6_382() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::"), "IPV6 :1111:2222:3333:: should be invalid");
    }

    @Test
    public void testIPv6_383() {
        assertFalse(validator.isValidInet6Address(":1111:2222::"), "IPV6 :1111:2222:: should be invalid");
    }

    @Test
    public void testIPv6_384() {
        assertFalse(validator.isValidInet6Address(":1111::"), "IPV6 :1111:: should be invalid");
    }

    @Test
    public void testIPv6_385() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555:6666::8888"), "IPV6 :1111:2222:3333:4444:5555:6666::8888 should be invalid");
    }

    @Test
    public void testIPv6_386() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555::8888"), "IPV6 :1111:2222:3333:4444:5555::8888 should be invalid");
    }

    @Test
    public void testIPv6_387() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444::8888"), "IPV6 :1111:2222:3333:4444::8888 should be invalid");
    }

    @Test
    public void testIPv6_388() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::8888"), "IPV6 :1111:2222:3333::8888 should be invalid");
    }

    @Test
    public void testIPv6_389() {
        assertFalse(validator.isValidInet6Address(":1111:2222::8888"), "IPV6 :1111:2222::8888 should be invalid");
    }

    @Test
    public void testIPv6_390() {
        assertFalse(validator.isValidInet6Address(":1111::8888"), "IPV6 :1111::8888 should be invalid");
    }

    @Test
    public void testIPv6_391() {
        assertFalse(validator.isValidInet6Address(":::8888"), "IPV6 :::8888 should be invalid");
    }

    @Test
    public void testIPv6_392() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555::7777:8888"), "IPV6 :1111:2222:3333:4444:5555::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_393() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444::7777:8888"), "IPV6 :1111:2222:3333:4444::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_394() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::7777:8888"), "IPV6 :1111:2222:3333::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_395() {
        assertFalse(validator.isValidInet6Address(":1111:2222::7777:8888"), "IPV6 :1111:2222::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_396() {
        assertFalse(validator.isValidInet6Address(":1111::7777:8888"), "IPV6 :1111::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_397() {
        assertFalse(validator.isValidInet6Address(":::7777:8888"), "IPV6 :::7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_398() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444::6666:7777:8888"), "IPV6 :1111:2222:3333:4444::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_399() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::6666:7777:8888"), "IPV6 :1111:2222:3333::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_400() {
        assertFalse(validator.isValidInet6Address(":1111:2222::6666:7777:8888"), "IPV6 :1111:2222::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_401() {
        assertFalse(validator.isValidInet6Address(":1111::6666:7777:8888"), "IPV6 :1111::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_402() {
        assertFalse(validator.isValidInet6Address(":::6666:7777:8888"), "IPV6 :::6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_403() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::5555:6666:7777:8888"), "IPV6 :1111:2222:3333::5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_404() {
        assertFalse(validator.isValidInet6Address(":1111:2222::5555:6666:7777:8888"), "IPV6 :1111:2222::5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_405() {
        assertFalse(validator.isValidInet6Address(":1111::5555:6666:7777:8888"), "IPV6 :1111::5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_406() {
        assertFalse(validator.isValidInet6Address(":::5555:6666:7777:8888"), "IPV6 :::5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_407() {
        assertFalse(validator.isValidInet6Address(":1111:2222::4444:5555:6666:7777:8888"), "IPV6 :1111:2222::4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_408() {
        assertFalse(validator.isValidInet6Address(":1111::4444:5555:6666:7777:8888"), "IPV6 :1111::4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_409() {
        assertFalse(validator.isValidInet6Address(":::4444:5555:6666:7777:8888"), "IPV6 :::4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_410() {
        assertFalse(validator.isValidInet6Address(":1111::3333:4444:5555:6666:7777:8888"), "IPV6 :1111::3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_411() {
        assertFalse(validator.isValidInet6Address(":::3333:4444:5555:6666:7777:8888"), "IPV6 :::3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_412() {
        assertFalse(validator.isValidInet6Address(":::2222:3333:4444:5555:6666:7777:8888"), "IPV6 :::2222:3333:4444:5555:6666:7777:8888 should be invalid");
    }

    @Test
    public void testIPv6_413() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555:6666:1.2.3.4"), "IPV6 :1111:2222:3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_414() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444:5555::1.2.3.4"), "IPV6 :1111:2222:3333:4444:5555::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_415() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444::1.2.3.4"), "IPV6 :1111:2222:3333:4444::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_416() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::1.2.3.4"), "IPV6 :1111:2222:3333::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_417() {
        assertFalse(validator.isValidInet6Address(":1111:2222::1.2.3.4"), "IPV6 :1111:2222::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_418() {
        assertFalse(validator.isValidInet6Address(":1111::1.2.3.4"), "IPV6 :1111::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_419() {
        assertFalse(validator.isValidInet6Address(":::1.2.3.4"), "IPV6 :::1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_420() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333:4444::6666:1.2.3.4"), "IPV6 :1111:2222:3333:4444::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_421() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::6666:1.2.3.4"), "IPV6 :1111:2222:3333::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_422() {
        assertFalse(validator.isValidInet6Address(":1111:2222::6666:1.2.3.4"), "IPV6 :1111:2222::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_423() {
        assertFalse(validator.isValidInet6Address(":1111::6666:1.2.3.4"), "IPV6 :1111::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_424() {
        assertFalse(validator.isValidInet6Address(":::6666:1.2.3.4"), "IPV6 :::6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_425() {
        assertFalse(validator.isValidInet6Address(":1111:2222:3333::5555:6666:1.2.3.4"), "IPV6 :1111:2222:3333::5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_426() {
        assertFalse(validator.isValidInet6Address(":1111:2222::5555:6666:1.2.3.4"), "IPV6 :1111:2222::5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_427() {
        assertFalse(validator.isValidInet6Address(":1111::5555:6666:1.2.3.4"), "IPV6 :1111::5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_428() {
        assertFalse(validator.isValidInet6Address(":::5555:6666:1.2.3.4"), "IPV6 :::5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_429() {
        assertFalse(validator.isValidInet6Address(":1111:2222::4444:5555:6666:1.2.3.4"), "IPV6 :1111:2222::4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_430() {
        assertFalse(validator.isValidInet6Address(":1111::4444:5555:6666:1.2.3.4"), "IPV6 :1111::4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_431() {
        assertFalse(validator.isValidInet6Address(":::4444:5555:6666:1.2.3.4"), "IPV6 :::4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_432() {
        assertFalse(validator.isValidInet6Address(":1111::3333:4444:5555:6666:1.2.3.4"), "IPV6 :1111::3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_433() {
        assertFalse(validator.isValidInet6Address(":::2222:3333:4444:5555:6666:1.2.3.4"), "IPV6 :::2222:3333:4444:5555:6666:1.2.3.4 should be invalid");
    }

    @Test
    public void testIPv6_434() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:7777:::"), "IPV6 1111:2222:3333:4444:5555:6666:7777::: should be invalid");
    }

    @Test
    public void testIPv6_435() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666:::"), "IPV6 1111:2222:3333:4444:5555:6666::: should be invalid");
    }

    @Test
    public void testIPv6_436() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:::"), "IPV6 1111:2222:3333:4444:5555::: should be invalid");
    }

    @Test
    public void testIPv6_437() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:::"), "IPV6 1111:2222:3333:4444::: should be invalid");
    }

    @Test
    public void testIPv6_438() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:::"), "IPV6 1111:2222:3333::: should be invalid");
    }

    @Test
    public void testIPv6_439() {
        assertFalse(validator.isValidInet6Address("1111:2222:::"), "IPV6 1111:2222::: should be invalid");
    }

    @Test
    public void testIPv6_440() {
        assertFalse(validator.isValidInet6Address("1111:::"), "IPV6 1111::: should be invalid");
    }

    @Test
    public void testIPv6_441() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555:6666::8888:"), "IPV6 1111:2222:3333:4444:5555:6666::8888: should be invalid");
    }

    @Test
    public void testIPv6_442() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555::8888:"), "IPV6 1111:2222:3333:4444:5555::8888: should be invalid");
    }

    @Test
    public void testIPv6_443() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444::8888:"), "IPV6 1111:2222:3333:4444::8888: should be invalid");
    }

    @Test
    public void testIPv6_444() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::8888:"), "IPV6 1111:2222:3333::8888: should be invalid");
    }

    @Test
    public void testIPv6_445() {
        assertFalse(validator.isValidInet6Address("1111:2222::8888:"), "IPV6 1111:2222::8888: should be invalid");
    }

    @Test
    public void testIPv6_446() {
        assertFalse(validator.isValidInet6Address("1111::8888:"), "IPV6 1111::8888: should be invalid");
    }

    @Test
    public void testIPv6_447() {
        assertFalse(validator.isValidInet6Address("::8888:"), "IPV6 ::8888: should be invalid");
    }

    @Test
    public void testIPv6_448() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444:5555::7777:8888:"), "IPV6 1111:2222:3333:4444:5555::7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_449() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444::7777:8888:"), "IPV6 1111:2222:3333:4444::7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_450() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::7777:8888:"), "IPV6 1111:2222:3333::7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_451() {
        assertFalse(validator.isValidInet6Address("1111:2222::7777:8888:"), "IPV6 1111:2222::7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_452() {
        assertFalse(validator.isValidInet6Address("1111::7777:8888:"), "IPV6 1111::7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_453() {
        assertFalse(validator.isValidInet6Address("::7777:8888:"), "IPV6 ::7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_454() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333:4444::6666:7777:8888:"), "IPV6 1111:2222:3333:4444::6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_455() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::6666:7777:8888:"), "IPV6 1111:2222:3333::6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_456() {
        assertFalse(validator.isValidInet6Address("1111:2222::6666:7777:8888:"), "IPV6 1111:2222::6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_457() {
        assertFalse(validator.isValidInet6Address("1111::6666:7777:8888:"), "IPV6 1111::6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_458() {
        assertFalse(validator.isValidInet6Address("::6666:7777:8888:"), "IPV6 ::6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_459() {
        assertFalse(validator.isValidInet6Address("1111:2222:3333::5555:6666:7777:8888:"), "IPV6 1111:2222:3333::5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_460() {
        assertFalse(validator.isValidInet6Address("1111:2222::5555:6666:7777:8888:"), "IPV6 1111:2222::5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_461() {
        assertFalse(validator.isValidInet6Address("1111::5555:6666:7777:8888:"), "IPV6 1111::5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_462() {
        assertFalse(validator.isValidInet6Address("::5555:6666:7777:8888:"), "IPV6 ::5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_463() {
        assertFalse(validator.isValidInet6Address("1111:2222::4444:5555:6666:7777:8888:"), "IPV6 1111:2222::4444:5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_464() {
        assertFalse(validator.isValidInet6Address("1111::4444:5555:6666:7777:8888:"), "IPV6 1111::4444:5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_465() {
        assertFalse(validator.isValidInet6Address("::4444:5555:6666:7777:8888:"), "IPV6 ::4444:5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_466() {
        assertFalse(validator.isValidInet6Address("1111::3333:4444:5555:6666:7777:8888:"), "IPV6 1111::3333:4444:5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_467() {
        assertFalse(validator.isValidInet6Address("::3333:4444:5555:6666:7777:8888:"), "IPV6 ::3333:4444:5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_468() {
        assertFalse(validator.isValidInet6Address("::2222:3333:4444:5555:6666:7777:8888:"), "IPV6 ::2222:3333:4444:5555:6666:7777:8888: should be invalid");
    }

    @Test
    public void testIPv6_469() {
        assertTrue(validator.isValidInet6Address("0:a:b:c:d:e:f::"), "IPV6 0:a:b:c:d:e:f:: should be valid");
    }

    @Test
    public void testIPv6_470() {
        assertTrue(validator.isValidInet6Address("::0:a:b:c:d:e:f"), "IPV6 ::0:a:b:c:d:e:f should be valid");
    }

    @Test
    public void testIPv6_471() {
        assertTrue(validator.isValidInet6Address("a:b:c:d:e:f:0::"), "IPV6 a:b:c:d:e:f:0:: should be valid");
    }

    @Test
    public void testIPv6_472() {
        assertFalse(validator.isValidInet6Address("':10.0.0.1"), "IPV6 ':10.0.0.1 should be invalid");
    }

    @Test
    public void testReservedInetAddresses_1() {
        assertTrue(validator.isValid("127.0.0.1"), "localhost IP should be valid");
    }

    @Test
    public void testReservedInetAddresses_2() {
        assertTrue(validator.isValid("255.255.255.255"), "broadcast IP should be valid");
    }
}
