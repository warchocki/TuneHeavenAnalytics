package com.comapny.tuneheavenanalytics.songreview;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
public class SongReviewCommandService {

    private final SongReviewRepository songReviewRepository;
    private final Clock clock;

    public SongReviewCommandService(final SongReviewRepository songReviewRepository, final Clock clock) {
        this.songReviewRepository = songReviewRepository;
        this.clock = clock;
    }

    public void importSongReview(final ImportSongReviewCommand importSongReviewCommand) {
        Objects.requireNonNull(importSongReviewCommand);

        final var songReview = importSongReviewCommand.toEntity();
        songReview.setCreatedAt(LocalDate.now(clock));
        songReview.setId(UUID.randomUUID());
        songReviewRepository.save(songReview);
    }
}

