package at.devfest.app.data.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import at.devfest.app.data.app.model.Schedule;
import at.devfest.app.data.app.model.Session;
import at.devfest.app.data.app.model.Speaker;
import at.devfest.app.data.database.dao.SessionsDao;
import at.devfest.app.data.database.dao.SpeakersDao;
import at.devfest.app.data.firebase.FirebaseMapper;

@Singleton
public class DataProvider {

    private final AppMapper appMapper;
    private final SessionsDao sessionsDao;
    private final SpeakersDao speakersDao;
    private final FirebaseMapper firebaseMapper;
    private final DatabaseReference firebaseReference;
    private final MutableLiveData<List<Session>> liveSessions;
    private final MutableLiveData<List<Speaker>> liveSpeakers;
    private final MutableLiveData<Schedule> liveSchedule;
    private final MutableLiveData<Boolean> liveError;
    private ValueEventListener firebaseListener = null;

    @Inject
    public DataProvider(AppMapper appMapper, SessionsDao sessionsDao, SpeakersDao speakersDao,
                        FirebaseMapper firebaseMapper, DatabaseReference firebaseReference) {
        this.appMapper = appMapper;
        this.sessionsDao = sessionsDao;
        this.speakersDao = speakersDao;
        this.firebaseMapper = firebaseMapper;
        this.firebaseReference = firebaseReference;
        this.liveSessions = new MutableLiveData<>();
        this.liveSpeakers = new MutableLiveData<>();
        this.liveSchedule = new MutableLiveData<>();
        this.liveError = new MutableLiveData<>();
        this.liveError.setValue(false);
    }

    private List<Speaker> getSpeakers(DataSnapshot fbSnapshot) {
        DataSnapshot fbSpeakersRef = fbSnapshot.child("speakers");
        ArrayList<at.devfest.app.data.firebase.model.Speaker> fbSpeakers
                = new ArrayList<>((int) fbSpeakersRef.getChildrenCount());
        for (DataSnapshot fbSpeakerRef : fbSpeakersRef.getChildren()) {
            fbSpeakers.add(fbSpeakerRef.getValue(at.devfest.app.data.firebase.model.Speaker.class));
        }
        return firebaseMapper.toAppSpeakers(fbSpeakers);
    }

    private List<Session> getSessions(DataSnapshot fbSnapshot, List<Speaker> speakers) {
        DataSnapshot fbSessionsRef = fbSnapshot.child("sessions");
        ArrayList<at.devfest.app.data.firebase.model.Session> fbSessions
                = new ArrayList<>((int) fbSessionsRef.getChildrenCount());
        for (DataSnapshot fbSessionRef : fbSessionsRef.getChildren()) {
            fbSessions.add(fbSessionRef.getValue(at.devfest.app.data.firebase.model.Session.class));
        }
        return firebaseMapper.toAppSessions(fbSessions, appMapper.speakersToMap(speakers));
    }

    private void provideLiveData() {
        if (firebaseListener == null) {
            // load selected sessions
            sessionsDao.initSelectedSessionsMemory();
            firebaseListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Speaker> speakers = getSpeakers(dataSnapshot);
                    List<Session> sessions = getSessions(dataSnapshot, speakers);
                    liveSpeakers.postValue(speakers);
                    liveSessions.postValue(sessions);
                    liveSchedule.postValue(appMapper.toSchedule(sessions));
                    liveError.postValue(false);
                    // save values in legacy db
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            sessionsDao.saveSessions(sessions);
                            speakersDao.saveSpeakers(speakers);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    liveError.postValue(true);
                }
            };
            firebaseReference.child("mobile").addValueEventListener(firebaseListener);
        }
    }

    public LiveData<List<Speaker>> getLiveSpeakers() {
        provideLiveData();
        return liveSpeakers;
    }

    public LiveData<List<Session>> getLiveSessions() {
        provideLiveData();
        return liveSessions;
    }

    public LiveData<Schedule> getLiveSchedule() {
        provideLiveData();
        return liveSchedule;
    }

    public LiveData<Boolean> getLiveError() {
        provideLiveData();
        return liveError;
    }
}
