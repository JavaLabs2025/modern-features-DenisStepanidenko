

import org.lab.model.milestone.MilestoneStatus;
import org.lab.model.project.Project;
import org.lab.model.report.Report;
import org.lab.model.ticket.Ticket;
import org.lab.model.user.User;
import org.lab.service.UserService;



import java.time.LocalDate;

void main() {

    UserService userService = new UserService();
    User manager = userService.registerUser("Manager");



    Project project1 = new Project(manager, "Valhalla");


    User developer1 = userService.registerUser("Developer1");
    User developer2 = userService.registerUser("Developer2");
    User developer3 = userService.registerUser("Developer3");

    User qa1 = userService.registerUser("QA1");
    User qa2 = userService.registerUser("QA2");
    User qa3 = userService.registerUser("QA3");

    User teamLead = userService.registerUser("TeamLead");



    project1.attachTeamLead(manager, teamLead);

    project1.attachDeveloper(manager, developer1);
    project1.attachDeveloper(manager, developer2);
    project1.attachDeveloper(manager, developer3);

    project1.attachQa(manager, qa1);
    project1.attachQa(manager, qa2);
    project1.attachQa(manager, qa3);


    project1.attachMilestone(manager, LocalDate.now(), LocalDate.now().plusDays(15));
    project1.changeMilestoneStatus(manager, MilestoneStatus.ACTIVE);

    Ticket ticket1 = project1.addTicket(manager, "Ticket1");
    Ticket ticket2 = project1.addTicket(manager, "Ticket2");
    Ticket ticket3 = project1.addTicket(teamLead, "Ticket3");

    System.out.println(project1.getStats());


    ticket1.addDeveloper(manager, developer1);
    ticket2.addDeveloper(manager, developer2);
    ticket3.addDeveloper(teamLead, developer3);

    ticket1.activeTicket(developer1);
    ticket2.activeTicket(developer2);
    ticket3.activeTicket(developer3);

    ticket1.finishTicket(developer1);
    ticket2.finishTicket(developer2);
    ticket3.finishTicket(developer3);



    Report report1 = project1.addReport(qa1, developer1, "bug-report1");
    Report report2 = project1.addReport(qa2, developer2, "bug-report2");
    Report report3 = project1.addReport(qa3, developer3, "bug-report3");

    report1.fixedReport(developer1);
    report2.fixedReport(developer2);
    report3.fixedReport(developer3);

    report1.checkReport(qa1);
    report2.checkReport(qa2);
    report3.checkReport(qa3);

    report1.closeReport(qa1);
    report2.closeReport(qa2);
    report3.closeReport(qa3);


 
    manager.viewAllProjects();
    teamLead.viewAllProjects();
    developer1.viewAllProjects();
    developer2.viewAllProjects();
    developer3.viewAllProjects();
    qa1.viewAllProjects();
    qa2.viewAllProjects();
    qa3.viewAllProjects();

    manager.viewAllTasks();
    teamLead.viewAllTasks();
    developer1.viewAllTasks();
    developer2.viewAllTasks();
    developer3.viewAllTasks();
    qa1.viewAllTasks();
    qa2.viewAllTasks();
    qa3.viewAllTasks();

    developer1.viewAllReport();
    developer2.viewAllReport();
    developer3.viewAllReport();



    project1.changeMilestoneStatus(manager, MilestoneStatus.CLOSE);
    System.out.println(project1.getMilestones());

}

