/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author erick
 */
public class ProjectController {

    public void Save(Project project) {
        String sql = "INSERT INTO projects (nome"
                + "description"
                + "createdAt"
                + "updatedAt) VALUES (?, ?, ?, ?) ";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("erro ao salvar o projeto " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void Update(Project project) {
        String sql = "Update projects SET"
                + "name = ?,"
                + "description = ?"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "Where id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao alterar o projeto" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void remobeById(int projectId) {
        String sql = "DELETE FROM projects WHERE ID = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, projectId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar o projeto" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Project> projects = new ArrayList<Project>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                //task.setId(resultSet.getInt("id"));
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setName(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projects.add(project);

            }
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao procurar as tarefas" + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return projects;
    }
    
}
