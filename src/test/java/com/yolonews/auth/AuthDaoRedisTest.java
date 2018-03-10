package com.yolonews.auth;

import com.yolonews.common.JedisProviderTest;
import org.junit.jupiter.api.*;

import java.util.OptionalLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author saket.mehta
 */
class AuthDaoRedisTest {
    private AuthDaoRedis dao;

    @BeforeEach
    void setup() {
        dao = new AuthDaoRedis(new JedisProviderTest());
    }

    @Nested
    class FindByToken {
        @BeforeEach
        void setup() {
            dao.insertToken(1L, "in_db");
        }

        @AfterEach
        void teardown() {
            dao.deleteToken("in_db");
        }

        @Test
        @DisplayName("token is null")
        void isNull() {
            assertThrows(NullPointerException.class, () -> dao.findUserByToken(null));
        }

        @Test
        @DisplayName("token does not exist in db")
        void isNotInDb() {
            OptionalLong user = dao.findUserByToken("not_in_db");
            assertTrue(!user.isPresent());
        }

        @Test
        @DisplayName("token exists in db")
        void isInDb() {
            OptionalLong user = dao.findUserByToken("in_db");
            assertTrue(user.isPresent());
            long userId = user.getAsLong();
            assertEquals(1L, userId);
        }
    }

    @Nested
    class InsertToken {
        @Test
        @DisplayName("invalid inputs")
        void tokenIsNull() {
            assertAll(
                    () -> assertThrows(NullPointerException.class, () -> dao.insertToken(1L, null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> dao.insertToken(0L, "token"))
            );
        }

        @Test
        @DisplayName("valid inputs")
        void validInput() {
            String token = "test_token";
            dao.insertToken(1L, token);
            assertEquals(1L, dao.findUserByToken(token).orElse(0));
            dao.deleteToken(token);
        }
    }

    @Nested
    class DeleteToken {
        private String token = "token";

        @BeforeEach
        void setup() {
            dao.insertToken(1L, token);
        }

        @AfterEach
        void teardown() {
            dao.deleteToken(token);
        }

        @Test
        @DisplayName("token is null")
        void tokenIsNull() {
            assertThrows(NullPointerException.class, () -> dao.deleteToken(null));
        }

        @Test
        @DisplayName("token not null")
        void tokenNotNull() {
            assertTrue(dao.findUserByToken(token).isPresent());
            dao.deleteToken(token);
            assertTrue(!dao.findUserByToken(token).isPresent());
        }
    }
}