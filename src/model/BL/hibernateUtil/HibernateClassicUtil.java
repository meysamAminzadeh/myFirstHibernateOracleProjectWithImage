package model.BL.hibernateUtil;

import org.hibernate.*;

import org.hibernate.cfg.*;


public class HibernateClassicUtil {
    private static final SessionFactory session_factory;

    static {

        session_factory = new Configuration()
                .configure("hibernate.cfg.xml")
                 .buildSessionFactory();


    }


    public static Session getSession() {
        ThreadLocal thread_session = new ThreadLocal();
        Session session = (Session) thread_session.get();


        if (session == null ) {
            session = session_factory.openSession();
            thread_session.set(session);
        }

        return session;
    }
}


