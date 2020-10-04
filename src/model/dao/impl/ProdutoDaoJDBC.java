package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ProdutoDao;
import model.entity.Categoria;
import model.entity.Produto;

public class ProdutoDaoJDBC implements ProdutoDao {

	private Connection conn;

	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public void insert(Produto obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO Produto " + "(Nome, preco, categoriaId) "
					+ "VALUES " + "(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getpreco());
			st.setInt(3, obj.getCategoria().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Produto obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto "
					+ "SET Nome = ?, preco = ?,categoriaId = ? " + "WHERE Id = ?");

			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getpreco());
			st.setInt(3, obj.getCategoria().getId());
			st.setInt(4, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM produto WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT produto.*,categoria.nome as catnome " + "FROM produto INNER JOIN categoria "
							+ "ON produto.categoriaId = categoria.Id " + "WHERE produto.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Categoria cat = instantiatecategoria(rs);
				Produto obj = instantiateProduto(rs, cat);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Produto instantiateProduto(ResultSet rs, Categoria cat) throws SQLException {
		Produto obj = new Produto();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("nome"));
		obj.setpreco(rs.getDouble("preco"));
		obj.setCategoria(cat);
		return obj;
	}

	private Categoria instantiatecategoria(ResultSet rs) throws SQLException {
		Categoria cat = new Categoria();
		cat.setId(rs.getInt("categoriaId"));
		cat.setNome(rs.getString("catnome"));
		return cat;
	}
	
	
	
	
	
	@Override
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT produto.*,categoria.nome as Catnome " + "FROM produto INNER JOIN categoria "
							+ "ON produto.categoriaId = categoria.Id " + "ORDER BY nome");

			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();
			Map<Integer, Categoria> map = new HashMap<>();

			while (rs.next()) {

				Categoria cat = map.get(rs.getInt("categoriaId"));

				if (cat == null) {
					cat = instantiatecategoria(rs);
					map.put(rs.getInt("categoriaId"), cat);
				}

				Produto obj = instantiateProduto(rs, cat);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Produto> findByCategoria(Categoria categoria) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT produto.*,categoria.nome as Catnome " + "FROM produto INNER JOIN categoria "
							+ "ON produto.categoriaId = categoria.Id " + "WHERE categoriaId = ? " + "ORDER BY nome");

			st.setInt(1, categoria.getId());

			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();
			Map<Integer, Categoria> map = new HashMap<>();

			while (rs.next()) {

				Categoria cat = map.get(rs.getInt("categoriaId"));

				if (cat == null) {
					cat = instantiatecategoria(rs);
					map.put(rs.getInt("categoriaId"), cat);
				}

				Produto obj = instantiateProduto(rs, cat);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
