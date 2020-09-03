package ru.autoins.oto_registry_rest.security.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(schema = "registry_security", name = "ROLES_TMP")
public class Role extends BaseEntity{

    @Column(name = "ROLE_NAME", columnDefinition = "VARCHAR(20) NOT NULL")
    private String roleName;

    @ManyToMany(mappedBy = "listOfRoles")
    private Set<User> listOfUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ROLE_PERMISSION_TMP",
            joinColumns = @JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name = "FK_ROLE_ID")),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"), inverseForeignKey = @ForeignKey(name = "FK_FROM__PERMISSION_ID"))
    private Set<Permission> listOfPermissions = new HashSet<>();

    public Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(Set<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public Set<Permission> getListOfPermissions() {
        return listOfPermissions;
    }

    public void setListOfPermissions(Set<Permission> listOfPermissions) {
        this.listOfPermissions = listOfPermissions;
    }

    public void setPermissionsById(Number ...id) {
        this.listOfPermissions = Stream.of(id)
                .map(number -> {
                    Permission permission = new Permission();
                    permission.setId(number.longValue());
                    return permission;
                })
                .collect(Collectors.toSet());
    }

    public enum Role_desc {
        ADMIN, USER;
    }
}
