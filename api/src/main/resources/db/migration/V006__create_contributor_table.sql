CREATE TABLE tb_contributors (
    user_id UUID NOT NULL,
    project_id UUID NOT NULL,
    role VARCHAR(50) NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    left_at TIMESTAMP,
    PRIMARY KEY (user_id, project_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE CASCADE,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES tb_projects(id) ON DELETE CASCADE
);