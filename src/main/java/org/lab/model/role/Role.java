package org.lab.model.role;



/**
 * Роли участников проекта.
 */
public sealed interface Role permits Manager, Developer, TeamLead, QA {

    String getRoleName();

}

