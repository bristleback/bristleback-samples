package pl.bristleback.scrumtable.vo;

import org.apache.log4j.Logger;

/**
 * //@todo class description
 * <p/>
 * Created on: 23.04.11 14:02 <br/>
 *
 * @author Paweł Machowski
 */
public class Position {
  private static Logger log = Logger.getLogger(Position.class.getName());

  private int left;
  private int top;
  private int height;
  private int width;

  public int getLeft() {
    return left;
  }

  public void setLeft(int left) {
    this.left = left;
  }

  public int getTop() {
    return top;
  }

  public void setTop(int top) {
    this.top = top;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public static Position defaultPosition() {
    Position position = new Position();
    position.setHeight(185);
    position.setWidth(165);
    position.setTop(80);
    position.setLeft(60);
    return position;
  }
}