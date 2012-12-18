package pl.bristleback.scrumtable.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.bristleback.scrumtable.actions.client.WidgetClientAction;
import pl.bristleback.scrumtable.vo.Position;
import pl.bristleback.scrumtable.vo.Widget;
import pl.bristleback.server.bristle.api.users.IdentifiedUser;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * //@todo class description
 * <p/>
 * Created on: 13.04.11 16:45 <br/>
 *
 * @author Paweł Machowski
 */
@Controller
public class WidgetService {
  private static Logger log = Logger.getLogger(WidgetService.class.getName());

  @Autowired
  private WidgetClientAction widgetClientAction;

  private final List<Widget> widgets = Collections.synchronizedList(new LinkedList<Widget>());

  public List<Widget> getAllWidgets() {
    return widgets;
  }

  public void sendAllWidgets(IdentifiedUser user) {
    widgetClientAction.widgetsList(getAllWidgets(), user);
  }


  public Widget getWidgetById(String id) {
    synchronized (widgets) {
      for (Widget Widget : widgets) {
        if (Widget.getId().equals(id)) {
          return Widget;
        }
      }
    }
    return null;
  }

  public void removeWidgetByd(String id) {
    synchronized (widgets) {
      Iterator<Widget> iterator = widgets.iterator();
      while (iterator.hasNext()) {
        Widget next = iterator.next();
        if (next.getId().equals(id)) {
          iterator.remove();
          break;
        }
      }
    }
  }

  public void removeAllWidgets() {
    widgets.clear();
    widgetClientAction.clearWidgets();
  }

  public void addWidget(Widget newWidget) {
    addNewWidgetToList(newWidget);
    widgetClientAction.newWidget(newWidget);
  }

  private void addNewWidgetToList(Widget newWidget) {
    if (widgets.contains(newWidget)) {
      log.error("widget already exist");
    }
    widgets.add(newWidget);
  }


  public void moveWidget(Widget widget) {
    Widget savedWidget = getWidgetById(widget.getId());
    if (savedWidget.getPosition() == null) {
      savedWidget.setPosition(widget.getPosition());
    } else {
      savedWidget.getPosition().setTop(widget.getPosition().getTop());
      savedWidget.getPosition().setLeft(widget.getPosition().getLeft());
    }
    widgetClientAction.moveWidget(widget);

  }

  public void resizeWidget(Widget widget) {
    Widget savedWidget = getWidgetById(widget.getId());
    if (savedWidget.getPosition() == null) {
      savedWidget.setPosition(widget.getPosition());
    } else {
      savedWidget.getPosition().setHeight(widget.getPosition().getHeight());
      savedWidget.getPosition().setWidth(widget.getPosition().getWidth());
    }
    widgetClientAction.resizeWidget(widget);

  }

  public void editWidget(Widget widget) {
    Position oldWidgetPosition = getWidgetById(widget.getId()).getPosition();
    removeWidgetByd(widget.getId());
    widget.setPosition(oldWidgetPosition);
    addNewWidgetToList(widget);
    widgetClientAction.editWidget(widget);
  }

  public void lockWidget(Widget widget) {
    Widget savedWidget = getWidgetById(widget.getId());
    savedWidget.setLocked(true);
    widgetClientAction.lockWidget(widget);
  }

  public void unlockWidget(Widget widget) {
    Widget savedWidget = getWidgetById(widget.getId());
    savedWidget.setLocked(false);
    widgetClientAction.unlockWidget(widget);
  }


}