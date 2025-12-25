package org.lab.model.project;

public record ProjectStatus(
        String projectName,
        int totalTickets,
        int completedTickets,
        int openTickets,
        int totalDevelopers,
        int totalQA
) {

    public double completionPercentage() {
        return totalTickets > 0 ? (completedTickets * 100.0) / totalTickets : 0.0;
    }

    @Override
    public String toString() {
        return STR."""

            PROJECT STATUS REPORT
            Project: \{projectName}

            TICKETS STATISTICS:
            Total tickets: \{totalTickets}
            Completed: \{completedTickets}
            Open: \{openTickets}
            Completion rate: \{String.format("%.1f", completionPercentage())}%
            TEAM COMPOSITION:
            Developers: \{totalDevelopers}
            QA Engineers: \{totalQA}
            """;
    }

}