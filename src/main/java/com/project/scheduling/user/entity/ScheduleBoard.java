package com.project.scheduling.user.entity;

import com.project.scheduling.scheduleBoard.dto.ScheduleBoardUpdateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "schedule_board")
public class ScheduleBoard {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "project_user_mapping_id")
  private ProjectUserMapping projectUserMapping;

  private LocalDate start;
  private LocalDate end;
  private String title;
  private String content;
  private String state;

  public ScheduleBoard(ProjectUserMapping projectUserMapping, LocalDate start, LocalDate end,
      String title, String content, String state) {
    this.projectUserMapping = projectUserMapping;
    this.start = start;
    this.end = end;
    this.title = title;
    this.content = content;
    this.state = state;
  }

  public void update(String title, String content, LocalDate start, LocalDate end, String state) {
      if (title != null) this.title = title;
      if (content != null) this.content = content;
      if (start != null) this.start = start;
      if (end != null) this.end = end;
      if (state != null) this.state = state;
  }


}
