package com.noki.noban.api.mocks;

import com.noki.noban.api.models.RoleModel;
import com.noki.noban.api.models.RoleModel.RoleType;

public class RoleMock {
    
    public RoleModel getRoleUser(){
        return new RoleModel(RoleType.ROLE_USER);
    }

    public RoleModel getRoleAdmin(){
        return new RoleModel(RoleType.ROLE_ADMIN);
    }
}
