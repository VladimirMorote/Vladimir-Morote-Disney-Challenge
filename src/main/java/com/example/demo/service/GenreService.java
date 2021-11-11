package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.GenreDTO;


public interface GenreService {

	// Post
	GenreDTO saveNewGenre(GenreDTO newGenre);

	// Get
	List<GenreDTO> getAllGenres();

	// Del
	void deleteGenreById(Long id);

	// PUT
	GenreDTO editGenreById(Long id, GenreDTO genreToEdit);

}
