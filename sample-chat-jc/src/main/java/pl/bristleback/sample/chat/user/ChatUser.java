package pl.bristleback.sample.chat.user;

import pl.bristleback.server.bristle.api.users.IdentifiedUser;

import java.util.UUID;

public class ChatUser implements IdentifiedUser {

  private String id;
  private String nickname;

  public ChatUser() {
    this.id = UUID.randomUUID().toString();
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public boolean isLogged() {
    return nickname != null; //just a simple implementation
  }
}
