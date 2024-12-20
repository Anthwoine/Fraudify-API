package fr.antoine.fraudify.configs;

import fr.antoine.fraudify.utils.osstrategie.LinuxStrategy;
import fr.antoine.fraudify.utils.osstrategie.OsStrategy;
import fr.antoine.fraudify.utils.osstrategie.WindowsStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:youtube-dl.properties")
@Getter
public class YoutubeDlpConfig {

    @Value("${youtube-dl.path}")
    public String youtubeDlPath;

    @Value("${audio.file}")
    public String audioFilePath;

    @Value("${metadata.file}")
    public  String metadataFilePath;
}
