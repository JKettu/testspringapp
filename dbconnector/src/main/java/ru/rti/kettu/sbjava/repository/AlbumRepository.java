package ru.rti.kettu.sbjava.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rti.kettu.sbjava.model.Album;
import ru.rti.kettu.sbjava.service.Service;

import java.io.Serializable;
import java.util.List;

@Repository
public class AlbumRepository {

    @Autowired
    SessionFactory sessionFactory;

    public Album getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Album.class, id);
    }

    public List<Album> getAllAlbums() {
        Session session = sessionFactory.getCurrentSession();
        Query<Album> query = session.createQuery("from Album");
        return query.getResultList();
    }

    public Serializable createAlbum(Album album) {
        Session session = sessionFactory.getCurrentSession();
        Serializable id = session.save(album);
        return id;
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
