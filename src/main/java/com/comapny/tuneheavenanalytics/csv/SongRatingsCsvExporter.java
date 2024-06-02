package com.comapny.tuneheavenanalytics.csv;

import com.comapny.tuneheavenanalytics.DirectoryProperties;
import com.comapny.tuneheavenanalytics.songreview.dto.SongRatingDto;
import com.comapny.tuneheavenanalytics.songreview.SongReviewQueryService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SongRatingsCsvExporter {

    private static final String TRENDING_SONGS_FILE_NAME =  "trending100songs-%s.csv";
    private static final String LOOSING_SONGS_FILE_NAME =  "songs-loosing-%s.csv";

    private final SongReviewQueryService songReviewQueryService;
    private final Clock clock;
    private final DirectoryProperties directoryProperties;


    public SongRatingsCsvExporter(final SongReviewQueryService songReviewQueryService, final Clock clock, DirectoryProperties directoryProperties) {
        this.songReviewQueryService = songReviewQueryService;
        this.clock = clock;
        this.directoryProperties = directoryProperties;
    }

    public File generateCsvWithTop100MostTrendingSongs() {
        final var songRatingDtos = songReviewQueryService.top100MostTrendingSongs();
        return prepareCsv(songRatingDtos, Paths.get(directoryProperties.getExportDir(), fileName(TRENDING_SONGS_FILE_NAME)));
    }

    public File generateCsvWithLoosingRatingSongs() {
        final var songRatingDtos = songReviewQueryService.allSongsWithLoosingRating();
        return prepareCsv(songRatingDtos, Paths.get(directoryProperties.getExportDir(), fileName(LOOSING_SONGS_FILE_NAME)));
    }

    File prepareCsv(final List<SongRatingDto> songRatings, final Path pathToCsvFile) {
        final var csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(Headers.class)
                .build();

        try (var printer = new CSVPrinter(Files.newBufferedWriter(pathToCsvFile), csvFormat)) {
            for (var songRating : songRatings) {
                printer.printRecord(
                        songRating.getSongName(),
                        songRating.getSongIdAsUUID(),
                        songRating.getCurrentMonthRating(),
                        songRating.getPreviousMonthRating(),
                        songRating.getTwoMonthsAgoRating()
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pathToCsvFile.toFile();
    }

    private String fileName(final String fileNamePattern) {
        final var formatter = DateTimeFormatter.ofPattern("yyyyMM");
        final var fileName = LocalDate.now(clock).format(formatter);
        return String.format(fileNamePattern, fileName);
    }
}
