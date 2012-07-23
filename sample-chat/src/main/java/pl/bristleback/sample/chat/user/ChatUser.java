package pl.bristleback.sample.chat.user;

import pl.bristleback.server.bristle.engine.base.users.DefaultUser;

public class ChatUser extends DefaultUser {

  private String nickname;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
}
