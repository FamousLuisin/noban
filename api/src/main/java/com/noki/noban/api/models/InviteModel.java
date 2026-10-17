package com.noki.noban.api.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.noki.noban.api.models.ContributorsModel.RoleType;

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

@Entity @Table(name = "tb_invites")
public class InviteModel {

    public enum Status {
        PENDING, ACCEPTED, DECLINED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "sent_at")
    @CreationTimestamp 
    private LocalDateTime sentAt;
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserModel sender;

    @ManyToOne 
    @JoinColumn(name = "receiver_id")
    private UserModel receiver;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public InviteModel() {
    }

    public InviteModel(String message, Status status, ProjectModel project, UserModel sender, UserModel receiver, RoleType role) {
        this.message = message;
        this.status = status;
        this.project = project;
        this.sender = sender;
        this.receiver = receiver;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public ProjectModel getProject() {
        return project;
    }

    public void setProject(ProjectModel project) {
        this.project = project;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public UserModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserModel receiver) {
        this.receiver = receiver;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
