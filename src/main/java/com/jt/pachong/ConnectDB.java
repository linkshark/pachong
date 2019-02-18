package com.jt.pachong;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class ConnectDB {
    private Configuration configuration;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
	
	 public void init(){
	        configuration = new Configuration().configure();
	        sessionFactory = configuration.buildSessionFactory();
	        session =sessionFactory.openSession();

	    }
	 public void insert(Job job) {
	        transaction = session.beginTransaction();
	        
	        
	        session.save(job);
	        transaction.commit();
	    }
	    public void exit()
	    {

	        session.close();
	        sessionFactory.close();
	    }
	 

}
