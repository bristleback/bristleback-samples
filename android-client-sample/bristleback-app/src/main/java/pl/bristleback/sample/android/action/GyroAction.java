package pl.bristleback.sample.android.action;

import org.springframework.stereotype.Component;
import pl.bristleback.sample.android.action.client.DisplayGraphAction;
import pl.bristleback.server.bristle.api.annotations.Action;
import pl.bristleback.server.bristle.api.annotations.ActionClass;

import javax.inject.Inject;

/**
 * <p/>
 * Created on: 16.06.13 11:40 <br/>
 *
 * @author Pawel Machowski
 */
@ActionClass
@Component
public class GyroAction {

  @Inject
  private DisplayGraphAction displayGraphAction;

  @Action
  public void showGyro(GyroString obj) {
    displayGraphAction.graphData(Double.valueOf(obj.getX()), Double.valueOf(obj.getY()), Double.valueOf(obj.getZ()));
  }
}
