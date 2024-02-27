package application;

import java.util.Date;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		// Using factory pattern for instantiate the DAO implementations
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

		// Create
		sellerDAO.insert(new Seller(null, "Marquinhos", "willian.marcos7@hotmail.com", new Date(), 2500.0, new Department(1, null)));
		departmentDAO.insert(new Department(null, "Servers"));

		// Read
		Seller seller = sellerDAO.findById(1);
		Department dep = departmentDAO.findById(2);

		// Read all
		List<Seller> sellers = sellerDAO.findAll();
		List<Department> departments = departmentDAO.findAll();

		// Read by department
		List<Seller> sellersGroup = sellerDAO.findByDepartment(dep);

		// Update
		sellerDAO.update(seller);
		departmentDAO.update(dep);
		
		// Delete
		sellerDAO.deleteById(500);
		departmentDAO.deleteById(500);
		
		// Show
		System.out.println("\nSellers by department: ");
		sellersGroup.forEach(element -> System.out.println(element));

		System.out.println("\nAll sellers: ");
		sellers.forEach(element -> System.out.println(element));

		System.out.println("\nAll departments: ");
		departments.forEach(element -> System.out.println(element));

		Department departmentToInsert = new Department(6, "Petz");

		departmentDAO.update(departmentToInsert);

		// All tests on Department and Seller entities worked!
	}
}
