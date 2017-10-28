package at.devfest.app.data.network;

import java.util.List;

import at.devfest.app.data.network.model.Session;
import at.devfest.app.data.network.model.Speaker;
import retrofit2.http.GET;
import rx.Observable;

public interface DevFestService {

    @GET("sessions.json")
    Observable<List<Session>> loadSessions();

    @GET("speakers.json")
    Observable<List<Speaker>> loadSpeakers();
}
