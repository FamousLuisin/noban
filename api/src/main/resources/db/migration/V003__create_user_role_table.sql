CREATE TABLE tb_user_roles (
    user_id UUID NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_users(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES tb_roles(id)
);