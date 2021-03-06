package util;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory = null;

    public static void setSession() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Diagnosis.class);
            configuration.addAnnotatedClass(Examination.class);
            configuration.addAnnotatedClass(Patient.class);
            configuration.addAnnotatedClass(Specialization.class);
            configuration.addAnnotatedClass(Staff.class);
            configuration.addAnnotatedClass(Position.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doInHibernateSession(Consumer<Session> sessionConsumer) {
        try (Session session = sessionFactory.openSession()) {
            sessionConsumer.accept(session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}