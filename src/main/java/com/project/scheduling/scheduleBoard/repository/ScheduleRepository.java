package com.project.scheduling.scheduleBoard.repository;

import com.project.scheduling.scheduleBoard.dto.ScheduleBoardReadResponse;
import com.project.scheduling.user.entity.ScheduleBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleBoard, Long> {

  List<ScheduleBoard> findByProjectUserMappingId(Long mappingId);
}
