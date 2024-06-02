package com.comapny.tuneheavenanalytics.artist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
class Artist {

    @Id
    private UUID artistId;

    @Column(nullable=false)
    private String artistName;

    public Artist() {
    }

    public Artist(UUID artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public UUID getArtistId() {
        return artistId;
    }
}
