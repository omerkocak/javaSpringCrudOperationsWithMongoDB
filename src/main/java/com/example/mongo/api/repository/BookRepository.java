package com.example.mongo.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mongo.api.model.Book;

public interface BookRepository extends MongoRepository<Book, Integer>{

}
