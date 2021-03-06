package com.revolut.tonsaito.app;

import java.io.File;
import java.net.MalformedURLException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.revolut.tonsaito.config.ResourceLoader;
import com.revolut.tonsaito.dao.ClientDAO;
import com.revolut.tonsaito.dao.TransactionDAO;

public class Application {
	private Tomcat tomcat;
	
	public static void main(String[] args) throws LifecycleException, MalformedURLException, ServletException {
		Application app = new Application();
		app.initDbConfigurations();
		app.start();
		app.awaitServer();
	}

	public void initDbConfigurations() {
		ClientDAO.createEntity();
		ClientDAO.generateData();
		TransactionDAO.createEntity();
	}

	public void start() throws ServletException, LifecycleException, MalformedURLException {
		instanceServer();
		this.tomcat.setPort(8080);

		Context context = this.tomcat.addWebapp("/", new File(".").getAbsolutePath());

		Tomcat.addServlet(context, "jersey-container-servlet", resourceConfig());
		context.addServletMapping("/*", "jersey-container-servlet");

		this.tomcat.start();
	}
	
	public void awaitServer() {
		if(this.tomcat != null) {
			this.tomcat.getServer().await();
		}
	}
	
	public void instanceServer() {
		if(this.tomcat == null) {
			this.tomcat = new Tomcat();
		}
	}
	
	public void stop() throws LifecycleException {
		this.tomcat.getServer().stop();
	}

	private ServletContainer resourceConfig() {
		return new ServletContainer(new ResourceConfig(new ResourceLoader().getClasses()));
	}

}