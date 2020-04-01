package repository;


import model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ParticipantDbRepository implements IRepository<Integer, Participant> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ParticipantDbRepository(Properties props){
        logger.info("Initializing ParticipantDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Participant")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Participant entity) {
        logger.traceEntry("saving participant {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Participant values (?,?,?)")){
            preStmt.setInt(1,entity.getIdParticipant());
            preStmt.setString(2,entity.getNume());
            preStmt.setInt(3,entity.getVarsta());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting task with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Participant where idParticipant=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Participant entity) {
        //To do
    }

    @Override
    public Participant findOne(Integer integer) {
        logger.traceEntry("finding task with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant where idParticipant=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int idParticipant = result.getInt("idParticipant");
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    Participant participant = new Participant(idParticipant, nume, varsta);
                    logger.traceExit(participant);
                    return participant;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Participant> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idParticipant = result.getInt("idParticipant");
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    Participant participant = new Participant(idParticipant, nume, varsta);
                    tasks.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(tasks);
        return tasks;
    }



}
