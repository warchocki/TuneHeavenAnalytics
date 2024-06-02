package com.comapny.tuneheavenanalytics.csv;

import com.comapny.tuneheavenanalytics.DirectoryProperties;
import com.comapny.tuneheavenanalytics.csv.dto.SongRatingDtoForTestsOnly;
import com.comapny.tuneheavenanalytics.songreview.dto.SongRatingDto;
import com.comapny.tuneheavenanalytics.songreview.SongReviewQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SongRatingsCsvExporterTest {

    @Mock
    private SongReviewQueryService songReviewQueryService;

    private Clock clock;

    private SongRatingsCsvExporter songRatingsCsvExporter;

    private DirectoryProperties directoryProperties;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        clock = Clock.fixed(Instant.parse("2024-05-30T12:00:00Z"), ZoneId.systemDefault());
        directoryProperties = new DirectoryProperties();
        directoryProperties.setExportDir(tempDir.toString());
        directoryProperties.setImportDir(tempDir.toString());
        songRatingsCsvExporter = new SongRatingsCsvExporter(songReviewQueryService, clock, directoryProperties);
    }

    @Test
    void shouldGenerateValidFileNameForTopTrendingSongsCsv() {
        // when
        var result = songRatingsCsvExporter.generateCsvWithTop100MostTrendingSongs();

        // then
        assertEquals("trending100songs-202405.csv", result.getName());
    }

    @Test
    void shouldGenerateValidFileNameForLoosingRatingSongsCsv() {
        // when
        var result = songRatingsCsvExporter.generateCsvWithLoosingRatingSongs();

        // then
        assertEquals("songs-loosing-202405.csv", result.getName());
    }

    @Test
    void shouldGenerateCsvFile() {
        // given
        List<SongRatingDto> songRatingDtos = List.of(songRatingDto(), songRatingDto());

        // when
        var result = songRatingsCsvExporter.prepareCsv(songRatingDtos, tempDir.resolve("testCsv.csv"));

        // then
        assertTrue(result.exists());
        assertTrue(result.length() > 0);
    }

    @Test
    void shouldGenerateEmptyCsvFile() {
        // when
        var result = songRatingsCsvExporter.prepareCsv(new ArrayList<>(), tempDir.resolve("testCsv.csv"));

        // then
        assertTrue(result.exists());
        assertTrue(result.length() > 0);
    }

    private SongRatingDtoForTestsOnly songRatingDto() {
        return SongRatingDtoForTestsOnly.Builder.newBuilder()
                .songId(UUID.randomUUID())
                .songName("songName")
                .currentMonthRating(1.0)
                .previousMonthRating(2.0)
                .twoMonthsAgoRating(3.0)
                .ratingGrowth(0.5)
                .build();
    }
}