package org.lab.model.project;

import org.lab.model.milestone.Milestone;
import org.lab.model.milestone.MilestoneStatus;
import org.lab.model.report.Report;
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

    private String description;

    private Set<User> qa = new HashSet<>();

    private LinkedList<Milestone> milestones = new LinkedList<>();

    private List<Report> reports = new ArrayList<>();


    private Milestone currentMilestone;

    /**
     * Создание проекта пользователем.
     *
     * @param user пользователь, от имени которого создаётся проект (он будет менеджером)
     */
    public Project(User user, String description) {

        projectId = UUID.randomUUID().toString();
        manager = user;
        this.description = description;
        user.addProject(this, new Manager());

    }

    /**
     * Добавление тимлида к проекту.
     */
    public void attachTeamLead(User manager, User teamLead) {

        if (!manager.equals(this.manager)) {

            String errorMessage = StringTemplate.STR."""
                User with name: \{manager.getFullName()}
                and id: \{manager.getId()}
                is not a manager of project with id: \{projectId}.
                Insufficient permissions to perform this action.
                """;
            System.out.println(errorMessage);
            return;
        }

        this.teamLead = teamLead;
        teamLead.addProject(this, new TeamLead());

    }

    /**
     * Добавление разработчика к проекту.
     */
    public void attachDeveloper(User manager, User developer) {

        if (!manager.equals(this.manager)) {
            String errorMessage = StringTemplate.STR."""
                User with name: \{manager.getFullName()}
                and id: \{manager.getId()}
                is not a manager of project with id: \{projectId}.
                Insufficient permissions to perform this action.
                """;
            System.out.println(errorMessage);
            return;
        }

        this.developers.add(developer);
        developer.addProject(this, new Developer());

    }

    /**
     * Добавление тестировщика к проекту.
     */
    public void attachQa(User manager, User qa) {

        if (!manager.equals(this.manager)) {
            String errorMessage = StringTemplate.STR."""
                User with name: \{manager.getFullName()}
                and id: \{manager.getId()}
                is not a manager of project with id: \{projectId}.
                Insufficient permissions to perform this action.
                """;
            System.out.println(errorMessage);
            return;
        }

        this.qa.add(qa);
        qa.addProject(this, new QA());

    }

    /**
     * Создать новый Milestone.
     *
     * @param start  начало milestone.
     * @param finish окончание milestone.
     */
    public void attachMilestone(User manager, LocalDate start, LocalDate finish) {

        if (!manager.equals(this.manager)) {
            String errorMessage = StringTemplate.STR."""
                User with name: \{manager.getFullName()}
                and id: \{manager.getId()}
                is not a manager of project with id: \{projectId}.
                Insufficient permissions to perform this action.
                """;
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
            String errorMessage = StringTemplate.STR."""
                User with name: \{manager.getFullName()}
                and id: \{manager.getId()}
                is not a manager of project with id: \{projectId}.
                Insufficient permissions to perform this action.
                """;
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

        if (!user.equals(this.manager) && !(Objects.nonNull(teamLead) && teamLead.equals(user))) {
            String errorMessage = StringTemplate.STR."""
                User with name: \{manager.getFullName()}
                and id: \{manager.getId()}
                is not a manager of project with id: \{projectId}.
                Insufficient permissions to perform this action.
                """;
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

    /**
     * Здесь разработчик или тестировщик может создать bug-report
     */
    public Report addReport(User createdUser, User fixedUser, String description) {

        Report report = new Report(description, createdUser, fixedUser, this);
        reports.add(report);

        return report;

    }

    public ProjectStatus getStats() {

        int totalTickets = milestones.stream()
                .mapToInt(m -> m.getTickets().size())
                .sum();

        int completedTickets = milestones.stream()
                .flatMap(m -> m.getTickets().stream())
                .filter(t -> t.getStatus() == TicketStatus.COMPLETED)
                .toList()
                .size();

        return new ProjectStatus(
                description,
                totalTickets,
                completedTickets,
                totalTickets - completedTickets,
                developers.size(),
                qa.size()
        );
    }


    public Set<User> getDevelopers() {
        return developers;
    }

    public String getDescription() {
        return description;
    }

    public LinkedList<Milestone> getMilestones() {
        return milestones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(projectId, project.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectId);
    }

    public List<Report> getReports() {
        return reports;
    }

}
