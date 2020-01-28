package ru.rti.kettu.sbjava.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.rti.kettu.sbjava.model.db.h2.Album;

import java.io.Serializable;
import java.util.List;

@Repository
public class AlbumRepository {

    final SessionFactory sessionFactory;

    public AlbumRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Album getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Album.class, id);
    }

    public List<Album> getAllAlbums() {
        Session session = sessionFactory.getCurrentSession();
        Query<Album> query = session.createQuery("from Album");
        return query.getResultList();
    }

    public String createAlbum(Album album) {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(album);
        return String.valueOf(id);
    }

    public void deleteAlbum(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Album album = session.get(Album.class, id);
        session.delete(album);
    }

    public Album updateAlbum(Album album) {
        Session session = sessionFactory.getCurrentSession();
        session.update(album);
        return album;
    }
}
