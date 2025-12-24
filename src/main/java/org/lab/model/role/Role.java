package org.lab.model.role;


import org.lab.model.permission.Permission;

/**
 * Роли участников проекта.
 */
public sealed interface Role permits Manager, Developer, TeamLead, QA {

    String getRoleName();

    default boolean checkPermission(Permission permission) {

        return switch (this) {
            case Manager _ -> permission.allowedForManager();
            case TeamLead _ -> permission.allowedForTeamLead();
            case Developer _ -> permission.allowedForDeveloper();
            case QA _ -> permission.allowedForQA();
        };

    }

}

