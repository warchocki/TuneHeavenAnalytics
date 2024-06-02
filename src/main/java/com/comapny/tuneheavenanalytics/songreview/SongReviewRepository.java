package com.comapny.tuneheavenanalytics.songreview;

import com.comapny.tuneheavenanalytics.songreview.dto.SongRatingDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

interface SongReviewRepository extends CrudRepository<SongReview, UUID> {

    @Query("SELECT AVG(e.reviewRating) FROM SongReview e WHERE e.songId = :songId AND e.createdAt >= :from AND e.createdAt <= :to")
    Double findAverageRating(@Param("songId") final UUID songId, @Param("from") final LocalDate from, @Param("to") final LocalDate to);


    @Query(value = "SELECT " +
            "    song.SONG_ID," +
            "    song.song_name," +
            "    current_month.rating current_month_rating," +
            "    previous_month.rating previous_month_rating," +
            "     two_months_ago.rating two_months_ago_rating," +
            "    (current_month.rating - previous_month.rating) AS rating_growth " +
            "FROM " +
            "    (SELECT SONG_ID, AVG(REVIEW_RATING) AS rating" +
            "    FROM SONG_REVIEW" +
            "    WHERE CREATED_AT BETWEEN :currentMonthFrom AND :currentMonthTo" +
            "    GROUP BY SONG_ID) AS current_month " +
            "LEFT JOIN " +
            "    (SELECT SONG_ID, AVG(REVIEW_RATING) AS rating" +
            "    FROM SONG_REVIEW" +
            "    WHERE CREATED_AT BETWEEN :previousMonthFrom AND :previousMonthTo" +
            "    GROUP BY SONG_ID) AS previous_month " +
            "ON previous_month.SONG_ID = current_month.SONG_ID " +
            "LEFT JOIN " +
            "    (SELECT SONG_ID, AVG(REVIEW_RATING) AS rating" +
            "    FROM SONG_REVIEW" +
            "    WHERE CREATED_AT BETWEEN :twoMonthsAgoFrom AND :twoMonthsAgoTo " +
            "    GROUP BY SONG_ID) AS two_months_ago " +
            "ON two_months_ago.SONG_ID = current_month.SONG_ID " +
            "JOIN Song song ON song.song_id = current_month.Song_id " +
            "ORDER BY rating_growth DESC " +
            "LIMIT 100; ", nativeQuery = true)
    List<SongRatingDto> getTop100SongsByRatingGrowth(@Param("currentMonthFrom") final LocalDate currentMonthFrom,
                                                     @Param("currentMonthTo") final LocalDate currentMonthTo,
                                                     @Param("previousMonthFrom") final LocalDate previousMonthFrom,
                                                     @Param("previousMonthTo") final LocalDate previousMonthTo,
                                                     @Param("twoMonthsAgoFrom") final LocalDate twoMonthsAgoFrom,
                                                     @Param("twoMonthsAgoTo") final LocalDate twoMonthsAgoTo
    );

    @Query(value = "SELECT " +
            "    song.SONG_ID," +
            "    song.song_name," +
            "    current_month.rating current_month_rating," +
            "    previous_month.rating previous_month_rating," +
            "     two_months_ago.rating two_months_ago_rating," +
            "    (current_month.rating - previous_month.rating) AS rating_growth " +
            "FROM " +
            "    (SELECT SONG_ID, AVG(REVIEW_RATING) AS rating" +
            "    FROM SONG_REVIEW" +
            "    WHERE CREATED_AT BETWEEN :currentMonthFrom AND :currentMonthTo" +
            "    GROUP BY SONG_ID) AS current_month " +
            "LEFT JOIN " +
            "    (SELECT SONG_ID, AVG(REVIEW_RATING) AS rating" +
            "    FROM SONG_REVIEW" +
            "    WHERE CREATED_AT BETWEEN :previousMonthFrom AND :previousMonthTo" +
            "    GROUP BY SONG_ID) AS previous_month " +
            "ON previous_month.SONG_ID = current_month.SONG_ID " +
            "LEFT JOIN " +
            "    (SELECT SONG_ID, AVG(REVIEW_RATING) AS rating" +
            "    FROM SONG_REVIEW" +
            "    WHERE CREATED_AT BETWEEN :twoMonthsAgoFrom AND :twoMonthsAgoTo " +
            "    GROUP BY SONG_ID) AS two_months_ago " +
            "ON two_months_ago.SONG_ID = current_month.SONG_ID " +
            "JOIN Song song ON song.song_id = current_month.Song_id " +
            "WHERE (previous_month.rating - current_month.rating) >= 0.4 ", nativeQuery = true)
    List<SongRatingDto> allSongsWithLoosingRating(@Param("currentMonthFrom") final LocalDate currentMonthFrom,
                               @Param("currentMonthTo") final LocalDate currentMonthTo,
                               @Param("previousMonthFrom") final LocalDate previousMonthFrom,
                               @Param("previousMonthTo") final LocalDate previousMonthTo,
                               @Param("twoMonthsAgoFrom") final LocalDate twoMonthsAgoFrom,
                               @Param("twoMonthsAgoTo") final LocalDate twoMonthsAgoTo
    );
}
