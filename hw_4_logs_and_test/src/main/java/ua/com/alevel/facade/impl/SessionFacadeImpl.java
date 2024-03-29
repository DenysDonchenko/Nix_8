package ua.com.alevel.facade.impl;

import ua.com.alevel.dto.sessions.SessionCreateDto;
import ua.com.alevel.dto.sessions.SessionFindDto;
import ua.com.alevel.dto.sessions.SessionUpdateDto;
import ua.com.alevel.entity.Session;
import ua.com.alevel.facade.SessionFacade;
import ua.com.alevel.service.FilmService;
import ua.com.alevel.service.HallService;
import ua.com.alevel.service.SessionService;
import ua.com.alevel.service.impl.FilmServiceImpl;
import ua.com.alevel.service.impl.HallServiceImpl;
import ua.com.alevel.service.impl.SessionServiceImpl;
import ua.com.alevel.util.ConstGlobal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SessionFacadeImpl implements SessionFacade {

    private static final FilmService filmService = new FilmServiceImpl();
    private static final HallService hallService = new HallServiceImpl();
    private static final SessionService sessionService = new SessionServiceImpl();

    @Override
    public void create(SessionCreateDto sessionCreateDto) {
        Session session = createSession(sessionCreateDto);
        sessionService.create(session);
    }

    @Override
    public void update(SessionUpdateDto sessionUpdateDto) throws IOException {
        if (!exists(sessionUpdateDto.getId_session())) {
            System.out.println(ConstGlobal.settings.getString("session.id.not.found") + " " + sessionUpdateDto.getId_session());
            ConstGlobal.loggerWarn.warn(ConstGlobal.settings.getString("session.id.not.found") + " " + sessionUpdateDto.getId_session());
            throw new IOException();
        } else {
            Session session = updateSession(sessionUpdateDto);
            sessionService.update(session);
        }
    }

    @Override
    public void delete(Long id) throws IOException {
        if (!exists(id)) {
            System.out.println(ConstGlobal.settings.getString("session.id.not.found") + " " + id);
            ConstGlobal.loggerWarn.warn(ConstGlobal.settings.getString("session.id.not.found") + " " + id);
            throw new IOException();
        } else
            sessionService.delete(id);
    }

    @Override
    public SessionFindDto findById(Long id) throws IOException {
        if (!exists(id)) {
            System.out.println(ConstGlobal.settings.getString("session.id.not.found") + " " + id);
            ConstGlobal.loggerWarn.warn(ConstGlobal.settings.getString("session.id.not.found") + " " + id);
            throw new IOException();
        } else {
            ConstGlobal.loggerInfo.info(ConstGlobal.settings.getString("session.find.by.id") + id);
            return createSessionForFind(sessionService.findById(id));
        }

    }

    @Override
    public SessionFindDto[] findAll() throws IOException {
        if (sessionService.count() == 0) {
            System.out.println(ConstGlobal.settings.getString("session.does.not.exist"));
            ConstGlobal.loggerWarn.warn(ConstGlobal.settings.getString("session.does.not.exist"));
            throw new IOException();
        } else {
            Session[] sessions = sessionService.findAll();
            SessionFindDto[] sessionFindDto = new SessionFindDto[sessions.length];

            for (int i = 0; i < sessions.length; i++) {
                sessionFindDto[i] = createSessionForFind(sessions[i]);
            }
            return sessionFindDto;
        }
    }

    @Override
    public boolean exists(Long id) {
        return sessionService.exists(id);
    }

    private SessionFindDto createSessionForFind(Session session) {

        SessionFindDto sessionFindDto = new SessionFindDto();
        sessionFindDto.setId(session.getId());
        String nameHall = hallService.findById(session.getIdHall()).getNameHall();
        String nameFilm = filmService.findById(session.getIdFilm()).getNameFilm();

        sessionFindDto.setDateSession(session.getDateSession());
        sessionFindDto.setTimeStart(session.getTimeStart());
        sessionFindDto.setTimeFinish(session.getTimeFinish());
        sessionFindDto.setNameFilm(nameFilm);
        sessionFindDto.setNameHall(nameHall);
        return sessionFindDto;
    }

    private Session updateSession(SessionUpdateDto sessionUpdate) {
        LocalTime filmDuration = filmService.findById(sessionUpdate.getIdFilm()).getFilmDuration();
        LocalTime timeFinish = whatTime(filmDuration, sessionUpdate.getTimeStart());
        int freePlaces = hallService.findById(sessionUpdate.getIdHall()).getCapacity();

        Session sessionFromDB = sessionService.findById(sessionUpdate.getId_session());

        sessionFromDB.setIdHall(sessionUpdate.getIdHall());
        sessionFromDB.setIdFilm(sessionUpdate.getIdFilm());
        sessionFromDB.setDateSession(sessionUpdate.getDateSession());
        sessionFromDB.setTimeStart(sessionUpdate.getTimeStart());
        sessionFromDB.setFreePlaces(freePlaces);
        sessionFromDB.setTimeFinish(timeFinish);
        return sessionFromDB;
    }

    private Session createSession(SessionCreateDto sessionCreate) {

        LocalTime filmDuration = filmService.findById(sessionCreate.getIdFilm()).getFilmDuration();

        Long id_film = sessionCreate.getIdFilm();
        Long id_hall = sessionCreate.getIdHall();
        LocalDate dateSession = sessionCreate.getDateSession();
        LocalTime timeStart = sessionCreate.getTimeStart();
        LocalTime timeFinish = whatTime(filmDuration, sessionCreate.getTimeStart());
        int freePlaces = hallService.findById(sessionCreate.getIdHall()).getCapacity();

        return new Session(id_film, id_hall, dateSession, timeStart, timeFinish, freePlaces);
    }

    private LocalTime whatTime(LocalTime genre, LocalTime startFilm) {

        startFilm = startFilm.plusHours(genre.getHour());
        LocalTime localTime = startFilm.plusHours(genre.getHour());
        localTime = startFilm.plusMinutes(genre.getMinute());
        System.out.println(localTime.format(ConstGlobal.TIME_FORMATTER));
        LocalTime time = LocalTime.now();//parse(hours + ":" + minute);
        return localTime;
    }
}
