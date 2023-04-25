package cz.gryga.avitech.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "SUSERS")
public class User {

    @Id
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_GUID", unique = true)
    private String guid;

    @Column(name = "USER_NAME")
    private String name;

    public User() {
    }

    public User(Long id, String guid, String name) {
        this.id = id;
        this.guid = guid;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getGuid(), user.getGuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGuid());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", guid='" + guid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
