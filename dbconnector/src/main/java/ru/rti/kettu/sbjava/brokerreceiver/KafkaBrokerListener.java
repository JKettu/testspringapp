package ru.rti.kettu.sbjava.brokerreceiver;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;
import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApi;
import ru.rti.kettu.sbjava.service.MusicService;

import static ru.rti.kettu.sbjava.mapping.moduleintegration.AlbumToDbAlbumMapping.getDbModelAlbum;

@EnableKafka
@Component
public class KafkaBrokerListener {

    final MusicService musicService;

    public KafkaBrokerListener(MusicService musicService) {
        this.musicService = musicService;
    }

    @KafkaListener(id = "consumer", topics = {"album"}, topicPartitions = { @TopicPartition(topic = "album", partitions = {"0"}) }, containerFactory = "albumSingleFactory")
    public void consume(OperationAlbumApi operationAlbumApi) {
        if (AlbumOperations.CREATE.equals(operationAlbumApi.getOperation())) {
            musicService.createAlbumInfo(getDbModelAlbum(operationAlbumApi.getAlbum()));
        }
    }

}
