package com.project.scheduling.scheduleBoard.dto;

import com.project.scheduling.user.entity.ScheduleBoard;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleBoardReadResponse {

  private String title;
  private String content;
  private String state;
  private LocalDate start;
  private LocalDate end;

  public static ScheduleBoardReadResponse getBoard(ScheduleBoard scheduleBoard) {
    return new ScheduleBoardReadResponse(
        scheduleBoard.getTitle(),
        scheduleBoard.getContent(),
        scheduleBoard.getState(),
        scheduleBoard.getStart(),
        scheduleBoard.getEnd()
    );
  }

}
