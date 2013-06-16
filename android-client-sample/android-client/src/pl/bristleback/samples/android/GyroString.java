package pl.bristleback.samples.android;

import java.util.Arrays;

/**
 * <p/>
 * Created on: 16.06.13 12:23 <br/>
 *
 * @author Pawel Machowski
 */
public class GyroString {
  private String accelerometerVals;
  private String x;
  private String y;
  private String z;
  private String magneticVals;
  private String orientationSensorVals;
  private String rotationMatrix;
  private String orientation;

  public GyroString(float[] accelerometerVals, float[] magneticVals, float[] orientationSensorVals, float[] rotationMatrix, float[] orientation) {
    this.x = String.valueOf(accelerometerVals[0]);
    this.y = String.valueOf(accelerometerVals[1]);
    this.z = String.valueOf(accelerometerVals[2]);
    this.accelerometerVals = Arrays.toString(accelerometerVals);
    this.magneticVals = Arrays.toString(magneticVals);
    this.orientationSensorVals = Arrays.toString(orientationSensorVals);
    this.rotationMatrix = Arrays.toString(rotationMatrix);
    this.orientation = Arrays.toString(orientation);
  }

  public String getX() {
    return x;
  }

  public void setX(String x) {
    this.x = x;
  }

  public String getY() {
    return y;
  }

  public void setY(String y) {
    this.y = y;
  }

  public String getZ() {
    return z;
  }

  public void setZ(String z) {
    this.z = z;
  }

  public String getAccelerometerVals() {
    return accelerometerVals;
  }

  public void setAccelerometerVals(String accelerometerVals) {
    this.accelerometerVals = accelerometerVals;
  }

  public String getMagneticVals() {
    return magneticVals;
  }

  public void setMagneticVals(String magneticVals) {
    this.magneticVals = magneticVals;
  }

  public String getOrientationSensorVals() {
    return orientationSensorVals;
  }

  public void setOrientationSensorVals(String orientationSensorVals) {
    this.orientationSensorVals = orientationSensorVals;
  }

  public String getRotationMatrix() {
    return rotationMatrix;
  }

  public void setRotationMatrix(String rotationMatrix) {
    this.rotationMatrix = rotationMatrix;
  }

  public String getOrientation() {
    return orientation;
  }

  public void setOrientation(String orientation) {
    this.orientation = orientation;
  }
}
