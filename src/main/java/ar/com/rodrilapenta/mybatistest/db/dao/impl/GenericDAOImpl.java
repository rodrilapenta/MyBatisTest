package ar.com.rodrilapenta.mybatistest.db.dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.QueryStatistics;

public class GenericDAOImpl {
	private SessionFactory sessionFactory;
	private SqlSessionFactory sqlSessionFactory;

	public GenericDAOImpl(SessionFactory sessionFactory, SqlSessionFactory sqlSessionFactory) {
		this.sessionFactory = sessionFactory;
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void insertElements(String tabla, List<String> registros, String file, List<String> emails) {
		Session s = sessionFactory.openSession();
		//TODO implementación con MyBatis
		List<String> fallidos = new ArrayList<String>();
		for (String r : registros) {
			try {
				s.beginTransaction();
				s.createSQLQuery("insert into " + tabla + " VALUES ( " + r + ")").executeUpdate();
				s.getTransaction().commit();
			} catch (Exception e) {
				// e.printStackTrace();
				fallidos.add(r);
			}
		}

		if (!fallidos.isEmpty()) {
			int pos = file.lastIndexOf("\\");
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-YYYY hh.mm.ss");
			String fileName = file.substring(0, pos + 1) + tabla + " (" + df.format(new Date()) + ") - fallidos.log";
			BufferedWriter writer;

			try {
				writer = new BufferedWriter(new FileWriter(fileName));
				for (String string : fallidos) {
					writer.write(string);
					writer.newLine();
				}
				writer.close();
				List<File> list = new ArrayList<File>();
				File f = new File(fileName);
				list.add(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		s.close();

	}
	
	public Map<String, Object> testQuery() {
		if(sessionFactory != null) {
			Session s = sessionFactory.openSession();
			
			Query query = s.createQuery("from Usuario where fecha < :fecha ");
			query.setParameter("fecha", new Date());
			System.out.print(query.getQueryString());
			query.list().size();
			QueryStatistics qs = s.getSessionFactory().getStatistics().getQueryStatistics( query.getQueryString() );
			System.out.print(query.getQueryString());
			qs.getExecutionMinTime();
			Map<String, Object> stats = new HashMap<String, Object>();
			stats.put("minExecTime", qs.getExecutionMinTime());
			stats.put("maxExecTime", qs.getExecutionMaxTime());
			stats.put("avgExecTime", qs.getExecutionAvgTime());
			
			s.close();
			
			return stats;
		}
		else {
			SqlSession session = sqlSessionFactory.openSession();
			return null;
		}
	}
	
	public Map<String, Object> genericQuery(String query) {
		if(sessionFactory != null) {
			Session s = sessionFactory.openSession();
			
			SQLQuery sqlQuery = s.createSQLQuery(query);
			sqlQuery.list();
			QueryStatistics qs = s.getSessionFactory().getStatistics().getQueryStatistics( sqlQuery.getQueryString() );
			Map<String, Object> stats = new HashMap<String, Object>();
			stats.put("minExecTime", qs.getExecutionMinTime());
			stats.put("maxExecTime", qs.getExecutionMaxTime());
			stats.put("avgExecTime", qs.getExecutionAvgTime());
			
			s.close();
			
			return stats;
		}
		else {
			SqlSession session = sqlSessionFactory.openSession();
			return null;
		}
	}
}