package com.movie.domain;

import com.sun.istack.internal.Nullable;
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

   @Nullable
   String highlightTitle;

   @Nullable
   String highlightEngTitle;
}
