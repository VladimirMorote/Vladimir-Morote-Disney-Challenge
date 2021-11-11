package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.GenreDTO;
import com.example.demo.entity.GenreEntity;
import com.example.demo.exception.ParamNotFound;
import com.example.demo.mapper.GenreMapper;
import com.example.demo.repository.GenreRepository;
import com.example.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GenreServiceImpl implements GenreService {

	// Repository
	@Autowired
	private GenreRepository genreRepo;
	
	// Mapper
	@Autowired
	private GenreMapper genreMapper;
	
	// --- POST ---
	@Override
	public GenreDTO saveNewGenre(GenreDTO newGenre) {
		GenreEntity newEntity = genreMapper.genreDTO2Entity(newGenre);
		GenreEntity savedGenre = genreRepo.save(newEntity);
		GenreDTO resultDTO = genreMapper.genreEntity2DTO(savedGenre, false);
		return resultDTO;
	}

	// --- GET ---
	@Override
	public List<GenreDTO> getAllGenres() {
		List<GenreEntity> savedGenres = genreRepo.findAll();
		List<GenreDTO> resultDTO = genreMapper.genreEntityList2DTOList(savedGenres);
		return resultDTO;
	}
	
	// --- PUT ---
	@Override
	public GenreDTO editGenreById(Long id, GenreDTO genreToEdit) {
		GenreEntity savedGenre = this.handleFindById(id);
		savedGenre.setImage(genreToEdit.getImage());
		savedGenre.setName(genreToEdit.getName());
		GenreEntity editedGenre = genreRepo.save(savedGenre);
		GenreDTO resultDTO = genreMapper.genreEntity2DTO(editedGenre, false);
		return resultDTO;
	}	

	// --- DELETE ---
	@Override
	public void deleteGenreById(Long id) {
		genreRepo.deleteById(id);		
	}
	
	// - Error Handling -
	public GenreEntity handleFindById(Long id) {
		Optional<GenreEntity> toBeFound = genreRepo.findById(id);
		if(!toBeFound.isPresent()) {
			throw new ParamNotFound("No Genre for id: " + id);
		}
		return toBeFound.get();
	}

}
