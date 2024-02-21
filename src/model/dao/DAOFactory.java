package model.dao;

import db.DB;
import model.dao.impl.SellerDAOJDBC;
import model.entities.Seller;

public class DAOFactory {
	public static GenericDAO<Seller> createSellerDAO() {
		return new SellerDAOJDBC(DB.getConnection());
	}
}