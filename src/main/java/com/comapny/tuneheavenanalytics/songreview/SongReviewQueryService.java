package com.comapny.tuneheavenanalytics.songreview;

import com.comapny.tuneheavenanalytics.songreview.dto.MonthRatingDto;
import com.comapny.tuneheavenanalytics.songreview.dto.SongRatingDto;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class SongReviewQueryService {

    private final SongReviewRepository songReviewRepository;
    private final Clock clock;

    public SongReviewQueryService(final SongReviewRepository songReviewRepository, final Clock clock) {
        this.songReviewRepository = songReviewRepository;
        this.clock = clock;
    }

    public List<SongRatingDto> top100MostTrendingSongs() {
        return songReviewRepository.getTop100SongsByRatingGrowth(firstDayOfMonth(RatingMonth.CURRENT_MONTH), lastDayOfMonth(RatingMonth.CURRENT_MONTH),
                firstDayOfMonth(RatingMonth.PREVIOUS_MONTH), lastDayOfMonth(RatingMonth.PREVIOUS_MONTH),
                firstDayOfMonth(RatingMonth.TWO_MONTHS_AGO), lastDayOfMonth(RatingMonth.TWO_MONTHS_AGO));
    }

    public List<SongRatingDto> allSongsWithLoosingRating() {
        return songReviewRepository.allSongsWithLoosingRating(firstDayOfMonth(RatingMonth.CURRENT_MONTH), lastDayOfMonth(RatingMonth.CURRENT_MONTH),
                firstDayOfMonth(RatingMonth.PREVIOUS_MONTH), lastDayOfMonth(RatingMonth.PREVIOUS_MONTH),
                firstDayOfMonth(RatingMonth.TWO_MONTHS_AGO), lastDayOfMonth(RatingMonth.TWO_MONTHS_AGO));
    }

    public Double averageSongRatting(final UUID songId, final LocalDate from, final LocalDate to) {
        Objects.requireNonNull(songId);
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        return songReviewRepository.findAverageRating(songId, from, to);
    }

    public List<MonthRatingDto> ratingsFromLastThreeMonths(final UUID songId) {
        Objects.requireNonNull(songId);

        final var ratings = new LinkedList<MonthRatingDto>();

        for (int i = 3; i > 0; i--) {
            final var dateFrom = LocalDate.now(clock).minusMonths(i).with(TemporalAdjusters.firstDayOfMonth());
            final var dateTo = LocalDate.now(clock).minusMonths(i).with(TemporalAdjusters.lastDayOfMonth());
            final var averageRating = songReviewRepository.findAverageRating(songId, dateFrom, dateTo);
            if (averageRating != null) {
                ratings.add(new MonthRatingDto(dateFrom.format(DateTimeFormatter.ofPattern("yyyyMM")), averageRating));
            }
        }

        return ratings;
    }

    private LocalDate lastDayOfMonth(RatingMonth ratingMonth) {
        return LocalDate.now(clock)
                .minusMonths(ratingMonth.getMonth())
                .with(TemporalAdjusters.lastDayOfMonth());
    }

    private LocalDate firstDayOfMonth(RatingMonth ratingMonth) {
        return LocalDate.now(clock)
                .minusMonths(ratingMonth.getMonth())
                .with(TemporalAdjusters.firstDayOfMonth());
    }
}

