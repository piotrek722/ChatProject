package agh.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.io.File;

/**
 * Created by Peter on 2015-12-05.
 * Project name : ChatProject
 */
public class HibernateUtils {

    private static SessionFactory sessionFactory;

    static{
        Configuration configuration = new Configuration();
        configuration.configure(new File("Chat/hibernate.cfg.xml"));
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
