package com.comapny.tuneheavenanalytics.cron;

import com.comapny.tuneheavenanalytics.DirectoryProperties;
import com.comapny.tuneheavenanalytics.csv.SongRatingsCsvExporter;
import com.comapny.tuneheavenanalytics.csv.SongRatingsCsvImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Component
public class ImportCsvCron {

    private static final Logger log = LoggerFactory.getLogger(ImportCsvCron.class);

    private static final String SONG_RATINGS_FILE_NAME =  "tune-heaven-songs-%s.csv";
    private final SongRatingsCsvImporter songRatingsCsvImporter;
    private final SongRatingsCsvExporter songRatingsCsvExporter;
    private final Clock clock;
    private final DirectoryProperties directoryProperties;

    public ImportCsvCron(final SongRatingsCsvImporter songRatingsCsvImporter, SongRatingsCsvExporter songRatingsCsvExporter, Clock clock, DirectoryProperties directoryProperties) {
        this.songRatingsCsvImporter = songRatingsCsvImporter;
        this.songRatingsCsvExporter = songRatingsCsvExporter;
        this.clock = clock;
        this.directoryProperties = directoryProperties;
    }

    @Scheduled(cron = "0 1 23 * * *")
    public void runCsvFileImport() {
        log.info("Importing CSV file with song ratings");
        final var filePath = Paths.get(directoryProperties.getImportDir(), songRatingsFileName());
        checkCsvFileToImportExists(filePath);
        songRatingsCsvImporter.importSongRatings(filePath.toFile());
        
        if (isTodayIsLastDayOfMonth()) {
            log.info("Generating CSV files with top 100 most trending songs and loosing rating songs");
            songRatingsCsvExporter.generateCsvWithTop100MostTrendingSongs();
            songRatingsCsvExporter.generateCsvWithLoosingRatingSongs();
        }
    }

    private void checkCsvFileToImportExists(final Path filePath) {
        if (filePath.toFile().exists()) {
            throw new RuntimeException("File does not exist");
        }
    }

    private boolean isTodayIsLastDayOfMonth() {
        final var lastDayInMonth = LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        final var currentDay  = LocalDate.now(clock).getDayOfMonth();
        return lastDayInMonth == currentDay;
    }

    private String songRatingsFileName() {
        final var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final var fileName = LocalDate.now(clock).format(formatter);
        return String.format(SONG_RATINGS_FILE_NAME, fileName);
    }
}
