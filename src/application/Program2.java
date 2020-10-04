package application;

import java.util.List;
import java.util.Scanner;

import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.dao.ProdutoDao;
import model.entity.Categoria;
import model.entity.Produto;

public class Program2 {

	public static void main(String[] args) {
ProdutoDao produtoDao = DaoFactory.createProdutoDao();
CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();

 		Scanner sc = new Scanner(System.in);

		System.out.println("=== TESTE 1: produto findById =====");

		Produto produto = produtoDao.findById(13);

		System.out.println(produto);

		System.out.println("\n=== TESTE 2: produto findByCategoria =====");
		Categoria cat = categoriaDao.findById(1);
		List<Produto> list = produtoDao.findByCategoria(cat);
		for (Produto obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n=== TESTE 3: produto findAll =====");
		list = produtoDao.findAll();
		for (Produto obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n=== TESTE 4: produto insert =====");
		Produto newProduto = new Produto(null, "PS4", 2000.0, cat);
		produtoDao.insert(newProduto);
		System.out.println("Inserted! New id = " + newProduto.getId());

		System.out.println("\n=== TESTE 5: produto update =====");
		produto = produtoDao.findById(1);
		produto.setNome("A cabana");
		produto.setpreco(20);
		produtoDao.update(produto);
		System.out.println("Update completed");

		System.out.println("\n=== TESTE 6: produto delete =====");
		System.out.println("Insira o Id para o  delete test: ");
		int id = sc.nextInt();
		produtoDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
	}

}
