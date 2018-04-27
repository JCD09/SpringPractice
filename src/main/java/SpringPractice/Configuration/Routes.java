package SpringPractice.Configuration;


import SpringPractice.Handlers.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Routes{

    @Bean
    public RouterFunction<ServerResponse> simpleRoute(Handler handler){
            return nest(path("/user/{username}"),route(method(GET), handler::userDetails)).
                    andNest(path("/session"),route(method(GET),handler::DisplaySession)).
                    andNest(path("/user"),route(method(GET),handler::redirect)).
                    andNest(path("/"),route(method(GET),handler::redirect));
    }

}