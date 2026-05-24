package com.Twg.SpringBoot.Library.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Twg.SpringBoot.Library.Entities.Book;
import com.Twg.SpringBoot.Library.Repository.BookRepository;

@Service
public class BookServiceImpl implements BookService
{
	@Autowired
    private BookRepository bookRepository;
	
	public BookServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	public BookServiceImpl(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	@Override
	public Book save(Book book) 
	{
		return bookRepository.save(book);
	}
	@Override
	public List<Book> findAll() 
	{
		return bookRepository.findAll();
	}
	@Override
	public Book findById(Integer id) {
		
		return bookRepository.findById(id).get();
	}
	
	@Override
	public List<Book> availableBooks() 
	{
		return bookRepository.findByAvailableTrue();
	}
	@Override
	public Book updateBook(Book book) 
	{
		return bookRepository.save(book);
	}
	@Override
	public void deleteBook(Book book) 
	{
		bookRepository.delete(book);
	}
	@Override
	public void deleteBookById(Integer id) 
	{
		bookRepository.deleteById(id);
		
	}
	@Override
	public List<Book> FindBookByTitle(String title) 
	{
		return bookRepository.findBookByTitle(title);
	}

	

}
