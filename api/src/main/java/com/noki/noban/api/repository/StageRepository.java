package com.noki.noban.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noki.noban.api.models.StageModel;

public interface StageRepository extends JpaRepository<StageModel, Integer> {
    
    Optional<StageModel> findFirstByProjectIdOrderByPositionDesc(UUID projectId);

    Optional<StageModel> findByIdAndProjectId(Integer stageId, UUID projectId);
}
