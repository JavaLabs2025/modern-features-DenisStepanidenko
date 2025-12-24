package org.lab.model.ticket;

import org.lab.model.milestone.Milestone;
import org.lab.model.project.Project;
import org.lab.model.user.User;

import java.util.*;

public class Ticket {

    private String ticketId;

    private Project project;

    private Milestone milestone;

    private String description;

    private Set<User> developers = new HashSet<>();

    private int count = 0;

    private TicketStatus status;

    /**
     * TeamLead или менеджер проекта, кто создал тикет.
     */
    private User createdUser;

    public Ticket(Project project, Milestone milestone, String description, User createdUser) {
        ticketId = UUID.randomUUID().toString();
        this.project = project;
        this.milestone = milestone;
        this.description = description;
        this.createdUser = createdUser;
        status = TicketStatus.NEW;
    }

    /**
     * Посмотреть статус тикета
     */
    public TicketStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Привязка разработчика к тикету.
     */
    public void addDeveloper(User createdUser, User developer) {

        if (!createdUser.equals(this.createdUser)) {

            String errorMessage = "Insufficient permissions to perform this action.";
            System.out.println(errorMessage);
            return;

        }


        if (!project.getDevelopers().contains(developer)) {

            String errorMessage = "The developer is not part of the project they are attached to this ticket.";
            System.out.println(errorMessage);
            return;
        }


        developers.add(developer);
        status = TicketStatus.ACCEPTED;
        count++;
    }

    /**
     * Разработчик выполняет задачу в тикете.
     */
    public void activeTicket(User developer) {

        if (!developers.contains(developer)) {
            String errorMessage = "Insufficient permissions to perform this action.";
            System.out.println(errorMessage);
            return;
        }

        if (status == TicketStatus.COMPLETED) {

            String errorMessage = "The task in the ticket has already been completed.";
            System.out.println(errorMessage);
            return;

        }

        status = TicketStatus.ACTIVE;


    }

    /**
     * Разработчик выполнил задачу в тикете.
     */
    public void finishTicket(User developer) {

        if (!developers.contains(developer)) {
            String errorMessage = "Insufficient permissions to perform this action.";
            System.out.println(errorMessage);
            return;
        }

        count--;
        if (count == 0) {
            status = TicketStatus.COMPLETED;
        }

    }

    public Set<User> getDevelopers() {
        return developers;
    }

}
