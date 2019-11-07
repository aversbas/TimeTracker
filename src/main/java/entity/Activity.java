package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activity implements Serializable {

  public static List<String> activityNameList = new ArrayList<>();

  private Long activityId;
  private String description;

  public Activity() {

  }

  public Activity(Long activityId, String description) {
    this.activityId = activityId;
    this.description = description;
  }

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activity_id) {
    this.activityId = activity_id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static List<String> getActivityNameList() {
    return activityNameList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Activity)) return false;

    Activity activity = (Activity) o;

    if (activityId != null ? !activityId.equals(activity.activityId) : activity.activityId != null) return false;
    return getDescription() != null ? getDescription().equals(activity.getDescription()) : activity.getDescription() == null;
  }

  @Override
  public int hashCode() {
    int result = activityId != null ? activityId.hashCode() : 0;
    result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Activity{" +
            "activityId=" + activityId +
            ", description='" + description + '\'' +
            '}';
  }
}
