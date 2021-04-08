package com.example.mongo.api.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongo.api.model.Book;
import com.example.mongo.api.repository.BookRepository;

@RestController
public class BookController {

	@Autowired
	private BookRepository repository;

	@PostMapping("/addBook")
	public String saveBook(@RequestBody Book book) {
		repository.save(book);
		return "Added book with id:" + book.getId();

	}

	@PostMapping("/add")
	public String save() {
		String line;
		try {
			FileReader dosya = new FileReader("ADD-YOUR-DATASET-PATH-HERE"); //first column is bookname next column is author name
			BufferedReader oku = new BufferedReader(dosya);
			line = oku.readLine();
			while (line != null) {
				String[] arr=line.split(",");
				Book book=new Book();
				int id=Integer.valueOf(arr[0]);
				String bookName=arr[1];
				String authorName=arr[2];
				book.setId(id);
				book.setBookName(bookName);
				book.setAuthorName(authorName);
				repository.save(book);
				line = oku.readLine();
			}
			oku.close();
			return "saved all books";
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	@GetMapping("/findAllBooks")
	public List<Book> getBooks() {
		return repository.findAll();
	}

	@GetMapping("/findAllBooks/{id}")
	public Optional<Book> getBook(@PathVariable int id) {
		return repository.findById(id);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteBook(@PathVariable int id) {
		repository.deleteById(id);
		return "book deleted with id: " + id;
	}

	@PostMapping("/findAllBooks/{id}/update/{bookName}")
	public String updateBookName(@PathVariable int id, @PathVariable String bookName) {
		Optional<Book> book = repository.findById(id);
		Book tempBook = book.get();
		tempBook.setBookName(bookName);
		repository.save(tempBook);
		return "book name updated \n" + tempBook.toString();

	}

}
