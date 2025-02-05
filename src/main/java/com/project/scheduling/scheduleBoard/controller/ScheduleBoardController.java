package com.project.scheduling.scheduleBoard.controller;

import com.project.scheduling.scheduleBoard.dto.ScheduleBoardReadResponse;
import com.project.scheduling.scheduleBoard.service.ScheduleBoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cycleManager")
public class ScheduleBoardController {
  private final ScheduleBoardService scheduleBoardService;

  @GetMapping("/")
  public List<ScheduleBoardReadResponse> getLifeCycle(@RequestParam Long projectId){
    return scheduleBoardService.getScheduleBoardListByProjectId(projectId);
  }

}
