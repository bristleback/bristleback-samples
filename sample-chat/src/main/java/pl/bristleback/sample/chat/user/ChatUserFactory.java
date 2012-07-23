package pl.bristleback.sample.chat.user;

import org.springframework.stereotype.Component;
import pl.bristleback.server.bristle.api.users.IdentifiedUser;
import pl.bristleback.server.bristle.api.users.UserFactory;

@Component
public class ChatUserFactory implements UserFactory {

  @Override
  public IdentifiedUser createNewUser() {
    return new ChatUser();
  }
}
