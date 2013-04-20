package pl.bristleback.scrumtable.actions.client;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    Widget widget = Widget.defaultWidget(newId);
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
