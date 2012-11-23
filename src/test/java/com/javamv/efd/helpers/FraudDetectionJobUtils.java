package com.javamv.efd.helpers;

import backtype.storm.Config;
import backtype.storm.ILocalCluster;
import backtype.storm.Testing;
import backtype.storm.generated.StormTopology;
import backtype.storm.testing.CompleteTopologyParam;
import backtype.storm.testing.MkClusterParam;
import backtype.storm.testing.MockedSources;
import backtype.storm.testing.TestJob;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 4:36 PM
 */
public class FraudDetectionJobUtils {

	public static FraudDetectionTestJob prepareTestJob(final StormTopology topology) {
		return new FraudDetectionTestJob(topology);
	}

	public static void replayFraudDetectionTopology(MkClusterParam mkClusterParam, FraudDetectionTestJob testJob) {
		try {
			Testing.withSimulatedTimeLocalCluster(mkClusterParam, testJob);
		} catch (Exception e) {
		}
	}

	public static class FraudDetectionTestJob implements TestJob {
		private final StormTopology topology;
		public Map result;
		private MockedSources mockedSources;

		public FraudDetectionTestJob(StormTopology topology) {
			this.topology = topology;
			mockedSources = new MockedSources();
		}

		@Override
		public void run(ILocalCluster cluster) {

			Config conf = new Config();
			conf.setNumWorkers(2);

			CompleteTopologyParam completeTopologyParam = new CompleteTopologyParam();
			completeTopologyParam.setMockedSources(mockedSources);
			completeTopologyParam.setStormConf(conf);

			result = Testing.completeTopology(cluster, topology,
					completeTopologyParam);

		}

		public void setMockData(String sourceName, Values ... values) {
			mockedSources.addMockData(sourceName, values);
		}

	}
}
