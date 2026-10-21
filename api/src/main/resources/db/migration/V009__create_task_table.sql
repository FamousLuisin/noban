CREATE TABLE tb_tasks (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(50) NOT NULL,
    created_by UUID NOT NULL,
    assigned_to UUID,
    stage_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES tb_users(id),
    CONSTRAINT fk_assigned_to FOREIGN KEY (assigned_to) REFERENCES tb_users(id),
    CONSTRAINT fk_stage_id FOREIGN KEY (stage_id) REFERENCES tb_stage(id)
);