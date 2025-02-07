package com.project.scheduling.scheduleBoard.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleBoardUpdateRequest {
  private Long id;
  private String title;
  private String content;
  private String state;
  private LocalDate start;
  private LocalDate end;
  private Long projectUserMapping;

}
