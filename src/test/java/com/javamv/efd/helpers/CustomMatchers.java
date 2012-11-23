package com.javamv.efd.helpers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 5:05 PM
 */
public class CustomMatchers {

	@SuppressWarnings("unchecked")
	public static Matcher<List<Object>> emailTupleHasTimestamp(final String value) {
		return new BaseMatcher<List<Object>>() {
			@Override
			public boolean matches(Object o) {
				Object timestamp = ((List<Object>) o).get(3);
				return ((List<Object>) o).get(3) != null && !timestamp.equals(value);
			}

			@Override
			public void describeTo(Description description) {
			}
		};
	}

}
