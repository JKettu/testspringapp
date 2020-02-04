package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.rti.kettu.sbjava.SpringBootCrudApplication;
import ru.rti.kettu.sbjava.controller.AlbumController;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.service.MusicService;

import static constants.Constants.AlbumTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {AlbumController.class})
@ContextConfiguration(classes = {SpringBootCrudApplication.class})
public class AlbumControllerTest {

    @MockBean
    private MusicService musicService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createAlbum() throws Exception {
        when(musicService.createAlbumInfo(any(Album.class))).thenReturn(ALBUM_ID);
        MvcResult response = mockMvc.perform(post("/album/createAlbum") //can add @PathVariable values
                .contentType(APPLICATION_JSON)
                .param("author", AUTHOR_NAME)
                .param("name", ALBUM_NAME)
                .param("year", String.valueOf(ALBUM_YEAR))
                //.content(objectMapper.writeValueAsString()) for @RequestBody as JSON
        ).andExpect(status().isOk())
                .andReturn();
        ArgumentCaptor<Album> albumCaptor = ArgumentCaptor.forClass(Album.class);
        verify(musicService, times(1)).createAlbumInfo(albumCaptor.capture());
        assertThat(albumCaptor.getValue().getName()).isEqualTo(ALBUM_NAME);
        assertThat(albumCaptor.getValue().getAuthor()).isEqualTo(AUTHOR_NAME);
        assertThat(albumCaptor.getValue().getYear()).isEqualTo(ALBUM_YEAR);

        Album expectedResponseBody = new Album();
        expectedResponseBody.setYear(ALBUM_YEAR);
        expectedResponseBody.setName(ALBUM_NAME);
        expectedResponseBody.setAuthor(AUTHOR_NAME);
        expectedResponseBody.setId(Long.parseLong(ALBUM_ID));
        assertThat(objectMapper.writeValueAsString(expectedResponseBody))
                .isEqualToIgnoringWhitespace(response.getResponse().getContentAsString());
    }
}
