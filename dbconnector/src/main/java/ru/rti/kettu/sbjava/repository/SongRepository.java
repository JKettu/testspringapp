package ru.rti.kettu.sbjava.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rti.kettu.sbjava.model.db.mongoDb.Song;

import java.util.List;

@Repository
public interface SongRepository extends MongoRepository<Song, String> {

    @Query("{albumId: ?0}")
    List<Song> getSongsByAlbumId(Long albumId);
}
