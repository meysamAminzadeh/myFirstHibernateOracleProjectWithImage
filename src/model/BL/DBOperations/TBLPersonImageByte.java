package model.BL.DBOperations;

import model.BL.ORM.Person2TO;
import model.BL.hibernateUtil.HibernateClassicUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class TBLPersonImageByte
{

    private  ServiceRegistry serviceRegistry;
    private  Session session;
    private  Transaction transaction;
    private long personId ;
    Person2TO person2TO;

    public TBLPersonImageByte(String name , String familly){

        person2TO = new Person2TO();
        person2TO.setName(name);
        person2TO.setFamily(familly);

    }

    public void main() throws IOException {
        initSession();


        String photoFilePathToRead = "D:\\avina5.png";
        savePersonWithPhoto(photoFilePathToRead);

        endSession();

        initSession();
        String photoFilePathToSave = "D:\\avinaByte.png";
        readPhotoOfPerson(personId, photoFilePathToSave);

        endSession();
    }
    private  void initSession() {
       /* Configuration configuration = new Configuration().configure();
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        session = sessionFactory.openSession();*/
        session = HibernateClassicUtil.getSession();
        session.beginTransaction();

    }

    //******************************************************************************************************

    private  void savePersonWithPhoto(String photoFilePath) throws IOException {

        byte[] photoBytes = readBytesFromFile(photoFilePath);
        person2TO.setImage(photoBytes);
        session.save(person2TO);
        personId = person2TO.getId();
    }

    private byte[] readBytesFromFile(String filePath) throws IOException {
        File inputFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(inputFile);

        byte[] fileBytes = new byte[(int) inputFile.length()];
        inputStream.read(fileBytes);
        inputStream.close();

        return fileBytes;
    }
    //--------------------------------------------------------------------------------------------

    private void readPhotoOfPerson(long personId, String photoFilePath) throws IOException {
        Person2TO person2TO = (Person2TO) session.get(Person2TO.class, personId);
        byte[] photoBytes = person2TO.getImage();
        saveBytesToFile(photoFilePath, photoBytes);
    }

   private static void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(fileBytes);
        outputStream.close();
    }

   //****************************************************************************************************
    private  void endSession() {
        session.getTransaction().commit();
        session.close();

        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }



}










