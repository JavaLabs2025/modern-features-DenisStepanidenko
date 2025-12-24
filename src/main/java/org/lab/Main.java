import org.lab.model.project.Project;
import org.lab.model.user.User;
import org.lab.service.UserService;

import java.io.IO;

void main() {

    UserService userService = new UserService();

    User user = new User("Denis Stepanidenko");
    User teamLead = new User("Ilya");

    Project project = Project.create(user);
    project.attachTeamLead(teamLead, user);

    System.out.println();

}

