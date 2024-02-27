package model.dao;
import java.io.Serializable;
import java.util.List;

// Interface: Especificação genérica de um DAO, declara as funções basicas de um DAO e especifíca que deve trabaçhar com objetos do tipo Serializable
public interface GenericDAO<T extends Serializable>{
	public void insert(T entity);
	public void update(T entity);
	public void deleteById(Integer id);
	public T findById(Integer id);
	public List<T> findAll();
}
