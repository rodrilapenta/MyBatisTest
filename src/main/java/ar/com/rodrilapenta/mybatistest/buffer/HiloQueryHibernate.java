package ar.com.rodrilapenta.mybatistest.buffer;

import java.util.Map;

import javax.swing.JTextField;

import ar.com.rodrilapenta.mybatistest.db.dao.impl.GenericDAOImpl;

public class HiloQueryHibernate extends Thread {
	//Buffer mon;
	private GenericDAOImpl dao;
	private JTextField minExecTime, maxExecTime, avgExecTime;
	private String query;
	
	public HiloQueryHibernate(GenericDAOImpl dao, JTextField minExecTime, JTextField maxExecTime, JTextField avgExecTime, String query) {
		this.dao = dao;
		this.avgExecTime = avgExecTime;
		this.minExecTime = minExecTime;
		this.maxExecTime = maxExecTime;
		this.query = query;
	}

	public void run() {
		for(int i = 0; i < 10; i++) {
			Map<String, Object> stats = dao.genericQuery(query);
			avgExecTime.setText(stats.get("avgExecTime").toString());
			minExecTime.setText(stats.get("minExecTime").toString());
			maxExecTime.setText(stats.get("maxExecTime").toString());
		}
	}
}
