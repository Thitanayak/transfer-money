package com.demo.transferMoney;

import com.demo.transferMoney.builder.RequestBuilder;
import com.demo.transferMoney.domain.Request;
import com.demo.transferMoney.domain.Response;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferMoneyApplicationTests {
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testTransferMoney(){
		Request request = RequestBuilder.getBuilder()
				.withAccountSource(1234)
				.withAccountDestination(4567)
				.withTransferAmount(new BigDecimal(23))
				.build();
		HttpEntity<Request> httpEntity = new HttpEntity<>(request,headers);
		ResponseEntity<Response[]> responseEntity = restTemplate
				.exchange(createURLWithPort("/api/transferMoney"),
						HttpMethod.POST,httpEntity,Response[].class);
		Response[] rs = responseEntity.getBody();
		assertTrue(rs.length == 2);
		for(Response r : rs){
			assertTrue(r != null);
			assertTrue(r.getAccountNumber() != null);
			assertTrue(r.getCurrentBalance() != null);
			assertTrue(r.getPreviousBalance() != null);
		}
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
