package application;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();
		DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();
		Department dep = new Department(2, null);
		
		Seller seller = sellerDAO.findById(1);
		List<Seller> sellers = sellerDAO.findByDepartment(dep);
		List<Seller> sellers1 = sellerDAO.findAll();
				
		System.out.println("\nSellers by department: ");
		sellers.forEach(element->System.out.println(element));
				
		System.out.println("\nAll sellers: ");
		sellers1.forEach(element->System.out.println(element));
		
		System.out.println("\nAll departments: ");
		List<Department> departments = departmentDAO.findAll();
		departments.forEach(element->System.out.println(element));
		
		Department departmentToInsert = new Department(6, "Petz");
		
		departmentDAO.update(departmentToInsert);

		
		// All tests on Department and Seller entities worked!
	}
}
