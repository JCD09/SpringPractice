package SpringPractice.Handlers;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.Rendering;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@Component
public class Handler {

    public Mono<ServerResponse> loginPage(ServerRequest serverRequest){
        Map<String,Object> data = new HashMap<>();
        return ServerResponse.
                ok().
                contentType(MediaType.TEXT_HTML).render("login",data);
    }


    public Mono<ServerResponse> DisplayPrincipal(ServerRequest serverRequest){
        Map<String,Object> data = new HashMap<>();

        Authentication principal = Authentication.class.cast(serverRequest.principal());

        return ServerResponse.
                ok().body(Mono.just(principal),Principal.class);
    }


    public Mono<ServerResponse> DisplaySession(ServerRequest serverRequest){
        Map<String,Object> data = new HashMap<>();
        return ServerResponse.
                ok().body(serverRequest.session(),WebSession.class);
    }


    public Mono<ServerResponse> userDetails(ServerRequest serverRequest){
        Mono<UserDetails> userDetails= serverRequest.
                principal().
                map(p-> UserDetails.class.cast(Authentication.class.cast(p).getPrincipal()));

        return ServerResponse.ok().
                body(userDetails,UserDetails.class);
    }

    public Mono<ServerResponse> redirect(ServerRequest serverRequest){
        return serverRequest.principal().
                map(Principal::getName).
                map(userName->URI.create("/user/"+userName)).
                flatMap(redirectURI->ServerResponse.permanentRedirect(redirectURI).build());
    }

}
