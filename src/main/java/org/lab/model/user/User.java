package org.lab.model.user;

import org.lab.model.role.Role;

import java.util.*;


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
    private Map<String, Set<Role>> roles = new HashMap<>();


    public User(String fullName) {
        this.fullName = fullName;
    }


    /**
     * Назначить роль в проекте.
     */
    public void addProject(String projectId, Role role) {

        Set<Role> currentRoles = roles.computeIfAbsent(projectId, k -> new HashSet<>());
        currentRoles.add(role);

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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<String, Set<Role>> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Set<Role>> roles) {
        this.roles = roles;
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





