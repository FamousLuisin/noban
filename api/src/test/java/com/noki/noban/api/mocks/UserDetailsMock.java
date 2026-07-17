package com.noki.noban.api.mocks;

import java.util.List;

import com.noki.noban.api.models.RoleModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.security.user.CustomUserDetails;

public class UserDetailsMock {

    public CustomUserDetails getUserDetails(RoleModel roleModel) {
        UserModel user = 
            new UserModel("teste", "teste@email", "senha123!", List.of(roleModel));

        return new CustomUserDetails(user);
    }
}
