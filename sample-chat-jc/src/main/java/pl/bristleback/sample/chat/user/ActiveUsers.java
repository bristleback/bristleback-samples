package pl.bristleback.sample.chat.user;

import org.springframework.stereotype.Component;
import pl.bristleback.sample.chat.exception.UserExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActiveUsers {

  private Map<String, ChatUser> activeUsers = new HashMap<String, ChatUser>();

  public void removeUser(String userName) {
    activeUsers.remove(userName);
  }

  public void registerUser(String userName, ChatUser user) {
    if (activeUsers.containsKey(userName)) {
      throw new UserExistsException(userName);
    }
    user.setNickname(userName);
    activeUsers.put(userName, user);
  }

  public List<ChatUser> getUsers() {
    return new ArrayList<ChatUser>(activeUsers.values());
  }
}
