package com.javamv.efd.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 2:39 PM
 */
public class FraudDetectionAlertBolt extends BaseBasicBolt {

	private Map<String, EmailRecord> activityMap = new HashMap<String, EmailRecord>();

	@Override
	public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
		String ip = tuple.getStringByField("ip");
		String timestamp = tuple.getString(2);
		String operation = tuple.getStringByField("operation");
		String email = tuple.getStringByField("email");
		EmailRecord currentEmailRecord = activityMap.get(email);
		if (currentEmailRecord != null) {
			if (verifyFraud(ip, operation, currentEmailRecord)) {
				Object alertMessage = "Attention! Same Operation Detected on:"+email;
				basicOutputCollector.emit(Arrays.asList(alertMessage));
			}
		}
		activityMap.put(email, new EmailRecord(ip,operation, timestamp));
	}

	private boolean verifyFraud(String ip, String operation, EmailRecord currentEmailRecord) {
		return operation.equals(currentEmailRecord.getOperation())&&!ip.equals(currentEmailRecord.getIp());
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("message"));
	}


	private static class EmailRecord {
		private final String ip;
		private final String operation;
		private final String timestamp;

		private EmailRecord(String ip, String operation, String timestamp) {
			this.ip = ip;
			this.operation = operation;
			this.timestamp = timestamp;
		}

		public String getIp() {
			return ip;
		}

		public String getOperation() {
			return operation;
		}

		public String getTimestamp() {
			return timestamp;
		}
	}
}
