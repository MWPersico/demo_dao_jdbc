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
		// TODO Auto-generated method stub
		return null;
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
