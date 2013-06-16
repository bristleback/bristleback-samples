package pl.bristleback.samples.android;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import pl.bristleback.client.JettyClient;
import pl.bristleback.client.api.onmessage.OnMessageCallback;
import pl.bristleback.client.serialization.BristleMessage;

public class MainActivity extends Activity implements SensorEventListener {

  private float accelerometerVals[] = new float[3];
  private float magneticVals[] = new float[3];
  private float orientationSensorVals[] = new float[3];
  private float rotationMatrix[] = new float[16];
  private float orientation[] = new float[3];

  private JettyClient client;

  /**
   * Called when the activity is first created.
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    client = new JettyClient(new OnMessageCallback() {
      @Override
      public void onMessage(String serverMessage) {
        showMessageOnScreen(serverMessage);
      }

    }, "ws://192.168.1.110:8080/websocket");
    client.connect();


    SensorManager mgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    mgr.registerListener(this,
      mgr.getDefaultSensor(Sensor.TYPE_ALL),
      SensorManager.SENSOR_DELAY_UI);

  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    switch (event.sensor.getType()) {
      case Sensor.TYPE_ACCELEROMETER:
        System.arraycopy(event.values, 0, accelerometerVals, 0, 3);
        break;
      case Sensor.TYPE_MAGNETIC_FIELD:
        System.arraycopy(event.values, 0, magneticVals, 0, 3);
        break;
      case Sensor.TYPE_ORIENTATION:
        System.arraycopy(event.values, 0, orientationSensorVals, 0, 3);
        break;
      default:
        break;
    }

    if (null != magneticVals && null != accelerometerVals) {
      SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerVals, magneticVals);
      SensorManager.getOrientation(rotationMatrix, orientation);
      double azimuth = Math.toDegrees(orientation[0]);
      //this calculation gives me the Azimuth in the same format that OrientationSensor
      azimuth += (azimuth >= 0) ? 0 : 360;
      double FALSE_PITCH = Math.toDegrees(orientation[1]);
      double FALSE_ROLL = Math.toDegrees(orientation[2]);
    }
    BristleMessage bristleMessage = new BristleMessage();
    bristleMessage.withName("GyroAction.showGyro").withPayload(new GyroString(
      accelerometerVals, magneticVals, orientationSensorVals, rotationMatrix, orientation
    ));

    client.sendMessage(bristleMessage);
  }


  private void showMessageOnScreen(final String serverMessage) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        TextView myTextView = (TextView) findViewById(R.id.editText);
        myTextView.append(serverMessage + "\n");
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }


  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {
  }


}
