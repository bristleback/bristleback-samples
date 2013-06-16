package pl.bristleback.samples.android;

/**
 * <p/>
 * Created on: 16.06.13 12:23 <br/>
 *
 * @author Pawel Machowski
 */
public class GyroArray {
  private float accelerometerVals[] = new float[3];
  private float magneticVals[] = new float[3];
  private float orientationSensorVals[] = new float[3];
  private float rotationMatrix[] = new float[16];
  private float orientation[] = new float[3];

  private GyroArray(float[] accelerometerVals, float[] magneticVals, float[] orientationSensorVals, float[] rotationMatrix, float[] orientation) {
    this.accelerometerVals = accelerometerVals;
    this.magneticVals = magneticVals;
    this.orientationSensorVals = orientationSensorVals;
    this.rotationMatrix = rotationMatrix;
    this.orientation = orientation;
  }

  private float[] getAccelerometerVals() {
    return accelerometerVals;
  }

  private void setAccelerometerVals(float[] accelerometerVals) {
    this.accelerometerVals = accelerometerVals;
  }

  private float[] getMagneticVals() {
    return magneticVals;
  }

  private void setMagneticVals(float[] magneticVals) {
    this.magneticVals = magneticVals;
  }

  private float[] getOrientationSensorVals() {
    return orientationSensorVals;
  }

  private void setOrientationSensorVals(float[] orientationSensorVals) {
    this.orientationSensorVals = orientationSensorVals;
  }

  private float[] getRotationMatrix() {
    return rotationMatrix;
  }

  private void setRotationMatrix(float[] rotationMatrix) {
    this.rotationMatrix = rotationMatrix;
  }

  private float[] getOrientation() {
    return orientation;
  }

  private void setOrientation(float[] orientation) {
    this.orientation = orientation;
  }
}
