package pl.bristleback.sample.chat.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bristleback.sample.chat.action.client.ChatClientAction;
import pl.bristleback.sample.chat.user.ActiveUsers;
import pl.bristleback.sample.chat.user.ChatUser;
import pl.bristleback.server.bristle.api.ConnectionStateListener;

@Component
public class ChatConnectionStateListener implements ConnectionStateListener<ChatUser> {

  @Autowired
  private ChatClientAction chatClientAction;

  @Autowired
  private ActiveUsers activeUsers;

  @Override
  public void userConnected(ChatUser user) {
  }

  @Override
  public void userDisconnected(ChatUser user) {
    if (user.isLogged()) {
      activeUsers.removeUser(user.getNickname());
      chatClientAction.userLeftChat(user.getNickname(), activeUsers.getUsers());
    }
  }
}
