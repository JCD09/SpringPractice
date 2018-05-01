package SpringPractice.NewsFeed.Messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.net.URL;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ExtendedSource extends Source {

    String description;
    URL url;
    String category;
    String language;
    String country;
}