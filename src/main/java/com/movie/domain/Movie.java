package com.movie.domain;

import com.opencsv.bean.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @CsvBindByName(column = "\uFEFFkey", required = true)
    int key;

    @CsvBindByName(column = "영화명", required = true)
    String name;

    @CsvBindByName(column = "영화명(영문)")
    String engName;

    @CsvBindByName(column = "제작연도")
    @CsvDate("yyyy")
    Date productionYear;
    //long productionYear;

    @CsvBindAndSplitByName(column = "제작국가", splitOn = ",", elementType = String.class)
    List productionCountry;

    @CsvBindByName(column = "유형")
    String type;

    @CsvBindAndSplitByName(column = "장르", splitOn = ",", elementType = String.class)
    List genre;

    @CsvBindByName(column = "제작상태")
    String productionStatus;

    @CsvBindAndSplitByName(column = "감독", splitOn = ",", elementType = String.class)
    List director;

    @CsvBindAndSplitByName(column = "제작사", splitOn = ",", elementType = String.class)
    List producer;
}
