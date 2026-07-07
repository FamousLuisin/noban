package com.noki.noban.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noki.noban.api.models.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Long>{
    
    RoleModel findByName(RoleModel.RoleType name);
}
