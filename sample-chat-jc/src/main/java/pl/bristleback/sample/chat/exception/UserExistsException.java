package pl.bristleback.sample.chat.exception;


public class UserExistsException extends RuntimeException {
  private String userName;

  public UserExistsException(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }
}
