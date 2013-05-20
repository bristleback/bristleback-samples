package pl.bristleback.sample.chat.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.bristleback.sample.chat.action.client.ChatClientAction;
import pl.bristleback.sample.chat.user.ActiveUsers;
import pl.bristleback.sample.chat.user.ChatUser;
import pl.bristleback.server.bristle.api.action.DefaultAction;
import pl.bristleback.server.bristle.api.annotations.Action;
import pl.bristleback.server.bristle.api.annotations.ActionClass;
import pl.bristleback.server.bristle.serialization.system.annotation.Bind;

import java.util.List;

@Controller
@ActionClass(name = "JoinChat")
public class JoinChatAction implements DefaultAction<ChatUser, String> {

  @Autowired
  private ActiveUsers activeUsers;

  @Autowired
  private ChatClientAction chatClientAction;

  @Action
  public List<ChatUser> executeDefault(ChatUser user, @Bind(required = true) String userName) {
    activeUsers.registerUser(userName, user);

    chatClientAction.newUser(userName, activeUsers.getUsers());

    return activeUsers.getUsers();
  }
}
