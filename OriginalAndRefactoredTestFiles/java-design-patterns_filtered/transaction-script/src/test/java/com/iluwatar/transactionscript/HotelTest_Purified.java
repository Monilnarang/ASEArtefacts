package com.iluwatar.transactionscript;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelTest_Purified {

    private static final String H2_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    private Hotel hotel;

    private HotelDaoImpl dao;

    @BeforeEach
    void setUp() throws Exception {
        final var dataSource = createDataSource();
        deleteSchema(dataSource);
        createSchema(dataSource);
        dao = new HotelDaoImpl(dataSource);
        addRooms(dao);
        hotel = new Hotel(dao);
    }

    private static void deleteSchema(DataSource dataSource) throws java.sql.SQLException {
        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()) {
            statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL);
        }
    }

    private static void createSchema(DataSource dataSource) throws Exception {
        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()) {
            statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(H2_DB_URL);
        return dataSource;
    }

    private static void addRooms(HotelDaoImpl hotelDao) throws Exception {
        for (var room : generateSampleRooms()) {
            hotelDao.add(room);
        }
    }

    public static List<Room> generateSampleRooms() {
        final var room1 = new Room(1, "Single", 50, false);
        final var room2 = new Room(2, "Double", 80, false);
        final var room3 = new Room(3, "Queen", 120, false);
        final var room4 = new Room(4, "King", 150, false);
        final var room5 = new Room(5, "Single", 50, false);
        final var room6 = new Room(6, "Double", 80, false);
        return List.of(room1, room2, room3, room4, room5, room6);
    }

    private int getNonExistingRoomId() {
        return 999;
    }

    @Test
    void NotBookingRoomShouldNotChangeBookedStatus_1() throws Exception {
        assertTrue(dao.getById(1).isPresent());
    }

    @Test
    void NotBookingRoomShouldNotChangeBookedStatus_2() throws Exception {
        assertFalse(dao.getById(1).get().isBooked());
    }
}
