package pl.bristleback.scrumtable.vo;

import org.apache.log4j.Logger;

/**
 * //@todo class description
 * <p/>
 * Created on: 23.04.11 14:05 <br/>
 *
 * @author Paweł Machowski
 */
public class Widget implements Comparable<Widget>{
  private static Logger log = Logger.getLogger(Widget.class.getName());

  private String id;
  private boolean locked;

  private Position position;
  private String title;
  private String description;
  private String owner;
  private String time;

  public Widget() {
  }

  public Widget(String id) {
    this.id = id;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  @Override
  public int compareTo(Widget widget) {
    return this.id.compareTo(widget.getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Widget widget = (Widget) o;

    if (id != null ? !id.equals(widget.id) : widget.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}