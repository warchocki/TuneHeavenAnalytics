package com.comapny.tuneheavenanalytics.artist;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(final ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void importArtist(final ImportArtistCommand importArtistCommand) {
        Objects.requireNonNull(importArtistCommand);
        if (!artistRepository.existsById(importArtistCommand.getArtistId())) {
            artistRepository.save(importArtistCommand.toEntity());
        }
    }
}
