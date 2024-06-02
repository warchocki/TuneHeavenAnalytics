package com.comapny.tuneheavenanalytics.songreview.dto;

import java.util.UUID;

public interface SongRatingDto {
    
     byte[] getSongId();

     String getSongName();

     Double getCurrentMonthRating();

     Double getPreviousMonthRating();

     Double getTwoMonthsAgoRating();

     Double getRatingGrowth();

     default UUID getSongIdAsUUID() {
         return UUID.nameUUIDFromBytes(getSongId());
     }
}


