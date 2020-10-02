package com.kucher.dao;

import com.kucher.model.Exhibition;
import com.kucher.model.User;
import com.kucher.util.QueryManager;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class DBManager {
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

        List<Exhibition.Hall> hallList = getAllHallsByExhibition(exhibition, connection);
        exhibition.setHallList(hallList);
    }

    private List<Exhibition.Hall> getAllHallsByExhibition(Exhibition exhibition, Connection connection) {
        List<Exhibition.Hall> hallList = new ArrayList<>();
        String query = QueryManager.getQuery("getAllHallsByExhibitionId");
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, exhibition.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Exhibition.Hall hall = new Exhibition.Hall();
                    hall.setId(resultSet.getInt("id"));
                    hall.setName(resultSet.getString("name"));
                    hall.setCapacity(resultSet.getInt("capacity"));
                    hallList.add(hall);
                }
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
}