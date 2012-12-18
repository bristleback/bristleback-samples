package pl.bristleback.scrumtable.vo;

import org.apache.log4j.Logger;

/**
 * //@todo class description
 * <p/>
 * Created on: 11.04.11 17:07 <br/>
 *
 * @author Paweł Machowski
 */
public class User implements Comparable<User>{
  private static Logger log = Logger.getLogger(User.class.getName());

  private String name;
  private String id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public int compareTo(User user) {
    return name.compareTo(user.getName());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (name != null ? !name.equals(user.name) : user.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }
}