package service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.repository.AlbumRepository;
import ru.rti.kettu.sbjava.service.MusicService;

import static constants.Constants.AlbumTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {
    @Mock
    private AlbumRepository albumRepository;
    @InjectMocks
    private MusicService musicService;

    @Test
    public void createAlbum() {
        Album album = new Album();
        album.setAuthor(AUTHOR_NAME);
        album.setName(ALBUM_NAME);
        album.setYear(ALBUM_YEAR);

        when(albumRepository.createAlbum(any(Album.class))).thenReturn(ALBUM_ID);
        String id = musicService.createAlbumInfo(album);
        assertNotNull(id);
        assertEquals(id, ALBUM_ID);
    }

}
