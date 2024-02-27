package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

// Especificação de SellerDAO, estender GenericDAO passando a entidade Seller como generic
public interface SellerDAO extends GenericDAO<Seller>{
	public List<Seller> findByDepartment(Department department);
}
