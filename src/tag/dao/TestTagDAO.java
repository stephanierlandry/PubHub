package tag.dao;

import java.util.List;

import examples.pubhub.model.Book;

public class TestTagDAO {
	
	public static void main(String[] args) {
		
		TagDAO dao = new TagDAOImpl();
		List<Book> list = dao.getBookByTag("fiction");
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

}
