package SpringPractice.NewsFeed.NewsWebClient;


import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.BiFunction;


@Component
public class NewsWebClient {

    private String NEWS_SOURCES="https://newsapi.org/v2/{endpoints}";

    private String API_KEY="X-Api-Key";
    private String API_VALUE="43a167ad5e5943c386c72685062b81c8";

    private String SOURCES="sources";
    private String EVERYTHING = "everything";
    private String TOP_HEADLINES = "top-headlines";



    WebClient webClientBuilder = WebClient.
            builder().
            baseUrl(NEWS_SOURCES).
            defaultHeader(API_KEY,API_VALUE).
            build();

    //
    BiFunction<UriBuilder,MultiValueMap<String,String>,UriBuilder> addQueryParams=

            (uriBuilder,queryParams)->uriBuilder.queryParams(queryParams);

//    BiFunction<UriBuilder,String,URI> build =
//
//            (uriBuilder, uriVar) -> ""


    public Mono<ClientResponse> getSources(MultiValueMap<String,String> queryParams){
        return
        webClientBuilder.
                get().
                uri(uriBuilder ->
                        uriBuilder.queryParams(queryParams).build(SOURCES)).
                exchange();
    }

    public Mono<ClientResponse> getTopHeadlines(MultiValueMap<String,String> queryParams){
        return webClientBuilder.
                get().
                uri(uriBuilder ->
                        uriBuilder.queryParams(queryParams).build(TOP_HEADLINES)).
                exchange();
    }

    public Mono<ClientResponse> getEverything(MultiValueMap<String,String> queryParams){
        return webClientBuilder.
                get().
                uri(uriBuilder ->
                        uriBuilder.queryParams(queryParams).build(EVERYTHING)).
                exchange();
    }








    public static void main(String[] args){
        NewsWebClient nf = new NewsWebClient();
        MultiValueMap<String,String > queryparams = new LinkedMultiValueMap<>();
        queryparams.add("category","en");
        queryparams.add("category","fr");

        UriBuilder n = new DefaultUriBuilderFactory().builder().path(nf.NEWS_SOURCES).path(nf.SOURCES);

        nf.webClientBuilder.
                get().
                uri(uriBuilder -> {
                    URI urB = uriBuilder.queryParams(queryparams).build("sources");
                    System.out.println("ASSDASDSD:   "+urB.toString());
                    return urB;
                }).exchange().block();


    }

}
