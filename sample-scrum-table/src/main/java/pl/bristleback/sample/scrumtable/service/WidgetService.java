package pl.bristleback.sample.scrumtable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bristleback.sample.scrumtable.action.client.WidgetClientAction;
import pl.bristleback.sample.scrumtable.exception.WidgetExistsException;
import pl.bristleback.sample.scrumtable.vo.Widget;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Component
public class WidgetService {

  private final List<Widget> widgets = Collections.synchronizedList(new LinkedList<Widget>());

  @Autowired
  private WidgetClientAction widgetClientAction;

  public List<Widget> getAllWidgets() {
    return widgets;
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

  public void addWidget(Widget newWidget) {
    if (widgets.contains(newWidget)) {
      throw new WidgetExistsException();
    }
    widgets.add(newWidget);
    widgetClientAction.newWidget(newWidget);
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

}
