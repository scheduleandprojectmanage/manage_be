package com.project.scheduling.scheduleBoard.controller;

import com.project.scheduling.scheduleBoard.dto.ScheduleBoardCreateRequest;
import com.project.scheduling.scheduleBoard.dto.ScheduleBoardReadResponse;
import com.project.scheduling.scheduleBoard.dto.ScheduleBoardUpdateRequest;
import com.project.scheduling.scheduleBoard.service.ScheduleBoardService;
import com.project.scheduling.user.entity.ScheduleBoard;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/post")
  public ResponseEntity<String> createScheduleBoard(@RequestBody ScheduleBoardCreateRequest scheduleBoardCreateRequest){
    try {
                ScheduleBoard board = scheduleBoardService.createScheduleBoard(scheduleBoardCreateRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body("일정 게시판 생성 완료!");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
            }
  }

  @PutMapping("/update")
  public ResponseEntity<String> updateScheduleBoard(@RequestBody ScheduleBoardUpdateRequest scheduleBoardUpdateRequest){
    try {
                ScheduleBoard board = scheduleBoardService.updateScheduleBoard(scheduleBoardUpdateRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body("일정 게시판 생성 완료!");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
            }
  }
}
