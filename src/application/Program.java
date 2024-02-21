package application;

import java.util.Date;

import model.dao.DAOFactory;
import model.dao.GenericDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String[] args) {
		Department obj = new Department(1, "Computers");
		Seller seller = new Seller(1, "Marcos", "willian.marcos7@gmail.com", new Date(), 3000.0, obj );
		
		GenericDAO<Seller> sellerDAO = DAOFactory.createSellerDAO();
		
		System.out.println(seller);
	}
}
