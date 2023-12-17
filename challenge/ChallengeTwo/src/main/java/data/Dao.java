package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import javax.persistence.*;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Dao <T>{
    private static final Logger logger = LogManager.getLogger(Dao.class);
    private static final Object LOCK = new Object();
    private static SessionFactory sessionFactory = null;
    private static List<Class<?>> models = null;
    private static HashMap<Class<?>, Object> instances = new HashMap<>();

    static {
        try {
            init();
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public T getById (Class<T> tableName, Long itemId) throws HibernateException {
        T item = null;
        try (Session session = sessionFactory.openSession()) {
            item = (T) session.get(tableName, itemId);
        }
        return item;
    }

    public List<T> select (String sql, Class<T> tableClass, HashMap<String, Object> parameters) throws HibernateException {
        List<T> listItems = null;
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery(sql, tableClass);
            for (String p : parameters.keySet() ) {
                query.setParameter(p, parameters.get(p));
            }
            listItems = query.getResultList();
        }
        return listItems;
    }

    public List<T> select (String sql, Class<T> tableClass) throws HibernateException {
        List<T> listItems = null;
        try (Session session = sessionFactory.openSession()){
            listItems = session.createQuery(sql, tableClass).list();
        }
        return listItems;
    }

    public List<T> select (String sql, Class<T> tableClass, int pageNum, int pageSize) throws HibernateException {
        List<T> listItems = null;
        try (Session session = sessionFactory.openSession()){
            listItems = session.createQuery(sql, tableClass).setFirstResult(pageNum * pageSize).setMaxResults(pageSize).list();
        }
        return listItems;
    }

    public static <T> T getInstance(Class<T> U){
        Object instance = instances.get(U);
        if (instance == null) {
            try {
                instances.put(U, U.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                logger.error("", e);
            }
        }
        return (T) instances.get(U);
    }

    private static void init() {
        logger.info("Start Dao Init ... ");
        if (sessionFactory == null || sessionFactory.isClosed()) {
            synchronized (LOCK) {
                if (sessionFactory == null || sessionFactory.isClosed()) {
                    try {
                        Configuration config = new Configuration();
                        getDataModelClasses();

                        if (models != null) {
                            for (Class<?> c : models) {
                                config.addAnnotatedClass(c);
                            }
                        }
                        config.setProperty("hibernate.connection.pool_size", "10");
                        config.setProperty("hibernate.connection.autocommit", "true");
                        config.setProperty("hibernate.show_sql", "true");
                        config.setProperty("hibernate.hbm2ddl.auto", "update");
                        config.setProperty("hibernate.generate_statistics", "true");
                        config.setProperty("hibernate.default_schema", "test");
                        config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                        config.setProperty("hibernate.connection.url", "jdbc:postgresql://127.0.0.1:5432/postgres");
                        config.setProperty("hibernate.connection.username", "abdin");
                        config.setProperty("hibernate.connection.password", "abdin@123");
                        config.setProperty("hibernate.connection.isolation", "4");
                        config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                        config.setProperty("hibernate.current_session_context_class", "thread");
                        sessionFactory = config.buildSessionFactory();
                        logger.info("sessionFactory >>> " + sessionFactory);
                    } catch (HibernateException e){
                        logger.error("cant init sessionFactory", e);
                    }
                }
            }
        }
    }

    private synchronized static void getDataModelClasses() {
        if (models == null) {
            Reflections reflections = new Reflections("data.model");
            Set<Class<?>> cls = reflections.getTypesAnnotatedWith(Entity.class);
            models = new LinkedList<Class<?>>();
            models.addAll(cls);
        }
    }
}
