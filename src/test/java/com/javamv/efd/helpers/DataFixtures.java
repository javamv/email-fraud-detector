package com.javamv.efd.helpers;

import backtype.storm.tuple.Tuple;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 5:08 PM
 */
public class DataFixtures {

	public static Tuple secondLoginTuple() {
		Tuple secondLoginTuple = mock(Tuple.class);
		when(secondLoginTuple.getStringByField("email")).thenReturn("a@gmail.com");
		when(secondLoginTuple.getStringByField("ip")).thenReturn("100.100.100.100");
		when(secondLoginTuple.getStringByField("operation")).thenReturn("login");
		when(secondLoginTuple.getStringByField("timestamp")).thenReturn("123123");
		return secondLoginTuple;
	}

	public static Tuple firstLoginTuple() {
		Tuple firstLoginTuple = mock(Tuple.class);
		when(firstLoginTuple.getStringByField("email")).thenReturn("a@gmail.com");
		when(firstLoginTuple.getStringByField("ip")).thenReturn("100.100.100.101");
		when(firstLoginTuple.getStringByField("operation")).thenReturn("login");
		when(firstLoginTuple.getStringByField("timestamp")).thenReturn("123123");
		return firstLoginTuple;
	}

	public static Tuple secondLogoutTuple() {
		Tuple firstLoginTuple = mock(Tuple.class);
		when(firstLoginTuple.getStringByField("email")).thenReturn("a@gmail.com");
		when(firstLoginTuple.getStringByField("ip")).thenReturn("100.100.100.101");
		when(firstLoginTuple.getStringByField("operation")).thenReturn("logout");
		when(firstLoginTuple.getStringByField("timestamp")).thenReturn("123123");
		return firstLoginTuple;
	}

}
