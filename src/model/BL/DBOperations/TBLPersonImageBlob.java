package model.BL.DBOperations;

import model.BL.ORM.PersonTO;
import model.BL.hibernateUtil.HibernateClassicUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by asus on 5/19/2020.
 */
public class TBLPersonImageBlob {



    private  ServiceRegistry serviceRegistry;
    private  Session session;
    private  long personId;
    PersonTO personTO;

    public TBLPersonImageBlob(String name , String familly){
        personTO = new PersonTO();
        personTO.setName(name);
        personTO.setFamily(familly);

    }

    public  void main() throws IOException, SQLException {
        initSession();

        String photoFilePathToRead = "D:\\avina5.png";
        savePersonWithPhoto(photoFilePathToRead);

        endSession();

        initSession();

        String photoFilePathToSave = "D:\\avinaBlob.png";
        readPhotoOfPerson(personId, photoFilePathToSave);

        endSession();
    }

    private  void initSession() {
       /* Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
         session = sessionFactory.openSession();
*/
        session = HibernateClassicUtil.getSession();
        session.beginTransaction();
    }


    private  void savePersonWithPhoto(String photoFilePath) throws IOException, SQLException {

        File file = new File(photoFilePath);
        FileInputStream inputStream = new FileInputStream(file);
        Blob blob = Hibernate.getLobCreator(session)
                .createBlob(inputStream, file.length());
        personTO.setImage(blob);
        session.save(personTO);
        personId = personTO.getId();
        System.out.println(personId);
       // blob.free();
    }

    private  void readPhotoOfPerson(long personId, String photoFilePath) throws IOException, SQLException {
        PersonTO personTO = (PersonTO) session.get(PersonTO.class, personId);
        Blob blob = personTO.getImage();
        byte[] blobBytes = blob.getBytes(1, (int) blob.length());
        saveBytesToFile(photoFilePath, blobBytes);
      //  blob.free();
    }

    private  void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(fileBytes);
        outputStream.close();
    }



    private  void endSession() {
        session.getTransaction().commit();
        session.close();

        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }





}
