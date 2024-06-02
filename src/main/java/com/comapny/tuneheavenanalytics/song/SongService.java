package com.comapny.tuneheavenanalytics.song;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(final SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void importSong(final ImportSongCommand importSongCommand) {
        Objects.requireNonNull(importSongCommand);
        if (!songRepository.existsById(importSongCommand.getSongId())) {
            songRepository.save(importSongCommand.toEntity());
        }
    }
}
