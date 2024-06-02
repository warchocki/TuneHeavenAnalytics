package com.comapny.tuneheavenanalytics.csv;

import com.comapny.tuneheavenanalytics.artist.ArtistService;
import com.comapny.tuneheavenanalytics.artist.ImportArtistCommand;
import com.comapny.tuneheavenanalytics.song.ImportSongCommand;
import com.comapny.tuneheavenanalytics.song.SongService;
import com.comapny.tuneheavenanalytics.songreview.ImportSongReviewCommand;
import com.comapny.tuneheavenanalytics.songreview.SongReviewCommandService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Clock;
import java.util.UUID;

import static com.comapny.tuneheavenanalytics.csv.ColumnNames.*;

@Component
public class SongRatingsCsvImporter {

    private static final Logger log = LoggerFactory.getLogger(SongRatingsCsvImporter.class);

    private final ArtistService artistService;
    private final SongReviewCommandService songReviewCommandService;
    private final SongService songService;
    private final Clock clock;


    public SongRatingsCsvImporter(final ArtistService artistService, final SongReviewCommandService songReviewCommandService, final SongService songService, Clock clock) {
        this.artistService = artistService;
        this.songReviewCommandService = songReviewCommandService;
        this.songService = songService;
        this.clock = clock;
    }

    public void importSongRatings(File csvFile) {
        try (FileReader reader = new FileReader(csvFile);
             CSVParser csvParser = CSVFormat.DEFAULT.builder()
                     .setHeader(SONG_NAME, SONG_ID, ARTIST_NAME, ARTIST_ID, USER_ID, REVIEW_RATING, GENRE)
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {
            for (CSVRecord csvRecord : csvParser) {
                final var songName = csvRecord.get(SONG_NAME);
                final var songId = UUID.fromString(csvRecord.get(SONG_ID));
                final var artistName = csvRecord.get(ARTIST_NAME);
                final var artistId = UUID.fromString(csvRecord.get(ARTIST_ID));
                final var userId = UUID.fromString(csvRecord.get(USER_ID));
                final var reviewRating = Integer.parseInt(csvRecord.get(REVIEW_RATING));
                final var genre = csvRecord.get(GENRE);

                artistService.importArtist(new ImportArtistCommand(artistId, artistName));
                songService.importSong(ImportSongCommand.Builder.newBuilder()
                        .songName(songName)
                        .songId(songId)
                        .artistId(artistId)
                        .genre(genre)
                        .build());

                songReviewCommandService.importSongReview(new ImportSongReviewCommand(userId, songId, reviewRating));
            }
        } catch (IOException e) {
            log.error("Error while importing song ratings from file: {}", csvFile.getName());
            e.printStackTrace();
        }
    }
}
