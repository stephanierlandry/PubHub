package tag.dao;

import java.util.List;

import examples.pubhub.model.Book;

public interface TagDAO {
	
	public boolean addTagToBook(BookTag bookTag);
	public boolean removeTagFromBookByIsbn(String isbn13);
	public boolean removeTagFromBookByTagName(String tagName);
	
	public  List <Book> getBookByTag(String tag_name); 
	public  List <BookTag> getTagByBook(String isbn13);
}
