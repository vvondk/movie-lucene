package com.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class DocResult {
   long docId;
   String title;
   String engTitle;
}
