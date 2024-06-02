package com.comapny.tuneheavenanalytics.song;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface SongRepository extends CrudRepository<Song, UUID> {
}
