package com.comapny.tuneheavenanalytics.csv.dto;

import com.comapny.tuneheavenanalytics.songreview.dto.SongRatingDto;

import java.util.UUID;

public class SongRatingDtoForTestsOnly implements SongRatingDto {

    UUID songId;
    String songName;
    Double currentMonthRating;
    Double previousMonthRating;
    Double twoMonthsAgoRating;
    Double ratingGrowth;

    private SongRatingDtoForTestsOnly(Builder builder) {
        songId = builder.songId;
        songName = builder.songName;
        currentMonthRating = builder.currentMonthRating;
        previousMonthRating = builder.previousMonthRating;
        twoMonthsAgoRating = builder.twoMonthsAgoRating;
        ratingGrowth = builder.ratingGrowth;
    }

    @Override
    public byte[] getSongId() {
        return songId.toString().getBytes();
    }

    @Override
    public String getSongName() {
        return songName;
    }

    @Override
    public Double getCurrentMonthRating() {
        return currentMonthRating;
    }

    @Override
    public Double getPreviousMonthRating() {
        return previousMonthRating;
    }

    @Override
    public Double getTwoMonthsAgoRating() {
        return twoMonthsAgoRating;
    }

    @Override
    public Double getRatingGrowth() {
        return ratingGrowth;
    }

    public UUID getSongIdAsUUID() {
        return songId;
    }


    public static final class Builder {
        private UUID songId;
        private String songName;
        private Double currentMonthRating;
        private Double previousMonthRating;
        private Double twoMonthsAgoRating;
        private Double ratingGrowth;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder songId(UUID val) {
            songId = val;
            return this;
        }

        public Builder songName(String val) {
            songName = val;
            return this;
        }

        public Builder currentMonthRating(Double val) {
            currentMonthRating = val;
            return this;
        }

        public Builder previousMonthRating(Double val) {
            previousMonthRating = val;
            return this;
        }

        public Builder twoMonthsAgoRating(Double val) {
            twoMonthsAgoRating = val;
            return this;
        }

        public Builder ratingGrowth(Double val) {
            ratingGrowth = val;
            return this;
        }

        public SongRatingDtoForTestsOnly build() {
            return new SongRatingDtoForTestsOnly(this);
        }
    }
}
