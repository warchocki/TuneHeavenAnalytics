package com.comapny.tuneheavenanalytics.songreview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;


@SpringBootTest
@ActiveProfiles("test")
class SongReviewQueryServiceIT {

    @Autowired
    SongReviewQueryService songReviewQueryService;


    @Test
    void shouldReturnTop100MostTrendingSongs() {
        // given
        // todo: implement
        // when
        var songRatings = songReviewQueryService.top100MostTrendingSongs();

        // then
    }

    @Test
    void shouldReturnAllSongsWithLoosingRating() {
        // given
        // todo: implement
        // when
        var results = songReviewQueryService.allSongsWithLoosingRating();

        // then
    }

    @Test
    void shouldReturnAverageSongRatting() {
        // given
        // todo: implement
        // when
        songReviewQueryService.averageSongRatting(UUID.randomUUID(), LocalDate.now(), LocalDate.now());

        // then
    }
}