package com.noki.noban.api.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Table(name = "tb_contributors") @Entity
public class ContributorsModel {
    
    @EmbeddedId
    private ContributorsId id;

    @ManyToOne 
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp
    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    public enum RoleType {
        OWNER, COMMON, ADMIN
    }

    public ContributorsModel() {
    }

    public ContributorsModel(UserModel user, ProjectModel project, RoleType role) {
        this.id = new ContributorsId(user, project);
        this.user = user;
        this.project = project;
        this.role = role;
    }

    public ContributorsId getId() {
        return id;
    }

    public void setId(ContributorsId id) {
        this.id = id;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((joinedAt == null) ? 0 : joinedAt.hashCode());
        result = prime * result + ((leftAt == null) ? 0 : leftAt.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContributorsModel other = (ContributorsModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        if (joinedAt == null) {
            if (other.joinedAt != null)
                return false;
        } else if (!joinedAt.equals(other.joinedAt))
            return false;
        if (leftAt == null) {
            if (other.leftAt != null)
                return false;
        } else if (!leftAt.equals(other.leftAt))
            return false;
        return true;
    }
}
