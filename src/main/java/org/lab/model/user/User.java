package org.lab.model.user;

import org.lab.model.project.Project;
import org.lab.model.role.Role;

import java.util.*;
import java.util.stream.Collectors;


public class User {

    /**
     * Уникальные идентификатор пользователя в системе.
     */
    private String id;

    /**
     * Полное имя пользователя.
     */
    private String fullName;

    /**
     * <projectId, список ролей>
     * Список ролей в разных проектах. (допускается в одном проекте иметь несколько ролей)
     */
    private Map<Project, Set<Role>> roles = new HashMap<>();


    public User(String fullName) {
        this.fullName = fullName;
    }


    /**
     * Назначить роль в проекте.
     */
    public void addProject(Project project, Role role) {

        Set<Role> currentRoles = roles.computeIfAbsent(project, k -> new HashSet<>());
        currentRoles.add(role);

    }

    /**
     * Просмотреть все проекты
     */
    public void viewAllProjects() {

        for (Map.Entry<Project, Set<Role>> entry : roles.entrySet()) {

            System.out.printf(
                    "Project: %s | Roles: %s%n",
                    entry.getKey().getDescription(),
                    entry.getValue().stream()
                            .map(Role::getRoleName)
                            .collect(Collectors.joining(", "))
            );

        }

    }

    /**
     * Просмотреть все задачи
     */
    public void viewAllTasks() {

        System.out.println("User: " + fullName);

        roles.forEach((project, _) -> {
            System.out.println("Project: " + project.getDescription());


            project.getMilestones().forEach(milestone -> {
                System.out.println("Milestone " + milestone.getMilestoneId());
                System.out.println("Tasks:");

                milestone.getTickets().stream()
                        .filter(ticket -> ticket.getDevelopers().contains(this))
                        .map(ticket -> "Description: " + ticket.getDescription())
                        .forEach(System.out::println);

                System.out.println("--------------");
            });


            System.out.println("Reports");
            project.getReports().stream()
                    .filter(report -> report.getCreatedUser().equals(this))
                    .map(report -> "Description: " + report.getDescription())
                    .forEach(System.out::println);

            System.out.println("--------------");
        });


    }

    public void viewAllReport() {

        System.out.println("User: " + fullName);

        roles.forEach((project, _) -> {
            System.out.println("Project: " + project.getDescription());

            System.out.println("Reports");
            project.getReports().stream()
                    .filter(report -> report.getFixedUser().equals(this))
                    .map(report -> "Description: " + report.getDescription())
                    .forEach(System.out::println);

            System.out.println("--------------");
        });

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}





