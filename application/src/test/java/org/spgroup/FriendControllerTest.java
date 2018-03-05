package org.spgroup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spgroup.common.EntityConstants;
import org.spgroup.request.SendRequest;
import org.spgroup.request.UpdateRequest;
import org.spgroup.response.FriendsResponse;
import org.spgroup.response.RecipientsResponse;
import org.spgroup.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(value = { ApplicationTestConfig.class })
@Ignore
public class FriendControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void helloworld() {
		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).isEqualTo(EntityConstants.helloworld);
	}

	@Test
	public void add() {
		HttpEntity<List<String>> requestBody = new HttpEntity<List<String>>(
				Arrays.asList("a@g.com", "b@g.com"));
		ResponseEntity<SuccessResponse> response = this.restTemplate.exchange(
				"/add", HttpMethod.POST, requestBody, SuccessResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().isSuccess()).isTrue();
	}

	@Test
	public void friends() {
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("email", "");
		String url = "/friends";
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath(url).
				queryParam("email", "a@g.com");
		System.out.println(builder.toUriString());
		FriendsResponse response = this.restTemplate.getForObject(builder.toUriString(), FriendsResponse.class);

		assertThat(response.isSuccess()).isTrue();
	}

	@Test
	public void common() {
		addFriends(new String[][] { { "1@g.com", "2@g.com" },
				{ "1@g.com", "3@g.com" }, { "1@g.com", "4@g.com" },
				{ "2@g.com", "4@g.com" } });

		HttpEntity<List<String>> requestBody = new HttpEntity<List<String>>(
				Arrays.asList("2@g.com", "1@g.com"));

		ResponseEntity<FriendsResponse> response = this.restTemplate.exchange(
				"/common", HttpMethod.POST, requestBody, FriendsResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().isSuccess()).isTrue();
	}

	@Test
	public void subscribe() {
		UpdateRequest rq = new UpdateRequest();
		rq.setRequestor("r1@g.com");
		rq.setTarget("1@g.com");
		HttpEntity<UpdateRequest> requestBody = new HttpEntity<UpdateRequest>(
				rq);
		ResponseEntity<SuccessResponse> response = this.restTemplate.exchange(
				"/subscribe", HttpMethod.POST, requestBody,
				SuccessResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().isSuccess()).isTrue();
	}

	@Test
	public void block() {
		UpdateRequest rq = new UpdateRequest();
		rq.setRequestor("3@g.com");
		rq.setTarget("1@g.com");
		HttpEntity<UpdateRequest> requestBody = new HttpEntity<UpdateRequest>(
				rq);
		ResponseEntity<SuccessResponse> response = this.restTemplate.exchange(
				"/block", HttpMethod.POST, requestBody, SuccessResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().isSuccess()).isTrue();
	}

	@Test
	public void recipients() {
		SendRequest rq = new SendRequest();
		rq.setSender("1@g.com");
		rq.setText("Hello SPGroup !!");
		HttpEntity<SendRequest> requestBody = new HttpEntity<SendRequest>(rq);
		ResponseEntity<RecipientsResponse> response = this.restTemplate
				.exchange("/recipients", HttpMethod.POST, requestBody,
						RecipientsResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().isSuccess()).isTrue();
	}

	private void addFriends(String[][] friends) {
		HttpEntity<List<String>> requestBody;
		for (int i = 0; i < friends.length; i++) {
			requestBody = new HttpEntity<List<String>>(Arrays.asList("1@g.com",
					"2@g.com"));
			this.restTemplate.exchange("/add", HttpMethod.POST, requestBody,
					SuccessResponse.class);
		}
	}
}
