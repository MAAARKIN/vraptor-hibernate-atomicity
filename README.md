# VRaptor Hibernate Atomicity Plugin

VRaptor Hibernate Atomicity Plugin provides support to use with Hibernate 4. 
This plugin is based on [vRaptor-Hibernate](https://github.com/caelum/vraptor-hibernate)

# How to install?

You only need to copy the jar to your classpath. VRaptor will register plugin when 
your application starts without any configurations.

# Transactional Control

The default behavior is that each request will have a transaction available (Open Session In View). Using the atomicity plugin, you need to define where the transactions will start through of @Atomic annotation.


#Using the Atomicity

You can open the transaction in controller or in any layer of your project. I recommend you use the @Atomic annotation in your GenericDAO (if you use), because if you forget to open your transaction your DAO will open the transactions. Without the annotation you can't commit.

You can use more then one annotation in your stream code, the transaction will be opened on the first call. If the annotation is called again, the transaction will be reused.


```java
@Atomic
@Controller
public class MyController {
	public void form() {
		//...
	}
}
```

```java

@Atomic
public abstract class GenericDAO<T> implements Repository<T> {
	public GenericDAO(Class<T> entityClass,) {
		this.entityClass = entityClass;
	}
	public void save(T entity) {
		getCurrentSession().save(entity);
	}
}
```