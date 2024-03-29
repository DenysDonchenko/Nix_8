package ua.com.alevel.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private LocalDate birthday;
    private String phoneNumber;
    private String email;


    public User() {
    }

    public User(String name, LocalDate birthday, String phoneNumber, String email) {
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(Long id, String name, LocalDate birthday, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday, phoneNumber, email);
    }
}
