package pl.bristleback.sample.android.action.client;

import org.springframework.stereotype.Component;
import pl.bristleback.server.bristle.api.action.SendCondition;
import pl.bristleback.server.bristle.api.annotations.ClientAction;
import pl.bristleback.server.bristle.api.annotations.ClientActionClass;
import pl.bristleback.server.bristle.security.authorisation.conditions.AllUsersCondition;


@Component
@ClientActionClass
public class DisplayGraphAction {

  @ClientAction
  public SendCondition graphData(double x, double y, double z) {
    return AllUsersCondition.getInstance();
  }

}
