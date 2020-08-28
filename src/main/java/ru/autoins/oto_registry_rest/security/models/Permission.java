package ru.autoins.oto_registry_rest.security.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "registry_security", name = "PERMISSIONS_TMP")
public class Permission {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PERMISSION_NAME", columnDefinition = "VARCHAR(20) UNIQUE NOT NULL")
    private String permissionName;

    @ManyToMany(mappedBy = "listOfPermissions")
    private Set<Role> listOfRoles = new HashSet<>();

    public Permission() {
    }

    public Permission(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Set<Role> getListOfRoles() {
        return listOfRoles;
    }

    public void setListOfRoles(Set<Role> listOfRoles) {
        this.listOfRoles = listOfRoles;
    }

    public enum Permission_desc {
        READ, WRITE;

    }
}
