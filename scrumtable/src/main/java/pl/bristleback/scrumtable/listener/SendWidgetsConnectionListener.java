package pl.bristleback.scrumtable.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bristleback.scrumtable.actions.client.WidgetClientAction;
import pl.bristleback.scrumtable.services.WidgetService;
import pl.bristleback.server.bristle.api.ConnectionStateListener;
import pl.bristleback.server.bristle.api.users.IdentifiedUser;

@Component
public class SendWidgetsConnectionListener implements ConnectionStateListener {

  @Autowired
  private WidgetClientAction widgetClientAction;

  @Autowired
  private WidgetService widgetService;

  @Override
  public void userConnected(IdentifiedUser scrumTableUser) {
//    widgetClientAction.widgetsList(widgetService.getAllWidgets(), scrumTableUser);
  }

  @Override
  public void userDisconnected(IdentifiedUser identifiedUser) {
  }
}
