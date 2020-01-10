package com.naz.PlexDownloader.services;

import com.naz.PlexDownloader.models.Book;
import com.naz.PlexDownloader.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }
}