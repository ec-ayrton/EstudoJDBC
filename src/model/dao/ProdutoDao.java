package model.dao;

import java.util.List;

import model.entity.Categoria;
import model.entity.Produto;

public interface ProdutoDao {
	void insert(Produto obj);
	void update(Produto obj);
	void deleteById(Integer id);
	
	Produto findById(Integer id);
	
	List<Produto> findAll();
	List<Produto> findByCategoria(Categoria categoria);
}
