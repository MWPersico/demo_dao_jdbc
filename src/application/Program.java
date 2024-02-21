package application;

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
				
		System.out.println(seller);
		sellers.forEach(element->System.out.println(element));
	}
}
