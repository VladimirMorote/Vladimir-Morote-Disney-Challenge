package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreDTO {
	
	private Long id;
	private String image;
	private String name;
	private List<MovieDTO> genreMovies;

}
