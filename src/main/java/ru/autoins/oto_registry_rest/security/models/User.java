package ru.autoins.oto_registry_rest.security.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "registry_security", name = "USERS_TMP")
public class User extends BaseEntity{

    @Column(name = "USER_NAME", columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    private String userName;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(255) NOT NULL")
    private String password;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH}
    )
    @JoinTable(
            name = "USER_ROLE_TMP",
            joinColumns = @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_FROM__USERS")),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"), inverseForeignKey = @ForeignKey(name = "FK_FROM__ROLES"))
    private Set<Role> listOfRoles = new HashSet<>();

    @OneToOne
    @JoinColumn(
            name = "STATUS_ID",
            foreignKey = @ForeignKey(name = "FK_FROM__STATUS"))
    private Status status;

    public User() {
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

    public Set<Role> getListOfRoles() {
        return listOfRoles;
    }

    public void setListOfRoles(Set<Role> listOfRoles) {
        this.listOfRoles = listOfRoles;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusById(Number id) {
        this.status = new Status(id.longValue());
    }
}
