package SpringPractice.NewsFeed;


import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class NewsFeedAPI {

    private String NEWS = "https://newsapi.org/v2/";
    private String SOURCES = "sources?";

    private String X_API_KEY = "X-API-KEY";
    private String API = "43a167ad5e5943c386c72685062b81c8";

    WebClient webClient;

    NewsFeedAPI(){}

    public static void main(String[] args){


    }
}
