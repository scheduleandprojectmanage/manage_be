package com.project.scheduling.scheduleBoard.service;

import com.project.scheduling.ProjectUserMapping.repository.ProjectUserMappingRepository;
import com.project.scheduling.scheduleBoard.dto.ScheduleBoardCreateRequest;
import com.project.scheduling.scheduleBoard.dto.ScheduleBoardReadResponse;
import com.project.scheduling.scheduleBoard.dto.ScheduleBoardUpdateRequest;
import com.project.scheduling.scheduleBoard.repository.ScheduleBoardRepository;
import com.project.scheduling.user.entity.ProjectUserMapping;
import com.project.scheduling.user.entity.ScheduleBoard;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleBoardService {

  private final ScheduleBoardRepository scheduleBoardRepository;
  private final ProjectUserMappingRepository projectUserMappingRepository;

  public List<ScheduleBoardReadResponse> getScheduleBoardListByProjectId(Long projectId) {
    ProjectUserMapping mappings = projectUserMappingRepository.findByProjectsId(projectId);

    if (mappings == null) {
      throw new IllegalArgumentException("mappings가 null입니다.");
    } else {
      return scheduleBoardRepository.findByProjectUserMappingId(mappings.getId()).stream()
          .map(ScheduleBoardReadResponse::getBoard)
          .collect(Collectors.toList());
    }
  }

  public ScheduleBoard createScheduleBoard(ScheduleBoardCreateRequest board) {

    ProjectUserMapping mapping = verificationMapping(board.getProjectUserMapping());

    ScheduleBoard schedule = ScheduleBoard.builder()
        .title(board.getTitle())
        .content(board.getContent())
        .start(board.getStart())
        .end(board.getEnd())
        .state(board.getState())
        .projectUserMapping(mapping)
        .build();
    return scheduleBoardRepository.save(schedule);

  }

  public ScheduleBoard updateScheduleBoard(ScheduleBoardUpdateRequest board) {
    verificationMapping(board.getProjectUserMapping());

    ScheduleBoard scheduleBoard = scheduleBoardRepository.findById(board.getId())
        .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다"));

    scheduleBoard.update(board.getTitle(), board.getContent(), board.getStart(),
        board.getEnd(), board.getState());
    return scheduleBoardRepository.save(scheduleBoard);

  }

  public HttpStatus deleteScheduleBoard(Long mappingId, Long boardId) {
    verificationMapping(mappingId);

    ScheduleBoard deleteBoard = scheduleBoardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 보드입니다."));

    scheduleBoardRepository.delete(deleteBoard);
    return HttpStatus.OK;
  }

  private ProjectUserMapping verificationMapping(Long mappingId) {
    return projectUserMappingRepository.findById(mappingId)
        .orElseThrow(() -> new IllegalArgumentException("접근 권한이 없음"));
  }

}

