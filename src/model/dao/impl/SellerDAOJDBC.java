package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DBException;
import model.dao.GenericDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDAOJDBC implements GenericDAO<Seller>{
	
	Connection conn;
	
	public SellerDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller object) {
		
	}

	@Override
	public void update(Seller object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
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
			statement.getGeneratedKeys();
			
			if(result.next()) {
				Integer depId = result.getInt("DepartmentId");
				String depName = result.getString("DepName");
				Department department = new Department(depId, depName);
				
				Seller seller = new Seller();
				seller.setId(result.getInt("Id"));
				seller.setName(result.getString("Name"));
				seller.setEmail(result.getString("Email"));
				seller.setBaseSalary(result.getDouble("BaseSalary"));
				seller.setBirthDate(result.getDate("BirthDate"));
				seller.setDepartment(department);
				
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
		// TODO Auto-generated method stub
		return null;
	}
	
}
