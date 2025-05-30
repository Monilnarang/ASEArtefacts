package org.jivesoftware.openfire.auth;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class JDBCAuthProviderTest_Purified {

    private static final String PASSWORD = "password";

    private static final String MD5_SHA1_PASSWORD = "55c3b5386c486feb662a0785f340938f518d547f";

    private static final String MD5_SHA512_PASSWORD = "85ec0898f0998c95a023f18f1123cbc77ba51f2632137b61999655d59817d942ecef3012762604e442d395a194c53e94e9fb5bb8fe74d61900eb05cb0c078bb6";

    private static final String MD5_PASSWORD = "5f4dcc3b5aa765d61d8327deb882cf99";

    private static final String SHA1_PASSWORD = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";

    private static final String SHA256_PASSWORD = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

    private static final String SHA512_PASSWORD = "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86";

    private static final String BCRYPTED_PASSWORD = "$2a$10$TS9mWNnHbTU.ukLUlrOopuGooirFR3IltqgRFcyM.iSPQuoPDAafG";

    private final JDBCAuthProvider jdbcAuthProvider = new JDBCAuthProvider();

    private void setPasswordTypes(final String passwordTypes) {
        jdbcAuthProvider.propertySet("jdbcAuthProvider.passwordType", new HashMap<>() {

            {
                put("value", passwordTypes);
            }
        });
    }

    @Test
    public void hashPassword_1() throws Exception {
        assertEquals(MD5_PASSWORD, jdbcAuthProvider.hashPassword(PASSWORD, JDBCAuthProvider.PasswordType.md5));
    }

    @Test
    public void hashPassword_2() throws Exception {
        assertEquals(SHA1_PASSWORD, jdbcAuthProvider.hashPassword(PASSWORD, JDBCAuthProvider.PasswordType.sha1));
    }

    @Test
    public void hashPassword_3() throws Exception {
        assertEquals(SHA256_PASSWORD, jdbcAuthProvider.hashPassword(PASSWORD, JDBCAuthProvider.PasswordType.sha256));
    }

    @Test
    public void hashPassword_4() throws Exception {
        assertEquals(SHA512_PASSWORD, jdbcAuthProvider.hashPassword(PASSWORD, JDBCAuthProvider.PasswordType.sha512));
    }

    @Test
    public void hashPassword_5() throws Exception {
        assertNotEquals(BCRYPTED_PASSWORD, jdbcAuthProvider.hashPassword(PASSWORD, JDBCAuthProvider.PasswordType.bcrypt));
    }

    @Test
    public void hashPassword_6() throws Exception {
        assertTrue(OpenBSDBCrypt.checkPassword(BCRYPTED_PASSWORD, PASSWORD.toCharArray()));
    }
}
