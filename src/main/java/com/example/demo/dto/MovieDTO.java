package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO {
	
	private Long id;
	private String image;
	private String title;
	private double rating; 
	private String creationDate;
	private List<CharacterDTO> movieCharacters;
	private List<GenreDTO> movieGenres;
}
