package com.comapny.tuneheavenanalytics.songreview;

public enum RatingMonth {
    CURRENT_MONTH(0),
    PREVIOUS_MONTH(1),
    TWO_MONTHS_AGO(2);

    private final int month;

    RatingMonth(final int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }
}
