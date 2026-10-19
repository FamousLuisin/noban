CREATE TABLE tb_stage (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    position INT NOT NULL,
    project_id UUID NOT NULL,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES tb_projects(id) ON DELETE CASCADE
);