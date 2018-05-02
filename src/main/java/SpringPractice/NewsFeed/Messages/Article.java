package SpringPractice.NewsFeed.Messages;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.net.URL;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Article {
    Source source;
    String author;
    String title;
    String description;
    URL url;
    URL urlToImage;
    String publishedAt;

}
