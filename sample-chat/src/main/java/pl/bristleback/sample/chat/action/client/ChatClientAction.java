package pl.bristleback.sample.chat.action.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bristleback.server.bristle.api.annotations.ClientAction;
import pl.bristleback.server.bristle.api.annotations.ClientActionClass;
import pl.bristleback.sample.chat.user.ActiveUsers;
import pl.bristleback.sample.chat.user.ChatUser;
import pl.bristleback.sample.chat.vo.ChatText;

import java.util.List;


@Component
@ClientActionClass
public class ChatClientAction {

  @Autowired
  private ActiveUsers activeUsers;

  @ClientAction
  public List<ChatUser> newUser(String userName, List<ChatUser> actualUsers) {
    return activeUsers.getUsers();
  }

  @ClientAction
  public List<ChatUser> sendText(String userName, String userId, ChatText text) {
    return activeUsers.getUsers();
  }

  @ClientAction
  public List<ChatUser> userLeftChat(String s) {
    return activeUsers.getUsers();
  }
}
