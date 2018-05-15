package au.com.adepto.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.adepto.demo.model.Role;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoleControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private URL baseURL;

	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setUp() throws Exception {
		this.baseURL = new URL("http://localhost:" + port + "/v1/api/roles");
	}

	@Test
	public void givenAValidRoleId_whenGET_willReturnAJSONRole() throws Exception {
		// given
		Role role = new Role(1L, "Chef");

		// when
		ResponseEntity<Role> response = restTemplate.getForEntity(this.baseURL.toString() + "/1", Role.class);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().equals(role));
		
		String expected = "{id:1, description:Chef}";

		JSONAssert.assertEquals(expected, asJsonString(response.getBody()), false);
	}

	@Test
	public void givenAnInvalidRoleId_whenGET_willReturn_NOT_FOUND() {
		// given

		// when
		ResponseEntity<Role> response = restTemplate.getForEntity(this.baseURL.toString() + "/9999", Role.class);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}
//
	@Test
	public void givenAnExistingRole_whenPUT_willReturn_NO_CONTENT() throws Exception {
		// given
		Role role = new Role(1L, "Chef");
		
		HttpEntity<Role> entity = new HttpEntity<>(role);

		// when
		ResponseEntity<Role> response = restTemplate.exchange(this.baseURL.toString(), HttpMethod.PUT, entity, Role.class);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		assertThat(response.getBody()).isNull();
	}
	
	@Test
	public void givenANewRole_whenPOST_willReturn_CREATED() throws Exception {
		// given
		Role role = new Role(9L, "Any role");
		
		HttpEntity<Role> entity = new HttpEntity<>(role);

		// when
		ResponseEntity<Role> response = restTemplate.exchange(this.baseURL.toString(), HttpMethod.POST, entity, Role.class);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}


	@Test
	public void givenAValidId_whenDELETE_willReturn_NO_CONTENT() throws Exception {
		// given
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		// when
		ResponseEntity<Role> response = restTemplate.exchange(this.baseURL.toString() + "/5", HttpMethod.DELETE, entity, Role.class);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
