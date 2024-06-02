package com.comapny.tuneheavenanalytics.songreview;

import com.comapny.tuneheavenanalytics.csv.SongRatingsCsvExporter;
import com.comapny.tuneheavenanalytics.csv.SongRatingsCsvImporter;
import com.comapny.tuneheavenanalytics.songreview.dto.MonthRatingDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class SongReviewController {

    private final SongReviewQueryService songReviewQueryService;
    private final SongRatingsCsvExporter songRatingsCsvExporter;
    private final SongRatingsCsvImporter songRatingsCsvImporter;

    public SongReviewController(final SongReviewQueryService songReviewQueryService, SongRatingsCsvExporter songRatingsCsvExporter, SongRatingsCsvImporter songRatingsCsvImporter) {
        this.songReviewQueryService = songReviewQueryService;
        this.songRatingsCsvExporter = songRatingsCsvExporter;
        this.songRatingsCsvImporter = songRatingsCsvImporter;
    }

    @GetMapping("{songId}/avg")
    public ResponseEntity<Double> getAverage(@PathVariable("songId") final UUID songId,
            @RequestParam("since") @DateTimeFormat(pattern = "yyyyMMdd") final LocalDate since,
            @RequestParam("until") @DateTimeFormat(pattern = "yyyyMMdd") final LocalDate until) {

        return ResponseEntity.ok(songReviewQueryService.averageSongRatting(songId, since, until));
    }

    @GetMapping("{songId}/avg-three-months")
    public ResponseEntity<List<MonthRatingDto>> lastThreeMonthsAvreageRatings(@PathVariable("songId") final UUID songId) {

        return ResponseEntity.ok(songReviewQueryService.ratingsFromLastThreeMonths(songId));
    }

    @GetMapping("test")
    public ResponseEntity<String> test() {
        //songRatingsCsvImporter.importSongRatings(new File("/Users/jacek/Downloads/tuneheaven-songs-2025-03-24.csv"));
        songRatingsCsvExporter.generateCsvWithLoosingRatingSongs();
        songRatingsCsvExporter.generateCsvWithTop100MostTrendingSongs();
        return ResponseEntity.ok("SS");
    }
}
