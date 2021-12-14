package Controllers;

import DAOs.SessionDAO;
import Models.Session;

import java.util.List;

public class SessionController {
    SessionDAO dao = new SessionDAO();

    public SessionController() {
    }

    public Session getById(Long id) {
        return dao.getById(id);
    }

    public Session createSession(Session session) {
        return dao.createSession(session);
    }

    public void editSession(Session session) {
        dao.editSession(session);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void deleteSession(Session session) {
        deleteById(session.getId());
    }

    public List<Session> listSessions() {
        return dao.listSessions();
    }
}
