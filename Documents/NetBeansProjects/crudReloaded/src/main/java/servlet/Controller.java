/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

/**
 *
 * @author JULIO
 */
public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("email");
            String password = request.getParameter("senha");

            try {
                if (username != null) {
                    Class.forName("org.postgresql.Driver").newInstance();
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/desafio", "postgres", "julio123");
                    String query = "select * from usuario where email = ? and senha = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    ResultSet resultset = preparedStatement.executeQuery();
                    if (resultset.next()) {
                        response.sendRedirect("/crudReloaded/faces/usuario/List.xhtml");
                    } else {
                        response.sendRedirect("/crudReloaded");
                        JOptionPane.showMessageDialog(null, "Login ou senha inválido.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
