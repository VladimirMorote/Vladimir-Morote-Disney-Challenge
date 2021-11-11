package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.GenreDTO;
import com.example.demo.entity.GenreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GenreMapper {
	
	// Mappers:
	@Autowired
	private MovieMapper movieMapper;

	// --- DTO -> Entity ---
	public GenreEntity genreDTO2Entity(GenreDTO newGenre) {
		GenreEntity ent = new GenreEntity();		
		ent.setName(newGenre.getName());
		ent.setImage(newGenre.getImage());
		return ent;
	}	
	
	//
	// === Entity -> DTO ===
	public GenreDTO genreEntity2DTO(GenreEntity savedGenre, Boolean b) {
		GenreDTO dto = new GenreDTO();
		dto.setId(savedGenre.getId());
		dto.setName(savedGenre.getName());
		dto.setImage(savedGenre.getImage());
		if(b) {
			dto.setGenreMovies(movieMapper.movieEntityList2DTOList(savedGenre.getGenreMovies(), false));
		}
		return dto;
	}	
	
	//
	// === List<Entity> -> List<DTO> ===
	public List<GenreDTO> genreEntityList2DTOList(List<GenreEntity> savedGenres) {
		List<GenreDTO> dtoList = new ArrayList<>();
		for (GenreEntity ent : savedGenres) {
			dtoList.add(this.genreEntity2DTO(ent, false));
		}
		return dtoList;
	}
	
}
