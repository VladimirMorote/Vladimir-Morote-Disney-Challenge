package com.example.demo.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
@SQLDelete(sql = "UPDATE movies SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class MovieEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String image;
	
	private String title;
	
	@Column(name = "creation_date")
	private LocalDate creationDate;
	
	private double rating;
	
	// Soft Delete:
	private boolean deleted = Boolean.FALSE;
		
	
	// Has Many Personaje:
	@ManyToMany(
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
			}, fetch = FetchType.LAZY)
	@JoinTable(
			name = "movie_characters",
			joinColumns= @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "character_id"))
	private List<CharacterEntity> movieCharacters = new ArrayList<>(); 	
	
	// Has Many Genres:
	@ManyToMany(
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE,
			}, fetch = FetchType.LAZY)
	@JoinTable(
			name = "movie_genres",
			joinColumns= @JoinColumn(name = "movie_id"),
			inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<GenreEntity> movieGenres = new ArrayList<>(); 		
	
	
	                  // --- Methods --- //
	                    // Characters//
	public void addCharacterToMovie(CharacterEntity charToBeAdded) {
		this.movieCharacters.add(charToBeAdded);
	}	
	
	public void removeCharacterFromMovie(CharacterEntity charToBeRemoved) {
		this.movieCharacters.remove(charToBeRemoved);
	}
	
	                       // Genres //
	public void addGenreToMovie(GenreEntity genreToBeAdded) {
		this.movieGenres.add(genreToBeAdded);
	}
	
	public void removeGenreFromMovie(GenreEntity genreToBeRemoved) {
		this.movieGenres.remove(genreToBeRemoved);	
	}

}
