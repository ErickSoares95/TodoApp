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
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author erick
 */
public class TaskController {

    public void save(Task task) {
        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + " notes,"
                + " completed,"
                + " deadline,"
                + " createdAt,"
                + " updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        //Serve para preparar enviar os comandos sql
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("erro ao salvar a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }

    }

    public void update(Task task) {

        String sql = "UPDATE tasks Set"
                + " idProject = ?,"
                + " name = ?,"
                + " description = ?,"
                + " notes = ?,"
                + " completed = ?,"
                + " deadline = ?,"
                + " createdAt = ?,"
                + " updatedAt = ?"
                + "WHERE id = ?";

        Connection connection = null;
        //Serve para preparar enviar os comandos sql
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //primeiro parametro (?) será substituido por taskId

            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());

            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao alterar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void removeById(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        //Serve para preparar enviar os comandos sql
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //primeiro parametro (?) será substituido por taskId
            statement.setInt(1, taskId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        //Serve para preparar enviar os comandos sql
        PreparedStatement statement = null;
        //Classe que guarda a consulta feita no banco de dados
        ResultSet resultSet = null;
        List<Task> tasks = new ArrayList<Task>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
                
            }
                   
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao procurar as tarefas" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return tasks;
    }

}
