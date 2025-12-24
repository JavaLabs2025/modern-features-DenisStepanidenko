package org.lab.model.project;

import org.lab.model.milestone.Milestone;
import org.lab.model.milestone.MilestoneStatus;
import org.lab.model.role.Developer;
import org.lab.model.role.Manager;
import org.lab.model.role.QA;
import org.lab.model.role.TeamLead;
import org.lab.model.ticket.Ticket;
import org.lab.model.ticket.TicketStatus;
import org.lab.model.user.User;

import java.time.LocalDate;
import java.util.*;

public class Project {

    private String projectId;

    private User manager;

    private User teamLead;

    private Set<User> developers = new HashSet<>();

    private Set<User> qa = new HashSet<>();

    private LinkedList<Milestone> milestones = new LinkedList<>();

    private Milestone currentMilestone;

    private Project() {
    }

    /**
     * Создание проекта пользователем.
     *
     * @param user пользователь, от имени которого создаётся проект (он будет менеджером)
     */
    public static Project create(User user) {

        Project project = new Project();
        project.projectId = UUID.randomUUID().toString();
        project.manager = user;
        user.addProject(project.projectId, new Manager());

        return project;
    }

    /**
     * Добавление тимлида к проекту.
     */
    public void attachTeamLead(User manager, User teamLead) {

        if (!manager.equals(this.manager)) {

            String errorMessage = String.format("User with name: %s and id: %s is not a manager of project with id: %s. Insufficient permissions to perform this action.", manager.getFullName(), manager.getId(), projectId);
            System.out.println(errorMessage);
            return;
        }

        this.teamLead = teamLead;
        teamLead.addProject(projectId, new TeamLead());

    }

    /**
     * Добавление разработчика к проекту.
     */
    public void attachDeveloper(User manager, User developer) {

        if (!manager.equals(this.manager)) {
            String errorMessage = String.format("User with name: %s and id: %s is not a manager of project with id: %s. Insufficient permissions to perform this action.", manager.getFullName(), manager.getId(), projectId);
            System.out.println(errorMessage);
            return;
        }

        this.developers.add(developer);
        developer.addProject(projectId, new Developer());

    }

    /**
     * Добавление тестировщика к проекту.
     */
    public void attachQa(User manager, User qa) {

        if (!manager.equals(this.manager)) {
            String errorMessage = String.format("User with name: %s and id: %s is not a manager of project with id: %s. Insufficient permissions to perform this action.", manager.getFullName(), manager.getId(), projectId);
            System.out.println(errorMessage);
            return;
        }

        this.qa.add(qa);
        qa.addProject(projectId, new QA());

    }

    /**
     * Создать новый Milestone.
     *
     * @param start  начало milestone.
     * @param finish окончание milestone.
     */
    public void attachMilestone(User manager, LocalDate start, LocalDate finish) {

        if (!manager.equals(this.manager)) {
            String errorMessage = String.format("User with name: %s and id: %s is not a manager of project with id: %s. Insufficient permissions to perform this action.", manager.getFullName(), manager.getId(), projectId);
            System.out.println(errorMessage);
            return;
        }

        if (Objects.nonNull(currentMilestone)) {
            System.out.println("The current milestone has not yet ended, so you cannot create a new one.");
            return;
        }

        Milestone milestone = new Milestone(projectId, start, finish);
        currentMilestone = milestone;
        milestones.push(milestone);

    }

    /**
     * Изменить статус текущего milestone
     */
    public void changeMilestoneStatus(User manager, MilestoneStatus newStatus) {

        if (!manager.equals(this.manager)) {
            String errorMessage = String.format("User with name: %s and id: %s is not a manager of project with id: %s. Insufficient permissions to perform this action.", manager.getFullName(), manager.getId(), projectId);
            System.out.println(errorMessage);
            return;
        }

        if (Objects.isNull(currentMilestone)) {
            System.out.println("First, create the current milestone");
            return;
        }


        if (newStatus == MilestoneStatus.CLOSE) {

            for (Ticket ticket : currentMilestone.getTickets()) {

                if (ticket.getStatus() != TicketStatus.COMPLETED) {

                    System.out.println("You cannot close the milestone because not all tickets have been completed.");
                    return;
                }

            }

            currentMilestone.setStatus(newStatus);
            currentMilestone = null;
            return;

        }


        currentMilestone.setStatus(newStatus);

    }

    /**
     * Создание нового тикета (прикрепляется к текущему milestone)
     */
    public Ticket addTicket(User user, String description) {

        if (!user.equals(this.manager) || !(Objects.nonNull(teamLead) && teamLead.equals(user))) {
            String errorMessage = String.format("User with name: %s and id: %s is not a manager or teamLead of project with id: %s. Insufficient permissions to perform this action.", user.getFullName(), user.getId(), projectId);
            System.out.println(errorMessage);
            return null;
        }

        if (Objects.isNull(currentMilestone)) {
            System.out.println("First, create the current milestone");
            return null;
        }

        Ticket ticket = new Ticket(this, currentMilestone, description, user);

        currentMilestone.getTickets().add(ticket);

        return ticket;

    }

    public Set<User> getDevelopers() {
        return developers;
    }
}
