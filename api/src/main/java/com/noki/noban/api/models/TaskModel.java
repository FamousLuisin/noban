package com.noki.noban.api.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity @Table (name = "tb_tasks")
public class TaskModel {

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
    
    @Id @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne 
    @JoinColumn(name = "created_by")
    private UserModel createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private UserModel assignedTo;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private StageModel stage;

    @Column(name = "created_at")
    @CreationTimestamp 
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp 
    private LocalDateTime updatedAt;

    public TaskModel() {
    }

    public TaskModel(String title, String description, Priority priority, UserModel createdBy, UserModel assignedTo, StageModel stage) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.stage = stage;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public UserModel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserModel createdBy) {
        this.createdBy = createdBy;
    }

    public UserModel getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserModel assignedTo) {
        this.assignedTo = assignedTo;
    }

    public StageModel getStage() {
        return stage;
    }

    public void setStage(StageModel stage) {
        this.stage = stage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        TaskModel other = (TaskModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
