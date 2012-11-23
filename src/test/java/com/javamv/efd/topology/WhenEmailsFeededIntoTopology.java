package com.javamv.efd.topology;

import backtype.storm.Testing;
import backtype.storm.generated.StormTopology;
import backtype.storm.testing.MkClusterParam;
import backtype.storm.tuple.Values;
import com.javamv.efd.helpers.FraudDetectionJobUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.javamv.efd.helpers.FraudDetectionJobUtils.FraudDetectionTestJob;
import static com.javamv.efd.helpers.FraudDetectionJobUtils.replayFraudDetectionTopology;
import static com.javamv.efd.helpers.IntegrationFixtures.buildClusterParams;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 11:23 AM
 */
public class WhenEmailsFeededIntoTopology {

	private MkClusterParam mkClusterParam;
	private FraudDetectionTestJob testJob;


	@Before
	public void setupContext() {
		mkClusterParam = buildClusterParams();
		StormTopology topology = FraudDetectionTopologyFactory.newFraudDetectionTopology();
		testJob = FraudDetectionJobUtils.prepareTestJob(topology);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void should_detect_fraud_suspicion() {
		// GIVEN
		testJob.setMockData("1", new Values("a@gmail.com", "100.100.100.100", "login", "12345"),
				new Values("a@gmail.com", "100.100.100.101", "login", "12345"),
				new Values("b@gmail.com", "100.100.100.100", "logout", "12345"),
				new Values("b@gmail.com", "100.100.100.101", "login", "12345"));
		// WHEN
		replayFraudDetectionTopology(mkClusterParam, testJob);
		// THEN
		List<Values> actualValues = new ArrayList<Values>(Testing.readTuples(testJob.result, "2"));
		assertThat(actualValues, hasItems(new Values("Attention! Same Operation Detected on:a@gmail.com")));
	}

}
