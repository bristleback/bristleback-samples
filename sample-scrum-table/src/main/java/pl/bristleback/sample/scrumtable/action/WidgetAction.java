package pl.bristleback.sample.scrumtable.action;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.bristleback.sample.scrumtable.service.WidgetService;
import pl.bristleback.sample.scrumtable.vo.Position;
import pl.bristleback.sample.scrumtable.vo.Widget;
import pl.bristleback.server.bristle.api.annotations.Action;
import pl.bristleback.server.bristle.api.annotations.AnnotatedActionClass;
import pl.bristleback.server.bristle.api.annotations.Bind;


@Controller
@AnnotatedActionClass(name = "Widget")
public class WidgetAction {

  @Autowired
  private WidgetService widgetService;

  @Action
  public void addWidget(@Bind(required = true) String widgetName) {
    String newId = RandomStringUtils.randomAlphabetic(15);
    Position position = new Position();
    position.setHeight(120);
    position.setWidth(120);
    position.setTop(125);
    position.setLeft(60);
    Widget widget = new Widget(newId, widgetName);
    widget.setPosition(position);

    widgetService.addWidget(widget);
  }

  @Action
  public void resizeWidget(Widget widget) {
    widgetService.resizeWidget(widget);
  }

  @Action
  public void moveWidget(Widget widget) {
    widgetService.moveWidget(widget);
  }


}
