package com.example.demo.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

@Component
public class UserJdbcService implements UserDataService {

	@Autowired
	DataSource datasource;

	// Think about exception handling
	// We are explicitly getting the connection! What if there is an
	// exception while executing the query!

	@Override
	public List<User> retrieveData(String user)
			throws SQLException {
		Connection connection = datasource.getConnection();

		PreparedStatement st = connection.prepareStatement(
				"SELECT * FROM USER where userId=?");

		st.setString(1, user);

		ResultSet resultSet = st.executeQuery();
		List<User> users = new ArrayList<>();

		while (resultSet.next()) {

			User user = new User(resultSet.getInt("id"),
					resultSet.getString("username"),
					resultSet.getString("desc"),
					resultSet.getTimestamp("create_date"),
					resultSet.getBoolean("is_active"));
			users.add(user);
		}

		st.close();

		connection.close();

		return users;

	}

	@Override
	public int addUser(String user, String desc,
			Date targetDate, boolean isDone)
					throws SQLException {
		Connection connection = datasource.getConnection();

		PreparedStatement st = connection.prepareStatement(
				"INSERT INTO USER(username, desc, create_date, is_active) VALUES (?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		st.setString(1, username);
		st.setString(2, desc);
		st.setTimestamp(3,
				new Timestamp(create_date.getTime()));
		st.setBoolean(4, is_active);

		int id = st.executeUpdate();

		st.close();

		connection.close();

		return id;

	}

	@Override
	public User retrieveUser(int id) throws SQLException {
		Connection connection = datasource.getConnection();

		PreparedStatement st = connection.prepareStatement(
				"SELECT * FROM USER where id=?");

		st.setInt(1, id);

		ResultSet resultSet = st.executeQuery();

		User user = null;

		if (resultSet.next()) {

			user = new User(resultSet.getInt("id"),
					resultSet.getString("username"),
					resultSet.getString("desc"),
					resultSet.getTimestamp("create_date"),
					resultSet.getBoolean("is_active"));

		}

		st.close();

		connection.close();

		return user;

	}

	@Override
	public void updateUser(User user) throws SQLException {
		Connection connection = datasource.getConnection();

		PreparedStatement st = connection.prepareStatement(
				"Update USER set username=?, desc=?, create_date=?, is_active=? where id=?");

		st.setString(1, user.getUserName());
		st.setString(2, user.getDesc());
		st.setTimestamp(3, new Timestamp(
				user.getCreateDate().getTime()));
		st.setBoolean(4, user.isCreated());
		st.setInt(5, user.getId());

		st.execute();

		st.close();

		connection.close();

	}

	@Override
	public void deleteUser(int id) throws SQLException {
		Connection connection = datasource.getConnection();

		PreparedStatement st = connection.prepareStatement(
				"delete from USER where id=?");

		st.setInt(1, id);

		st.execute();

		st.close();

		connection.close();

	}

}
