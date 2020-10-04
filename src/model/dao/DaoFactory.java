package model.dao;

import db.DB;
import model.dao.impl.CategoriaDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;

public class DaoFactory {
	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoJDBC(DB.getConnection());
	}
}