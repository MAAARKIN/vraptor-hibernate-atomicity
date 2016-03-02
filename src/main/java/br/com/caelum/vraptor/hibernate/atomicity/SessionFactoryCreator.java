package br.com.caelum.vraptor.hibernate.atomicity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionFactoryCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionFactoryCreator.class);
	private Configuration cfg;
	private ServiceRegistry serviceRegistry;

	@Inject
	public SessionFactoryCreator(Configuration cfg, ServiceRegistry serviceRegistry) {
		this.cfg = cfg;
		this.serviceRegistry = serviceRegistry;
	}

	@Produces
	@ApplicationScoped
	public SessionFactory getInstance() {
		LOGGER.debug("creating a session factory");
		return cfg.buildSessionFactory(serviceRegistry);
	}

	public void destroy(@Disposes SessionFactory sessionFactory) {
		LOGGER.debug("destroying session factory");
		sessionFactory.close();
	}
}