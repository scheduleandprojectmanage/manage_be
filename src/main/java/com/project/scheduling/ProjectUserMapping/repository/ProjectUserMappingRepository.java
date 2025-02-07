package com.project.scheduling.ProjectUserMapping.repository;

import com.project.scheduling.user.entity.ProjectUserMapping;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserMappingRepository extends JpaRepository<ProjectUserMapping, Long> {
  ProjectUserMapping findByProjectsId(Long projectId);

}
