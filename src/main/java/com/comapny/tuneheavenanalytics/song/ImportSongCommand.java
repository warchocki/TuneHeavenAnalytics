package com.comapny.tuneheavenanalytics.song;

import java.util.UUID;

public class ImportSongCommand {

    private final UUID songId;
    private final String songName;
    private final UUID artistId;
    private final String genre;

    private ImportSongCommand(final Builder builder) {

        songId = builder.songId;
        songName = builder.songName;
        artistId = builder.artistId;
        genre = builder.genre;
    }

    Song toEntity() {
        Song song = new Song();
        song.setSongId(songId);
        song.setSongName(songName);
        song.setArtistId(artistId);
        song.setGenre(genre);
        return song;
    }

    public UUID getSongId() {
        return songId;
    }

    public static final class Builder {
        private UUID songId;
        private String songName;
        private UUID artistId;
        private String genre;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder songId(UUID val) {
            songId = val;
            return this;
        }

        public Builder songName(String val) {
            songName = val;
            return this;
        }

        public Builder artistId(UUID val) {
            artistId = val;
            return this;
        }

        public Builder genre(String val) {
            genre = val;
            return this;
        }

        public ImportSongCommand build() {
            return new ImportSongCommand(this);
        }
    }
}
