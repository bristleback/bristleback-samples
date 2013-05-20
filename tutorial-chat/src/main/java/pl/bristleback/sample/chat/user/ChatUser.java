  package pl.bristleback.sample.chat.user;

  import pl.bristleback.server.bristle.engine.user.BaseUserContext;


  public class ChatUser extends BaseUserContext {

    private String nickname;

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
