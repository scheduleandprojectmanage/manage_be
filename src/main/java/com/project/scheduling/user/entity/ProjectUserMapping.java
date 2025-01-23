package com.project.scheduling.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "projectsUserMapping")
public class ProjectUserMapping {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project projects;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  private User users;

  @OneToMany(mappedBy = "project_user_mapping_id")
  private List<ScheduleBoard> schedulesBoards;
  @OneToMany(mappedBy = "project_user_mapping_id")
  private List<Schedule> schedules;

  @Enumerated(EnumType.STRING)
  private ProjectUserMappingEnum authority;
  private Boolean isConnect;

}
