package br.com.caelum.vraptor.hibernate.atomicity;

import br.com.caelum.vraptor.environment.Environment;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.net.URL;

public class ConfigurationCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationCreator.class);

	private final Environment environment;
	
	/**
	 * @deprecated cdi eyes only
	 */	
	protected ConfigurationCreator() {
		this(null);	
	}

	@Inject
	public ConfigurationCreator(Environment environment) {
		this.environment = environment;	
	}

	protected URL getHibernateCfgLocation() {
		return environment.getResource("/hibernate.cfg.xml");
	}

	@Produces
	@ApplicationScoped
	public Configuration getInstance() {
		Configuration configuration = new Configuration();
		URL location = getHibernateCfgLocation();
		LOGGER.debug("building configuration using {} file", location);
		return configuration.configure(location);
	}
}
