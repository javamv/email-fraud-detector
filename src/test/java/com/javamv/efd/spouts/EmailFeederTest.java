package com.javamv.efd.spouts;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.InprocMessaging;
import com.javamv.efd.spouts.EmailFeederSpout;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.javamv.efd.helpers.CustomMatchers.emailTupleHasTimestamp;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 11:41 AM
 */
public class EmailFeederTest {


	@Test
	public void should_emit_values_with_updated_timestamp() {
		// GIVEN
		EmailFeederSpout emailFeederSpout = new EmailFeederSpout();
		SpoutOutputCollector mockCollector = mock(SpoutOutputCollector.class);
		Object emailTuple = Arrays.asList(new Values("a@gmail.com", "100.100.100.100", "login", "12345"));
		InprocMessaging.sendMessage(emailFeederSpout.getPortId(), emailTuple);
		emailFeederSpout.open(null, null, mockCollector);
		// WHEN
		emailFeederSpout.nextTuple();
		// THEN
		verify(mockCollector).emit(argThat(emailTupleHasTimestamp("12345")));
	}

}
