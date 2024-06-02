package com.comapny.tuneheavenanalytics.songreview;

import java.util.UUID;


public class ImportSongReviewCommand {

    private final UUID userId;
    private final UUID songId;
    private final int reviewRating;

    public ImportSongReviewCommand(final UUID userId, final UUID songId, final int reviewRating) {
        this.userId = userId;
        this.songId = songId;
        this.reviewRating = reviewRating;
    }

    SongReview toEntity() {
        SongReview songReview = new SongReview();
        songReview.setUserId(userId);
        songReview.setSongId(songId);
        songReview.setReviewRating(reviewRating);
        return songReview;
    }
}
