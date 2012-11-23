package com.javamv.efd.bolts;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.tuple.Tuple;
import com.javamv.efd.bolts.FraudDetectionAlertBolt;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.javamv.efd.helpers.DataFixtures.firstLoginTuple;
import static com.javamv.efd.helpers.DataFixtures.secondLoginTuple;
import static com.javamv.efd.helpers.DataFixtures.secondLogoutTuple;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 2:28 PM
 */
public class FraudDetectionAlertBoltTest {

	@Test
	public void should_emit_alert_when_ip_on_same_operation_login_is_different() {
		// GIVEN
		BasicOutputCollector mockCollector = mock(BasicOutputCollector.class);
		FraudDetectionAlertBolt alertBolt = new FraudDetectionAlertBolt();
		// WHEN
		alertBolt.execute(firstLoginTuple(), mockCollector);
		alertBolt.execute(secondLoginTuple(), mockCollector);
		// THEN
		Object alert = "Attention! Same Operation Detected on:a@gmail.com";
		verify(mockCollector, times(1)).emit(Arrays.asList(alert));
	}

	@Test
	public void should_not_emit_alert_when_ip_not_on_same_operation_login_is_different() {
		// GIVEN
		BasicOutputCollector mockCollector = mock(BasicOutputCollector.class);
		FraudDetectionAlertBolt alertBolt = new FraudDetectionAlertBolt();
		// WHEN
		alertBolt.execute(firstLoginTuple(), mockCollector);
		alertBolt.execute(secondLogoutTuple(), mockCollector);
		// THEN
		verify(mockCollector, never()).emit(anyListOf(Object.class));
	}

}
