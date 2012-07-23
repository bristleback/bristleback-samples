package pl.bristleback.sample.chat.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.bristleback.server.bristle.api.action.DefaultAction;
import pl.bristleback.server.bristle.api.annotations.Action;
import pl.bristleback.server.bristle.api.annotations.AnnotatedActionClass;
import pl.bristleback.sample.chat.action.client.ChatClientAction;
import pl.bristleback.sample.chat.user.ChatUser;
import pl.bristleback.sample.chat.vo.ChatText;

@Controller
@AnnotatedActionClass(name = "SendText")
public class SendTextAction implements DefaultAction<ChatUser, ChatText> {

  @Autowired
  private ChatClientAction chatClientAction;

  @Action
  public Void executeDefault(ChatUser user, ChatText message) {
    chatClientAction.sendText(user, message);
    return null;
  }
}
