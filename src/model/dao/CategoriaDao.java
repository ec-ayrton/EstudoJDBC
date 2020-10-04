package model.dao;

import java.util.List;

import model.entity.Categoria;

public interface CategoriaDao {

	void insert(Categoria obj);

	void update(Categoria obj);

	void deleteById(Integer id);

	Categoria findById(Integer id);

	List<Categoria> findAll();
}