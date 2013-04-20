package pl.bristleback.scrumtable.actions.client;

import org.springframework.stereotype.Component;
import pl.bristleback.scrumtable.vo.Widget;
import pl.bristleback.server.bristle.api.action.SendCondition;
import pl.bristleback.server.bristle.api.annotations.ClientAction;
import pl.bristleback.server.bristle.api.annotations.ClientActionClass;
import pl.bristleback.server.bristle.api.annotations.Ignore;
import pl.bristleback.server.bristle.engine.user.BaseUserContext;
import pl.bristleback.server.bristle.security.authorisation.conditions.AllUsersCondition;

import java.util.List;

@Component
@ClientActionClass
public class WidgetClientAction {


  @ClientAction
  public SendCondition newWidget(Widget newWidget) {
    return AllUsersCondition.getInstance();
  }

  @ClientAction
  public SendCondition resizeWidget(Widget widget) {
    return AllUsersCondition.getInstance();
  }

  @ClientAction
  public SendCondition moveWidget(Widget widget) {
    return AllUsersCondition.getInstance();
  }

  @ClientAction
  public SendCondition editWidget(Widget widget) {
    return AllUsersCondition.getInstance();
  }

  @ClientAction
  public SendCondition lockWidget(Widget widget) {
    return AllUsersCondition.getInstance();
  }

  @ClientAction
  public SendCondition unlockWidget(Widget widget) {
    return AllUsersCondition.getInstance();
  }

  @ClientAction
  public SendCondition clearWidgets() {
    return AllUsersCondition.getInstance();
  }

  @ClientAction
  public BaseUserContext widgetsList(List<Widget> allWidgets, @Ignore BaseUserContext user) {
    return user;
  }


}
