package Models;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private String name;
    private List<Session> sessions = new ArrayList<>();
    private Long id;

    public Cinema() {
    }

    public Cinema(String name, List<Session> sessions, Long id) {
        this.name = name;
        this.sessions = sessions;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", sessions=" + sessions +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
