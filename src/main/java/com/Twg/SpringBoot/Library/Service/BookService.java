package com.Twg.SpringBoot.Library.Service;

import java.util.List;

import com.Twg.SpringBoot.Library.Entities.Book;


public interface BookService 
{
	public Book save(Book book);
	public List<Book> findAll();
	public Book findById(Integer id);
	public List<Book> FindBookByTitle(String title);
	public List<Book> availableBooks();
	public Book updateBook(Book book);
	public void deleteBook(Book book);
    public void deleteBookById(Integer id);

}
