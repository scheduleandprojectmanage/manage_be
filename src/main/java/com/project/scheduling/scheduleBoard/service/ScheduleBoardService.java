package com.project.scheduling.scheduleBoard.service;

import com.project.scheduling.ProjectUserMapping.repository.ProjectUserMappingRepository;
import com.project.scheduling.scheduleBoard.dto.ScheduleBoardReadResponse;
import com.project.scheduling.scheduleBoard.repository.ScheduleRepository;
import com.project.scheduling.user.entity.ProjectUserMapping;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleBoardService {

  private final ScheduleRepository scheduleRepository;
  private final ProjectUserMappingRepository projectUserMappingRepository;

  public List<ScheduleBoardReadResponse> getScheduleBoardListByProjectId(Long projectId) {
    ProjectUserMapping mappings = projectUserMappingRepository.findByProjectsId(projectId);

    if (mappings == null) {
        throw new IllegalArgumentException("mappings가 null입니다.");
    }
    else {
      return scheduleRepository.findByProjectUserMappingId(mappings.getId()).stream()
          .map(ScheduleBoardReadResponse::getBoard)
          .collect(Collectors.toList());
    }
  }



}
