package pl.bristleback.sample.chat.action;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.bristleback.sample.chat.action.client.ChatClientAction;
import pl.bristleback.sample.chat.user.ActiveUsers;
import pl.bristleback.sample.chat.user.ChatUser;
import pl.bristleback.server.bristle.api.annotations.Action;
import pl.bristleback.server.bristle.api.annotations.ActionClass;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@ActionClass(name = "JoinChat")
public class JoinChatAction {

  @Autowired
  private ActiveUsers activeUsers;

  @Autowired
  private ChatClientAction chatClientAction;

  @Action
  public List<ChatUser> join(ChatUser user, @NotNull @Length(min = 2) String userName) {
    activeUsers.registerUser(userName, user);

    chatClientAction.newUser(userName, activeUsers.getUsers());

    return activeUsers.getUsers();
  }
}
