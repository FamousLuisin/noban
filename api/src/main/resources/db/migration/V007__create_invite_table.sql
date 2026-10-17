CREATE TABLE tb_invites (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    project_id UUID NOT NULL,
    sender_id UUID NOT NULL,
    receiver_id UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES tb_projects(id),
    CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES tb_users(id),
    CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES tb_users(id)
);