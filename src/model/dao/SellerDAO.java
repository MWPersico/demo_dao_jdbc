package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDAO extends GenericDAO<Seller>{
	public List<Seller> findByDepartment(Department department);
}
