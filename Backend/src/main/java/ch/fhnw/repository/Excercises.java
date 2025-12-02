package ch.fhnw.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface Excercises {
   LocalTime getDuration();
   LocalDate getDate();
   String getTitle();

}
