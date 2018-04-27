package SpringPractice;


import SpringPractice.Configuration.Routes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {Routes.class})
public class SpringPracticeApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void sampleTest1() {

		webTestClient.get().uri("/misha")
				.accept(MediaType.TEXT_PLAIN).
				exchange().
				expectStatus().isOk().
				expectBody().
				consumeWith(System.out::println);
	}

}
