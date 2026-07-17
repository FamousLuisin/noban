package com.noki.noban.api.mocks;

import java.util.List;

import com.noki.noban.api.models.RoleModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.models.RoleModel.RoleType;

public class UserModelMock {
    
    public UserModel getUserModel(){
        return new UserModel("teste", "teste@email", "Teste123/", List.of(new RoleModel(RoleType.ROLE_USER)));
    }
}
