package ru.rti.kettu.sbjava.brokerreceiver;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApi;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApiResponse;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.service.MusicService;

import static ru.rti.kettu.sbjava.mapping.moduleintegration.AlbumToDbAlbumMapping.getApiModelAlbum;
import static ru.rti.kettu.sbjava.mapping.moduleintegration.AlbumToDbAlbumMapping.getDbModelAlbum;

@Component
public class KafkaBrokerListener {

    final MusicService musicService;

    public KafkaBrokerListener(MusicService musicService) {
        this.musicService = musicService;
    }

    @KafkaListener(id = "consumer", topics = {"album"}, topicPartitions = { @TopicPartition(topic = "album", partitions = {"0"}) }, containerFactory = "albumSingleFactory")
    @SendTo (value = "albumReply")
    public OperationAlbumApiResponse consume(OperationAlbumApi albumApi) {
        if (albumApi == null) return null;
        Album album = getDbModelAlbum(albumApi.getAlbum());
        if (album == null) return null;
        if (AlbumOperations.CREATE.equals(albumApi.getOperation())) {
            OperationAlbumApiResponse response = new OperationAlbumApiResponse(AlbumOperations.CREATE);
            String id = musicService.createAlbumInfo(album);
            if (StringUtils.isEmpty(id))
                return null;
            album.setId(Long.parseLong(id));
            response.setAlbum(getApiModelAlbum(album));
            return response;
        }
        return null;
    }

}
