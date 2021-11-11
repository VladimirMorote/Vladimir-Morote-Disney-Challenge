package com.example.demo.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.MovieBasicDTO;
import com.example.demo.dto.MovieDTO;
import com.example.demo.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MovieMapper {
	
	// Mappers:
	@Autowired
	private CharacterMapper charMapper;	
	@Autowired
	private GenreMapper genreMapper;
	

	// --- DTO -> Entity ---
	public MovieEntity movieDTO2Entity(MovieDTO dto) {
		MovieEntity ent = new MovieEntity();
		ent.setId(dto.getId());
		ent.setImage(dto.getImage());
		ent.setTitle(dto.getTitle());
		ent.setRating(dto.getRating());
		ent.setCreationDate(this.String2LocalDate(dto.getCreationDate()));
		return ent;
	}	
	
	// --- Entity -> DTO ---
	public MovieDTO entity2DTO(MovieEntity dbMovie, boolean b) {
		MovieDTO dto = new MovieDTO();
		dto.setId(dbMovie.getId());
		dto.setImage(dbMovie.getImage());
		dto.setTitle(dbMovie.getTitle());	
		dto.setRating(dbMovie.getRating());
		dto.setCreationDate(this.localDate2String(dbMovie.getCreationDate()));
		if(b) {
			dto.setMovieCharacters(charMapper.charListEntity2DTOList(dbMovie.getMovieCharacters(),false));
			dto.setMovieGenres(genreMapper.genreEntityList2DTOList(dbMovie.getMovieGenres()));
		}		
		return dto;
	}

	//
	// === List<Entity> -> List<DTO> ===
	public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entList, boolean b){
		List<MovieDTO> dtoList = new ArrayList<>();
		for(MovieEntity ent : entList) {
			dtoList.add(this.entity2DTO(ent, b));
		}
		return dtoList;
	}

	
	//--- Entity -> BasicDTO ---
	public MovieBasicDTO entity2BasicDTO(MovieEntity ent) {
		MovieBasicDTO dto = new MovieBasicDTO();
		dto.setImage(ent.getImage());
		dto.setTitle(ent.getTitle());	
		dto.setCreationDate(this.localDate2String(ent.getCreationDate()));
		return dto;
	}

	//--- List<Entity> -> List<BasicDTO> ---
	public List<MovieBasicDTO> entityList2BasicDTO(List<MovieEntity> dbList) {
		List<MovieBasicDTO> newList = new ArrayList<>();
		for(MovieEntity ent : dbList) {
			newList.add(this.entity2BasicDTO(ent));
		}
		return newList;
	}

	// --> Utils <--
	public LocalDate String2LocalDate(String enteredDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		LocalDate transformedDate = LocalDate.parse(enteredDate, formatter);
		return transformedDate;
	}
	
	public String localDate2String(LocalDate dbDate) {
		String formattedDate = dbDate.format(DateTimeFormatter.ofPattern("d/MM/yyyy"));	
		return formattedDate;
	}
}
	

