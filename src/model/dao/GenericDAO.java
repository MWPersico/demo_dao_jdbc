package model.dao;
import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T extends Serializable>{
	public void insert(T object);
	public void update(T object);
	public void deleteById(Integer id);
	public T findById(Integer id);
	public List<T> findAll();
}
