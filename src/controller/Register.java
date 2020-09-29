package controller;

import model.BL.DBOperations.TBLPersonImageBlob;
import model.BL.DBOperations.TBLPersonImageByte;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by asus on 5/17/2020.
 */
public class Register extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String name =request.getParameter("name");
        String familly= request.getParameter("familly");


        TBLPersonImageByte tbl_personImageByte = new TBLPersonImageByte( name ,  familly);
        TBLPersonImageBlob tblPersonImageBlob = new TBLPersonImageBlob( name ,  familly);

        tbl_personImageByte.main();
        try {
            tblPersonImageBlob.main();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("welcome.jsp");



    }
}
