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
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOJDBC implements SellerDAO{
	
	Connection conn;
	
	public SellerDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = 
				"INSERT INTO seller "
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+ "	VALUES(?,?,?,?,?)";
		
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, seller.getName());
			statement.setString(2, seller.getEmail());
			statement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			statement.setDouble(4, seller.getBaseSalary());
			statement.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected == 1) {
				result = statement.getGeneratedKeys();
				if(result.next()) {
					seller.setId(result.getInt(1));
				}else {
					throw new DBException("Unexpected error: No rows affected!");
				}
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement statement = null;
		String query = 
				"UPDATE seller "
				+ "SET Name = ?, "
				+ "Email = ?, "
				+ "BirthDate = ?, "
				+ "BaseSalary = ?, "
				+ "DepartmentId = ? "
				+ "WHERE Id = ?";
		
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, seller.getName());
			statement.setString(2, seller.getEmail());
			statement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			statement.setDouble(4, seller.getBaseSalary());
			statement.setInt(5, seller.getDepartment().getId());
			statement.setInt(6, seller.getId());
			
			statement.executeUpdate();
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement statement = null;
		String query = 
				"DELETE FROM seller "
				+ "WHERE seller.id = ?;";
		
		try {
			statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected < 1) {
				throw new DBException("Unexpected error: No rows affected.");
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = 
				"SELECT seller.*, department.Name AS DepName FROM seller "
				+ "INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "WHERE seller.id = ?;";
		
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, id);
			result = statement.executeQuery();
			
			if(result.next()) {
				Department department = instantiateDepartment(result);
				Seller seller = instantiateSeller(result, department);
				return seller;
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
		return null;
	}

	@Override
	public List<Seller> findAll() {
		List<Seller> sellers = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = 
				"SELECT seller.*, department.Name AS DepName FROM seller "
				+ "INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "ORDER BY Name;";
		try {
			statement = conn.prepareStatement(query);
			result = statement.executeQuery();
			
			while(result.next()) {
				Department department = instantiateDepartment(result);
				sellers.add(instantiateSeller(result, department));
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
		return sellers;
	}
	
	public List<Seller> findByDepartment(Department department){
		List<Seller> sellers = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet result = null;
		String query = 
				"SELECT seller.*, department.Name AS DepName FROM seller "
				+ "INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "WHERE department.id = ? "
				+ "ORDER BY Name;";
		try {
			statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, department.getId());
			result = statement.executeQuery();
			
			Department sharedDepartment = null;
			while(result.next()) {
				if(sharedDepartment == null) {
					sharedDepartment = instantiateDepartment(result);
				}
				
				sellers.add(instantiateSeller(result, sharedDepartment));
			}
		}catch(SQLException ex) {
			throw new DBException(ex.getMessage());
		}finally {
			DB.closeResultSet(result);
			DB.closeStatement(statement);
		}
		return sellers;
	}
	
	private Department instantiateDepartment(ResultSet result) throws SQLException{
		return new Department(result.getInt("DepartmentId"), result.getString("DepName"));
	}
	
	private Seller instantiateSeller(ResultSet result, Department department) throws SQLException{
		Seller seller = new Seller();
		seller.setId(result.getInt("Id"));
		seller.setName(result.getString("Name"));
		seller.setEmail(result.getString("Email"));
		seller.setBaseSalary(result.getDouble("BaseSalary"));
		seller.setBirthDate(result.getDate("BirthDate"));
		seller.setDepartment(department);
		return seller;
	}
	
}
