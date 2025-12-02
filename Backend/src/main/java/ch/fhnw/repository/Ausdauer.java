package ch.fhnw.repository;

import java.time.LocalDate;
import java.time.LocalTime;

public class Ausdauer implements Excercises{
    private final LocalTime duration;
    private final LocalDate date;
    private final String title;

    public Ausdauer(LocalTime duration, LocalDate date, String title) {
        this.duration = duration;
        this.date = date;
        this.title = title;
    }

    @Override
    public LocalTime getDuration() {
        return duration;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
