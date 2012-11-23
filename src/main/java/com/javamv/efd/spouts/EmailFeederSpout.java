package com.javamv.efd.spouts;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.InprocMessaging;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 11:48 AM
 */
public class EmailFeederSpout extends BaseRichSpout {
	private int portId;
	private SpoutOutputCollector spoutOutputCollector;

	public int getPortId() {
		return portId;
	}

	public EmailFeederSpout() {
		portId = InprocMessaging.acquireNewPort();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("email", "ip", "operation", "timestamp"));
	}

	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		this.spoutOutputCollector = spoutOutputCollector;
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public void nextTuple() {

		List<Object> toEmit = (List<Object>) InprocMessaging.pollMessage(portId);

		List<Object> tuple = (List<Object>) toEmit.get(0);
		tuple.set(3, System.currentTimeMillis());
		spoutOutputCollector.emit(tuple);
	}
}
