package at.devfest.app.ui.sessions.list;

import java.util.List;

import at.devfest.app.data.app.model.Session;

public interface SessionsListMvp {

    interface SessionDetailsHandler {
        void startSessionDetails(Session session);
    }

    interface View extends SessionDetailsHandler {
        void initToobar(String title);

        void initSessionsList(List<Session> sessions);

    }
}
