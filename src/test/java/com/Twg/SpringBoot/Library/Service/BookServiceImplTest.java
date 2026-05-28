package com.Twg.SpringBoot.Library.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Twg.SpringBoot.Library.Entities.Book;
import com.Twg.SpringBoot.Library.ExceptionHandler.ResourceNotFoundException;
import com.Twg.SpringBoot.Library.Repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;
    //Expected User
    private Book bookSample;

    @BeforeEach
    void ArrangeBook() 
    {
        bookSample = new Book();
        bookSample.setId(101);
        bookSample.setTitle("Harry Potter");
        bookSample.setAuthor("J.k.Rowling");
        bookSample.setAvailable(true);
    }

    

    @Test
    @DisplayName("save() - Should successfully persist and return the book")
    void save_Success() 
    {
    	//Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(bookSample);
        //Act
        Book savedBook = bookService.save(new Book());

        assertNotNull(savedBook);
        assertThat(savedBook.getTitle()).isEqualTo(bookSample.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("findAll() - Should return list of all books")
    void findAll_Success() 
    {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(bookSample));

        List<Book> books = bookService.findAll();

        assertThat(books).hasSize(1);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("findById() - Should return book instance when valid ID is given")
    void findById_Success() 
    {
        when(bookRepository.findById(bookSample.getId())).thenReturn(Optional.of(bookSample));

        Book foundBookById = bookService.findById(bookSample.getId());

        assertNotNull(foundBookById);
        assertThat(foundBookById.getId()).isEqualTo(bookSample.getId());
    }
    
    @Test
    @DisplayName("findById() - Should throw ResourceNotFoundException when ID is invalid")
    void findById_ThrowsResourceNotFoundException() {
        when(bookRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.findById(999);
        });
    }
    
    @Test
    @DisplayName("FindBookByTitle() - Should return a list when matching titles exist")
    void findBookByTitle_Success() {
        // Notice we stub twice because your code calls bookRepository.findBookByTitle(title) twice!
        when(bookRepository.findBookByTitle(bookSample.getTitle())).thenReturn(Arrays.asList(bookSample));

        List<Book> books = bookService.FindBookByTitle(bookSample.getTitle());
     
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getAuthor()).isEqualTo(bookSample.getAuthor());
    }

    @Test
    @DisplayName("FindBookByTitle() - Should throw ResourceNotFoundException when query yields empty list")
    void findBookByTitle_ThrowsResourceNotFoundException_WhenEmpty() 
    {
        when(bookRepository.findBookByTitle("Nonexistent Book")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.FindBookByTitle("Nonexistent Book");
        });
    }
    
    @Test
    @DisplayName("availableBooks() - Should return list of items where available is true")
    void availableBooks_Success() 
    {
        when(bookRepository.findByAvailableTrue()).thenReturn(Arrays.asList(bookSample));

        List<Book> books = bookService.availableBooks();

        assertThat(books).hasSize(1);
        verify(bookRepository, times(1)).findByAvailableTrue();
    }

    @Test
    @DisplayName("updateBook() - Should successfully process modifications")
    void updateBook_Success() 
    {
        when(bookRepository.save(bookSample)).thenReturn(bookSample);

        Book updatedBook = bookService.updateBook(bookSample);

        assertNotNull(updatedBook);
        verify(bookRepository, times(1)).save(bookSample);
    }

    @Test
    @DisplayName("updateBook() - Should throw NullPointerException if database layer yields null")
    void updateBook_ThrowsNullPointerException() 
    {
        when(bookRepository.save(any(Book.class))).thenReturn(null);

        assertThrows(NullPointerException.class, () -> 
        {
            bookService.updateBook(new Book());
        });
    }
    
    @Test
    @DisplayName("deleteBook() - Should delegate to repository delete method")
    void deleteBook_Success() 
    {
        doNothing().when(bookRepository).delete(bookSample);

        bookService.deleteBook(bookSample);

        verify(bookRepository, times(1)).delete(bookSample);
    }

    @Test
    @DisplayName("deleteBookById() - Should delegate to repository deleteById method")
    void deleteBookById_Success() 
    {
        doNothing().when(bookRepository).deleteById(bookSample.getId());

        bookService.deleteBookById(bookSample.getId());

        verify(bookRepository, times(1)).deleteById(bookSample.getId());
    }

    
}
