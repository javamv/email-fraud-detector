package com.javamv.efd.helpers;

import backtype.storm.Config;
import backtype.storm.testing.MkClusterParam;

/**
 * Created with IntelliJ IDEA.
 * User: vmoskalenko
 * Date: 11/23/12
 * Time: 4:52 PM
 */
public class IntegrationFixtures {

	public static MkClusterParam buildClusterParams() {
		MkClusterParam mkClusterParam = new MkClusterParam();
		mkClusterParam.setSupervisors(4);
		Config daemonConf = new Config();
		daemonConf.put(Config.STORM_LOCAL_MODE_ZMQ, false);
		mkClusterParam.setDaemonConf(daemonConf);
		return mkClusterParam;
	}

}
