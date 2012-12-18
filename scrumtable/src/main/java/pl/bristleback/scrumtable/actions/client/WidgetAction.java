package pl.bristleback.scrumtable.actions.client;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.bristleback.scrumtable.services.WidgetService;
import pl.bristleback.scrumtable.vo.Position;
import pl.bristleback.scrumtable.vo.Widget;
import pl.bristleback.server.bristle.api.annotations.Action;
import pl.bristleback.server.bristle.api.annotations.ActionClass;
import pl.bristleback.server.bristle.engine.base.users.DefaultUser;


@Controller
@ActionClass(name = "Widget")
public class WidgetAction {

  @Autowired
  private WidgetService widgetService;

  @Action
  public void addWidget() {
    String newId = RandomStringUtils.randomAlphabetic(15);
    Position position = Position.defaultPosition();
    Widget widget = new Widget(newId);
    widget.setPosition(position);
    widgetService.addWidget(widget);
  }

  @Action
  public void userLogged(DefaultUser user) {
    widgetService.sendAllWidgets(user);
  }

  @Action
  public void resizeWidget(Widget widget) {
    widgetService.resizeWidget(widget);
  }

  @Action
  public void moveWidget(Widget widget) {
    widgetService.moveWidget(widget);
  }

  @Action
  public void editWidget(Widget widget) {
    widgetService.editWidget(widget);
  }

  @Action
  public void clearWidgets() {
    widgetService.removeAllWidgets();
  }

  @Action
  public void lockWidget(Widget widget) {
    widgetService.lockWidget(widget);
  }

  @Action
  public void unlockWidget(Widget widget) {
    widgetService.unlockWidget(widget);
  }

}
