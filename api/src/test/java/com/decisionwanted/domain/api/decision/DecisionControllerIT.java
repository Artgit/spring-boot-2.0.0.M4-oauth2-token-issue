package com.decisionwanted.domain.api.decision;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.decisionwanted.domain.api.Application;
import com.decisionwanted.domain.api.decision.util.DecisionTestUtils;
import com.decisionwanted.domain.api.dto.decision.DecisionResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class DecisionControllerIT {

	@LocalServerPort
	protected int port;

	@Test
	public void testCreateDecision() {

		DecisionResponse decision = DecisionTestUtils.createDecision("Decision1", "user", "user", port);

		assertNotNull(decision);
		assertNotNull(decision.getId());

	}

}