package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentDAOJDBC implements DepartmentDAO{
	private Connection conn;
	
	public DepartmentDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = "INSERT INTO department "
				+"(Name) VALUES (?)";
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, department.getName());
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected == 1) {
				result = statement.getGeneratedKeys();
				if(result.next()) {
					department.setId(result.getInt(1));
				}
			}else {
				throw new DBException("Unexpected error: No rows affected");
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeStatement(statement);
			DB.closeResultSet(result);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement statement = null;
		String query = "UPDATE department "
				+"SET Name = ? "
				+"WHERE Id = ?";
		try {
			statement = conn.prepareStatement(query);
			statement.setString(1, department.getName());
			statement.setInt(2, department.getId());
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected < 1) {
				throw new DBException("Unexpected error: No rows affected");
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement statement = null;
		String query = "DELETE FROM department WHERE Id = ?";
		try {
			statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected < 1) {
				throw new DBException("Unexpected error: No rows affected");
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM department WHERE Id = ?";
		try {
			statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			result = statement.executeQuery();
			
			if(result.next()) {
				return new Department(result.getInt("Id"), result.getString("Name"));
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
		return new Department();
	}

	@Override
	public List<Department> findAll() {
		List<Department> departments = new ArrayList<>();
		Statement statement = null;
		ResultSet result = null;
		String query = "SELECT * FROM department ORDER BY Id";
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(query);
			
			while(result.next()) {
				departments.add(new Department(result.getInt("Id"), result.getString("Name")));
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeStatement(statement);
			DB.closeResultSet(result);
		}
		return departments;
	}
}
