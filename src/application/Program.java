package application;

import java.util.Date;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		SellerDAO sellerDAO = DAOFactory.createSellerDAO();		
		Department dep = new Department(2, null);
		
		Seller seller = sellerDAO.findById(1);
		List<Seller> sellers = sellerDAO.findByDepartment(dep);
		List<Seller> sellers1 = sellerDAO.findAll();
				
		System.out.println("\nSellers by department: ");
		sellers.forEach(element->System.out.println(element));
		
		System.out.println("\nAll sellers: ");
		sellers1.forEach(element->System.out.println(element));
		
		/*
		Seller sellerToUpdate = new Seller(11, "Leonardo Renan", "leo@hotmail.com", new Date(), 5000.0, dep);
		sellerDAO.update(sellerToUpdate);
		System.out.println("\nInsert before: ");
		System.out.println(sellerToInsert);
		
		sellerDAO.insert(sellerToInsert);
		
		System.out.println("\nInsert after: ");
		System.out.println(sellerToInsert);
		*/
	}
}
