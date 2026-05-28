package com.Twg.SpringBoot.Library.Controller;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Twg.SpringBoot.Library.Entities.Book;
import com.Twg.SpringBoot.Library.Service.BookService;

@RestController
@RequestMapping("/books")
public class BookController 
{
	@Autowired
    private BookService bookService;
	
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
	public BookService getBookService() {
		return bookService;
	}
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	public BookController() 
	{
		
	}
	
	//Get All Books
	@GetMapping("/")
	public ResponseEntity<List<Book>> GetAllBooks()
	{
		return ResponseEntity.status(200).body(bookService.findAll());
	}
	//Get Specific Book By Id
	@GetMapping("/{id}")
	public ResponseEntity<Book> GetBookById(@PathVariable Integer id)
	{
		Book book=bookService.findById(id);
		return ResponseEntity.status(200).body(book);
	}
	@GetMapping("/booktitle/{title}")
	public ResponseEntity<List<Book>> GetBookByTitle(@PathVariable String title)
	{
		List<Book> books=bookService.FindBookByTitle(title);
		return ResponseEntity.status(200).body(books);
	}
	//Add a new Book
    @PostMapping("/")
	public ResponseEntity<Book> saveBook(@RequestBody Book book)
    {
    	Book storebook=bookService.save(book);
    	if(storebook==null)
    	{
    		throw new NullPointerException();
    	}
    	return ResponseEntity.status(201).body(storebook);
	}
	//It is used to update the inserted book with the id that is written inside a body
	@PutMapping("/")
    public ResponseEntity<Book> updateBook(@RequestBody Book book)
    {
       Book updatedBook=bookService.updateBook(book);
    	return ResponseEntity.status(200).body(updatedBook);
    }
	//Update Book By  id
	@PutMapping("/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Integer id,@RequestBody Book book)
    {
    	Book foundbook=bookService.findById(id);
    	foundbook.setTitle(book.getTitle());
    	foundbook.setIsbn(book.getIsbn());
    	foundbook.setAuthor(book.getAuthor());
    	foundbook.setAvailable(book.isAvailable());
    	Book updatedBook=bookService.updateBook(foundbook);
    	return ResponseEntity.status(200).body(updatedBook);
    }
	//Delete Book By id
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBookById(@PathVariable Integer id)
	{
		Book book=bookService.findById(id);
		bookService.deleteBookById(id);
		return ResponseEntity.status(200).body(book.getTitle()+"is deleted");
	}
}
