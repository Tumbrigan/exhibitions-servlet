package com.kucher.dao;

import com.kucher.model.Exhibition;
import com.kucher.model.User;
import com.kucher.util.QueryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

public class DBManager {
    private static final Logger LOGGER = LogManager.getLogger(DBManager.class.getName());

    private static DBManager dbManager;

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public User getUserByEmail(String email) {
        User user = new User();
        String sqlQuery = QueryManager.getQuery("getUserByEmail");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, email);
            statement.execute();
            ResultSet set = statement.getResultSet();
            if (set.next()) {
                user.setId(set.getInt("id"));
                user.setEmail(set.getString("email"));
                user.setPassword(set.getString("password"));
                String role = set.getString("role");
                user.setRole(User.ROLE.valueOf(role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = QueryManager.getQuery("getAllUsers");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
            ResultSet set = statement.getResultSet();
            while (set.next()) {
                User user = new User();
                user.setId(set.getInt("id"));
                user.setEmail(set.getString("email"));
                user.setPassword(set.getString("password"));
                String role = set.getString("role");
                user.setRole(User.ROLE.valueOf(role));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Exhibition.Category> getCategoryList() {
        List<Exhibition.Category> listCategory = new ArrayList<>();
        String query = QueryManager.getQuery("getCategoryList");

        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Exhibition.Category category = new Exhibition.Category(id, name);

                listCategory.add(category);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
//            throw ex;
        }
        return listCategory;
    }

    public int getMaxSeatAmount() {
        String query = QueryManager.getQuery("getMaxSeatAmount");
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            result.next();
            return result.getInt("max");
        } catch (SQLException ex) {
            ex.printStackTrace();
//            throw ex;
        }
        return -666;
    }

    public List<Exhibition> getAllExhibitions() {
        List<Exhibition> exhibitions = new ArrayList<>();
        String query = QueryManager.getQuery("getAllExhibitions");
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {
            while (result.next()) {
                Exhibition exhibition = new Exhibition();
                fillExhibition(exhibition, connection, result);
                exhibitions.add(exhibition);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
//            throw ex;
        }
        return exhibitions;
    }

    private void fillExhibition(Exhibition exhibition, Connection connection, ResultSet result)
            throws SQLException {
        int id = result.getInt("id");
        int categoryID = result.getInt("category_id");
        String topic = result.getString("topic");
        Date startDate = result.getDate("start_date");
        Date endDate = result.getDate("end_date");
        Time startTime = result.getTime("start_time");
        Time endTime = result.getTime("end_time");
        int price = result.getInt("price");
        int capacity = result.getInt("capacity");
        int remainingSeats = result.getInt("remaining_seats");

        String statusStr = result.getString("status");
        Exhibition.STATUS status = Exhibition.STATUS.valueOf(statusStr);

        exhibition.setId(id);

        exhibition.setCategory(getCategoryById(categoryID, connection));

        exhibition.setTopic(topic);
        exhibition.setStartDate(startDate);
        exhibition.setEndDate(endDate);
        exhibition.setStartTime(startTime);
        exhibition.setEndTime(endTime);
        exhibition.setPrice(price);
        exhibition.setCapacity(capacity);
        exhibition.setRemainingSeats(remainingSeats);
        exhibition.setStatus(status);

        List<Exhibition.Hall> hallList = getAllHallsByExhibitionId(exhibition.getId(), connection);
        exhibition.setHallList(hallList);
    }

    private List<Exhibition.Hall> getAllHallsByExhibitionId(int exhibitionID, Connection connection) {
        List<Exhibition.Hall> hallList = new ArrayList<>();
        String query = QueryManager.getQuery("getAllHallsByExhibitionId");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, exhibitionID);
            try (ResultSet resultSet = statement.executeQuery()) {
                getAllHalls(hallList, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hallList;
    }

    private Exhibition.Category getCategoryById(int categoryID, Connection connection) {
        Exhibition.Category category = new Exhibition.Category();

        String query = QueryManager.getQuery("getCategoryById");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryID);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public boolean isEmailPresent(String email) {
        String query = QueryManager.getQuery("isEmailPresent");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createUser(String email, String hash) {
        String query = QueryManager.getQuery("insertUser");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, hash);
            int row = statement.executeUpdate();
            if (row != 1) throw new SQLException("User is not created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExhibition(Exhibition exhibition, String[] hallID) {
        String query = QueryManager.getQuery("insertExhibition");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, exhibition.getCategory().getId());
            statement.setString(2, exhibition.getTopic());
            statement.setString(3, exhibition.getStartDate());
            statement.setString(4, exhibition.getEndDate());
            statement.setString(5, exhibition.getStartTime());
            statement.setString(6, exhibition.getEndTime());
            statement.setInt(7, exhibition.getPrice());
            statement.setInt(8, exhibition.getCapacity());
            statement.setInt(9, exhibition.getRemainingSeats());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    exhibition.setId(id);
                }
            }

            fillHallsForExhibition(exhibition, hallID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillHallsForExhibition(Exhibition exhibition, String[] hallIDs) {
        String query = QueryManager.getQuery("fillHallsForExhibition");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (String hallID : hallIDs) {
                statement.setInt(1, exhibition.getId());
                statement.setString(2, hallID);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExhibitionById(int id) {
        String query = QueryManager.getQuery("deleteExhibition");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int row = statement.executeUpdate();
            if (row != 1) throw new SQLException("Exhibition is not deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getOccupiedHallMap(Exhibition exhibition, String[] hallsID) {

        Map<String, Integer> hallNameExhIdMap = new HashMap<>();

        String startDate = exhibition.getStartDate();
        String endDate = exhibition.getEndDate();
        String startTime = exhibition.getStartTime();
        String endTime = exhibition.getEndTime();

        String query = QueryManager.getQuery("getBusyHall");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (String id : hallsID) {
                int i = 1;
                statement.setString(i++, id);
                statement.setString(i++, startDate);
                statement.setString(i++, endDate);
                statement.setString(i++, startDate);
                statement.setString(i++, endDate);
                statement.setString(i++, startTime);
                statement.setString(i++, endTime);
                statement.setString(i++, startTime);
                statement.setString(i, endTime);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String hallName = resultSet.getString("name");
                        int exhibitionID = resultSet.getInt("id");
                        hallNameExhIdMap.put(hallName, exhibitionID);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hallNameExhIdMap;
    }

    public List<Exhibition.Hall> getHallList() {
        String query = QueryManager.getQuery("getHallList");
        List<Exhibition.Hall> halls = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            getAllHalls(halls, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return halls;
    }

    private void getAllHalls(List<Exhibition.Hall> halls, ResultSet resultSet)
            throws SQLException {
        while (resultSet.next()) {
            Exhibition.Hall hall = new Exhibition.Hall();
            hall.setId(resultSet.getInt("id"));
            hall.setName(resultSet.getString("name"));
            hall.setCapacity(resultSet.getInt("capacity"));
            halls.add(hall);
        }
    }

    public void cancelExhibition(String id) {
        String query = QueryManager.getQuery("cancelExhibition");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            int result = statement.executeUpdate();
            if (result != 1) throw new SQLException("Exhibition is not canceled");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void activateExhibition(String id) {
        String query = QueryManager.getQuery("activateExhibition");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            int result = statement.executeUpdate();
            if (result != 1) throw new SQLException("Exhibition is not activated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean makeUserAdmin(int userID) {
        String query = QueryManager.getQuery("makeUserAdmin");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            int result = statement.executeUpdate();
            if (result != 1) throw new SQLException("User is already admin");
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
        LOGGER.info("User with ID " + userID + " is Admin");
        return true;
    }

    public boolean makeAdminUser(int userID) {
        String query = QueryManager.getQuery("makeAdminUser");
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            int result = statement.executeUpdate();
            if (result != 1) throw new SQLException("Admin is already user");
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
        LOGGER.info("Admin with ID " + userID + " is User");
        return true;
    }
}