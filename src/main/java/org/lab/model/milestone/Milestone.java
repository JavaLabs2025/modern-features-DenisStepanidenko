package org.lab.model.milestone;

import org.lab.model.ticket.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Milestone {

    private String milestoneId;

    private String projectId;

    private LocalDate startDate;

    private LocalDate endDate;

    private MilestoneStatus status;

    private List<Ticket> tickets = new ArrayList<>();

    public Milestone(String projectId, LocalDate startDate, LocalDate endDate) {
        milestoneId = UUID.randomUUID().toString();
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
        status = MilestoneStatus.OPEN;
    }

    public MilestoneStatus getStatus() {
        return status;
    }

    public void setStatus(MilestoneStatus status) {
        this.status = status;
    }

    public String getMilestoneId() {
        return milestoneId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "startDate=" + startDate +
                ", milestoneId='" + milestoneId + '\'' +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}
