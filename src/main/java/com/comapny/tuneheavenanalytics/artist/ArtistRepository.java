package com.comapny.tuneheavenanalytics.artist;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


interface ArtistRepository extends CrudRepository<Artist, UUID> {
}
