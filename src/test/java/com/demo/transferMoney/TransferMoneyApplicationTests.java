package com.demo.transferMoney;

import com.demo.transferMoney.Repository.AccountRepository;
import com.demo.transferMoney.builder.RequestBuilder;
import com.demo.transferMoney.domain.Request;
import com.demo.transferMoney.domain.Response;
import com.demo.transferMoney.exception.TransferMoneyException;
import com.demo.transferMoney.model.Account;
import com.demo.transferMoney.service.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferMoneyApplicationTests {
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@MockBean
	AccountRepository accountRepository;

	@Autowired
	TransferService transferService;

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
			if(r.getAccountNumber().equals(1234)){
				assertTrue(r.getCurrentBalance().compareTo(new BigDecimal(6766.00)) == 0);
			}
			if(r.getAccountNumber().equals(4567)){
				assertTrue(r.getCurrentBalance().compareTo(new BigDecimal(8543.00)) == 0);
			}
		}
	}

	@Test
	public void testTransferMoneySourceAccountDoesnotExist(){
		Request request = RequestBuilder.getBuilder()
				.withAccountSource(12)
				.withAccountDestination(4567)
				.withTransferAmount(new BigDecimal(23))
				.build();
		HttpEntity<Request> httpEntity = new HttpEntity<>(request,headers);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange(createURLWithPort("/api/transferMoney"),
						HttpMethod.POST,httpEntity,String.class);
		HttpStatus s = responseEntity.getStatusCode();
		assertTrue(s.equals(HttpStatus.NOT_FOUND));
	}
	@Test
	public void testTransferMoneyDesinationAccountDoesnotExist(){
		Request request = RequestBuilder.getBuilder()
				.withAccountSource(1234)
				.withAccountDestination(45)
				.withTransferAmount(new BigDecimal(23))
				.build();
		HttpEntity<Request> httpEntity = new HttpEntity<>(request,headers);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange(createURLWithPort("/api/transferMoney"),
						HttpMethod.POST,httpEntity,String.class);
		HttpStatus s = responseEntity.getStatusCode();
		assertTrue(s.equals(HttpStatus.NOT_FOUND));
	}

	@Test
	public void testTransferMoneySourceAccountDoesnotHaveEnoughBalance(){
		Request request = RequestBuilder.getBuilder()
				.withAccountSource(1234)
				.withAccountDestination(4567)
				.withTransferAmount(new BigDecimal(999876))
				.build();

		HttpEntity<Request> httpEntity = new HttpEntity<>(request,headers);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange(createURLWithPort("/api/transferMoney"),
						HttpMethod.POST,httpEntity,String.class);
		HttpStatus s = responseEntity.getStatusCode();
		assertTrue(s.equals(HttpStatus.NOT_FOUND));
	}

	@Test(expected = TransferMoneyException.class)
	public void testTransferMoneySourceAccountHaveZeroBalance(){
		Request request = RequestBuilder.getBuilder()
				.withAccountSource(1234)
				.withAccountDestination(4567)
				.withTransferAmount(new BigDecimal(999876))
				.build();
  when(accountRepository.findById(1234))
		  .thenReturn(Optional.of(new Account(1234, BigDecimal.ZERO, 34578)));
		transferService.transferMoney(request);
	}


	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
