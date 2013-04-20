package pl.bristleback.scrumtable.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bristleback.scrumtable.actions.client.WidgetClientAction;
import pl.bristleback.scrumtable.services.WidgetService;
import pl.bristleback.server.bristle.api.ConnectionStateListener;
import pl.bristleback.server.bristle.api.users.UserContext;
import pl.bristleback.server.bristle.listener.ConnectionStateListenerChain;

@Component
public class SendWidgetsConnectionListener implements ConnectionStateListener {

  @Autowired
  private WidgetClientAction widgetClientAction;

  @Autowired
  private WidgetService widgetService;

  @Override
  public void userConnected(UserContext userContext, ConnectionStateListenerChain connectionStateListenerChain) {
  }

  @Override
  public void userDisconnected(UserContext userContext, ConnectionStateListenerChain connectionStateListenerChain) {
  }
}
