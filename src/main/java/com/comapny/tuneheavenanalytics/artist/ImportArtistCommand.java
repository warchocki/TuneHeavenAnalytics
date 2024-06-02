package com.comapny.tuneheavenanalytics.artist;

import java.util.UUID;


public class ImportArtistCommand {

    private final UUID artistId;
    private final String artistName;

    public ImportArtistCommand(final UUID artistId, final String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public UUID getArtistId() {
        return artistId;
    }

    Artist toEntity() {
        return new Artist(artistId, artistName);
    }
}
