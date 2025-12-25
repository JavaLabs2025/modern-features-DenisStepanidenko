package org.lab.model.report;

import org.lab.model.project.Project;
import org.lab.model.user.User;

import java.util.Objects;
import java.util.UUID;

public class Report {

    private String reportId;

    private String description;

    /**
     * Пользователь, кто создал сообщение об ошибки
     */
    private User createdUser;

    /**
     * Пользователь, кто исправил сообщение об ошибки
     */
    private User fixedUser;

    private Project project;

    private ReportStatus status;

    public Report(String description, User createdUser, User fixedUser, Project project) {
        reportId = UUID.randomUUID().toString();
        this.description = description;
        this.createdUser = createdUser;
        this.project = project;
        this.fixedUser = fixedUser;
        status = ReportStatus.NEW;
    }


    /**
     * Разработчик устраняет сообщение об ошибке.
     */
    public void fixedReport(User developer) {

        if (!project.getDevelopers().contains(developer)) {

            String errorMessage = "Insufficient permissions to perform this action.";
            System.out.println(errorMessage);
            return;

        }

        if (!fixedUser.equals(developer)) {

            String errorMessage = "Insufficient permissions to perform this action.";
            System.out.println(errorMessage);
            return;

        }

        status = ReportStatus.FIXED;

    }

    /**
     * Тестировщик проверяет как исправил bug-report разработчик.
     */
    public void checkReport(User user) {

        if (!user.equals(createdUser)) {

            String errorMessage = "Insufficient permissions to perform this action.";
            System.out.println(errorMessage);
            return;
        }

        status = ReportStatus.TESTED;

    }

    /**
     * Тестировщик закрывает bug-report
     */
    public void closeReport(User user) {

        if (!user.equals(createdUser)) {

            String errorMessage = "Insufficient permissions to perform this action.";
            System.out.println(errorMessage);
            return;
        }

        status = ReportStatus.CLOSED;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(reportId, report.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reportId);
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public String getDescription() {
        return description;
    }

    public User getFixedUser() {
        return fixedUser;
    }

    @Override
    public String toString() {
        return STR."""
             Report [
                ID: \{reportId}
                Description: \{description}
                Status:  \{status}
                Created by: \{createdUser.getFullName()}
                Assigned to: \{fixedUser.getFullName()}
                Project: \{project.getDescription()}
            ]
            """;
    }


}
