package br.com.caelum.vraptor.hibernate.atomicity;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Atomic
public class TransactionInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionInterceptor.class);
	@Inject private Session session;

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		
		if (session.getTransaction().isActive()) {
			LOGGER.debug("tx active");
			return ctx.proceed();
		}
		
		LOGGER.debug("intercept");
		Transaction transaction = session.beginTransaction();
		
		Object result = null;
		try {
			result = ctx.proceed();
			commit(transaction);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
				session.clear();
				LOGGER.debug("tx rollback and session cleared");
			}
		}
		return result;
	}

	private void commit(Transaction transaction) {
		if (transaction.isActive()) {
			LOGGER.debug("tx commit");
			transaction.commit();
		}
	}
}