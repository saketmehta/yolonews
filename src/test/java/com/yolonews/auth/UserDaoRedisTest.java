package com.yolonews.auth;

import com.yolonews.common.JedisProviderTest;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author saket.mehta
 */
class UserDaoRedisTest {
    private UserDaoRedis dao;

    @BeforeEach
    void setup() {
        dao = new UserDaoRedis(new JedisProviderTest());
    }

    @Nested
    class FindByUsername {
        private User user;

        @BeforeEach
        void setup() {
            user = new User();
            user.setId(2);
            user.setUsername("test_user");
            dao.save(user);
        }

        @AfterEach
        void teardown() {
            dao.delete(user.getId());
        }

        @Test
        @DisplayName("when invalid input")
        void findByUsername_invalidInput() {
            assertThrows(NullPointerException.class, () -> dao.findByUsername(null));
        }

        @Test
        @DisplayName("when user is in db")
        void findByUsername_inDb() {
            Optional<User> user = dao.findByUsername(this.user.getUsername());
            assertTrue(user.isPresent());
            assertEquals(this.user.getId(), user.get().getId());
            assertEquals(this.user.getUsername(), user.get().getUsername());
        }

        @Test
        @DisplayName("when user is not in db")
        void findByUsername_notInDb() {
            Optional<User> user = dao.findByUsername("not_in_db");
            assertTrue(!user.isPresent());
        }
    }

    @Nested
    class Save {
        @Test
        @DisplayName("when invalid input")
        void save_invalidInput() {
            assertThrows(NullPointerException.class, () -> dao.save(null));
        }

        @Test
        @DisplayName("when user object does not have an id")
        void save_newUser() {
            User user = new User();
            user.setUsername("test_user");

            Long userId = dao.save(user);
            assertNotNull(user);
            Optional<User> fromDb = dao.findById(userId);
            assertTrue(fromDb.isPresent());
            assertEquals((long) userId, fromDb.get().getId());

            dao.delete(userId);
        }

        @Test
        @DisplayName("when user object has an id")
        void save_updateUser() {
            dao.delete(1L);
            User user = new User();
            user.setId(1L);
            user.setUsername("test_user");

            Long userId = dao.save(user);
            assertNotNull(user);
            assertEquals(1L, (long) userId);
            Optional<User> fromDb = dao.findById(userId);
            assertTrue(fromDb.isPresent());
            assertEquals((long) userId, fromDb.get().getId());

            dao.delete(1L);
        }
    }

    @Nested
    class FindById {
        private User user;

        @BeforeEach
        void setup() {
            user = new User();
            user.setId(1);
            user.setUsername("test_user");
            dao.save(user);
        }

        @AfterEach
        void teardown() {
            if (user != null) {
                dao.delete(user.getId());
            }
        }

        @Test
        @DisplayName("when invalid input")
        void findById_invalidInput() {
            assertThrows(NullPointerException.class, () -> dao.findById(null));
        }

        @Test
        @DisplayName("when user is in db")
        void findById_userInDb() {
            Optional<User> user = dao.findById(this.user.getId());
            assertTrue(user.isPresent());
            assertEquals(this.user.getId(), user.get().getId());
            assertEquals(this.user.getUsername(), user.get().getUsername());
        }

        @Test
        @DisplayName("when user is not in db")
        void findById_userNotInDb() {
            Optional<User> user = dao.findById(23L);
            assertTrue(!user.isPresent());
        }
    }

    @Nested
    class Delete {
        @Test
        @DisplayName("when invalid input")
        void delete_invalidInput() {
            assertThrows(NullPointerException.class, () -> dao.delete(null));
        }

        @Test
        void delete_validInput() {
            User user = new User();
            user.setId(1);
            user.setUsername("test_user");
            dao.save(user);

            dao.delete(1L);
            Optional<User> byId = dao.findById(user.getId());
            if (byId.isPresent()) {
                assertEquals(user.getId(), byId.get().getId());
                assertNull(byId.get().getUsername());
            }
            Optional<User> byUsername = dao.findByUsername(user.getUsername());
            assertTrue(!byUsername.isPresent());
        }
    }
}