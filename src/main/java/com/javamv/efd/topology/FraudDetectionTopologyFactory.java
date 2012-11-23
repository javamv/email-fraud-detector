package com.javamv.efd.topology;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.javamv.efd.spouts.EmailFeederSpout;
import com.javamv.efd.bolts.FraudDetectionAlertBolt;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 4:25 PM
 */
public class FraudDetectionTopologyFactory {
	public static StormTopology newFraudDetectionTopology() {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("1", new EmailFeederSpout(), 3);
		builder.setBolt("2", new FraudDetectionAlertBolt(), 4).fieldsGrouping(
				"1", new Fields("email"));
		return builder.createTopology();
	}
}
