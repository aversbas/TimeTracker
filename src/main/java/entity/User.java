package entity;


import java.io.Serializable;

public class User implements Serializable{

  private Long userId;
  private String firstName;
  private String secondName;
  private String email;
  private String login;
  private String password;
  private UserType userType;

  public User() {
  }

  public User(Long userId, String firstName, String secondName,
              String email, String login, String password, UserType userType) {
    this.userId = userId;
    this.firstName = firstName;
    this.secondName = secondName;
    this.email = email;
    this.login = login;
    this.password = password;
    this.userType = userType;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;

    User user = (User) o;

    if (getUserId() != null ? !getUserId().equals(user.getUserId()) : user.getUserId() != null) return false;
    if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
      return false;
    if (getSecondName() != null ? !getSecondName().equals(user.getSecondName()) : user.getSecondName() != null)
      return false;
    if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
    if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) return false;
    if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null) return false;
    return getUserType() != null ? getUserType().equals(user.getUserType()) : user.getUserType() == null;
  }

  @Override
  public int hashCode() {
    int result = getUserId() != null ? getUserId().hashCode() : 0;
    result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
    result = 31 * result + (getSecondName() != null ? getSecondName().hashCode() : 0);
    result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
    result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
    result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
    result = 31 * result + (getUserType() != null ? getUserType().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
            "userId=" + userId +
            ", firstName='" + firstName + '\'' +
            ", secondName='" + secondName + '\'' +
            ", email='" + email + '\'' +
            ", login='" + login + '\'' +
            ", password='" + password + '\'' +
            ", userType=" + userType +
            '}';
  }
}
