package tag.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

public class TagDAOImpl implements TagDAO {

	Connection connection = null;
	PreparedStatement stmt = null;

	@Override
	public boolean addTagToBook(BookTag bookTag) {
		
		try {
			connection = DAOUtilities.getConnection();
			 String sql = "INSERT INTO book_tag VALUES (?,?)";
			 stmt = connection.prepareStatement(sql);
			 
			 stmt.setString(1, bookTag.getIsbn13());
			 stmt.setString(2, bookTag.getTagName());
			 
			 if (stmt.executeUpdate() != 0) {
				 return true;
			 } else {
				 return false;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}


	@Override
	public boolean removeTagFromBookByIsbn(String isbn13) {
		
		 try {
			 connection = DAOUtilities.getConnection();
			 String sql = "DELETE book_tags WHERE isbn_13=?";
			 stmt = connection.prepareStatement(sql);
			 
			 stmt.setString(1, isbn13);
			 
			 if (stmt.executeUpdate() !=0) {
				 return true;
			 } else {
				 return false;
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return false;
		 } finally {
			 closeResources();
		 }
	}

	@Override
	public boolean removeTagFromBookByTagName(String tagName) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE book_tags WHERE tagName=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tagName);
			
			if(stmt.executeUpdate() !=0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	
	}

	@Override
	public List <Book> getBookByTag(String tag_name) {
		
		List<Book> books = new ArrayList<>();
		
		 try {
			 connection = DAOUtilities.getConnection();
			 String sql = "SELECT * FROM books INNER JOIN book_tag ON books.isbn_13 = book_tag.isbn_13";
			 stmt = connection.prepareStatement(sql);
			 
			 stmt.setString(1, tag_name );
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				Book book = new Book();

				book.setIsbn13(rs.getString("isbn_13"));
				book.setAuthor(rs.getString("author"));
				book.setTitle(rs.getString("title"));
				book.setPublishDate(rs.getDate("publish_date").toLocalDate());
				book.setPrice(rs.getDouble("price"));
				book.setContent(rs.getBytes("content"));
					
				books.add(book);
			}
			 
		 } catch(SQLException e) {
			 e.printStackTrace();
			 
		 } finally {
			 closeResources();
		 }
		
		return books;
	}

	@Override
	public List <BookTag> getTagByBook(String isbn13) {
		
		List<BookTag> tags = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			 String sql = "SELECT * FROM book_tag INNER JOIN books ON book_tag.isbn_13 = books.isbn_13";
			 stmt = connection.prepareStatement(sql);
			 
			 stmt.setString(1, "%" + isbn13 + "%");
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while(rs.next() ) {
				 BookTag tag = new BookTag();
				 
				 tag.setIsbn13(rs.getString("isbn_13"));
				 tag.setTagName(rs.getString("tag_name"));
				 
				 tags.add(tag);
			 }
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tags;
	}
	
	
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}
