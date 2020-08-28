package ru.autoins.oto_registry_rest.security.models;


import javax.persistence.*;

@Entity
@Table(schema = "registry_security", name = "STATUS_TMP")
public class Status {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STATUS_NAME", columnDefinition = "VARCHAR(10) NOT NULL DEFAULT 'ACTIVE'")
    private String statusName;

    public Status() {
    }

    public Status(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public enum Status_desc {
        ACTIVE, BANNED
    }
}
