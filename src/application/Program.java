package application;

import java.util.List;
import java.util.Scanner;

import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.entity.Categoria;

public class Program {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();

		System.out.println("=== TESTE 1: findById =======");
		Categoria cat = categoriaDao.findById(1);
		System.out.println(cat);

		System.out.println("\n=== TESTE 2: findAll =======");
		List<Categoria> list = categoriaDao.findAll();
		for (Categoria c : list) {
			System.out.println(c);
		}

		System.out.println("\n=== TEST 3: insert =======");
		Categoria newCategoria = new Categoria(null, "Music");
		categoriaDao.insert(newCategoria);
		System.out.println("Inserido! Novo id: " + newCategoria.getId());

		System.out.println("\n=== TESTE 4: update =======");
		Categoria cat2 = categoriaDao.findById(1);
		cat2.setNome("Eletronicos");
		categoriaDao.update(cat2);
		System.out.println("Update completed");

		System.out.println("\n=== TEST 5: delete =======");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		categoriaDao.deleteById(id);
		System.out.println("Delete completed");

		sc.close();
	}
}
