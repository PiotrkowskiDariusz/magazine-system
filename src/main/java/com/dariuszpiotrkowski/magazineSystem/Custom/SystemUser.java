package com.dariuszpiotrkowski.magazineSystem.Custom;

import com.dariuszpiotrkowski.magazineSystem.entity.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@FieldMatch.List({@FieldMatch(first = "password", second = "matchingPassword", message = "Hasła muszą być takie same")})
public class SystemUser {

    @NotNull(message="pole wymagane")
    @Size(min=1, message="pole wymagane")
    private String userName;

    @NotNull(message="pole wymagane")
    @Size(min=1, message="pole wymagane")
    private String password;

    @NotNull(message="pole wymagane")
    @Size(min=1, message="pole wymagane")
    private String matchingPassword;

    @NotNull(message="pole wymagane")
    @Size(min=1, message="pole wymagane")
    private String firstName;

    @NotNull(message="pole wymagane")
    @Size(min=1, message="pole wymagane")
    private String lastName;

    @NotNull(message="pole wymagane")
    @Size(min=1, message="pole wymagane")
    private String email;

    @NotNull(message="pole wymagane")
    @Size(min=1, message="pole wymagane")
    private List<Role> roles;

    public SystemUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
