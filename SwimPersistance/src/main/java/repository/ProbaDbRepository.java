package repository;

import model.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ProbaDbRepository implements IRepository<Integer, Proba> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ProbaDbRepository(Properties props){
        logger.info("Initializing ProbaDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Proba")) {
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
    public void save(Proba entity) {
        logger.traceEntry("saving model.Proba {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Proba values (?,?,?,?)")){
            preStmt.setInt(1,entity.getIdProba());
            preStmt.setInt(2,entity.getLungime());
            preStmt.setString(3,entity.getStil());
            preStmt.setInt(4,entity.getNrParticipanti());
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
        try(PreparedStatement preStmt=con.prepareStatement("delete from Proba where idProba=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Proba entity) {
        logger.traceEntry("updating element with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Proba set idProba = ? , lungime = ?, stil = ?,nrParticipanti=? where idProba=?")){
            preStmt.setInt(1,integer);
            preStmt.setInt(2,entity.getLungime());
            preStmt.setString(3,entity.getStil());
            preStmt.setInt(4,entity.getNrParticipanti());
            preStmt.setInt(5,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();    }

    @Override
    public Proba findOne(Integer integer) {
        logger.traceEntry("finding task with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Proba where idProba=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int idProba = result.getInt("idProba");
                    int lungime = result.getInt("lungime");
                    String stil = result.getString("stil");
                    int nrParticipanti = result.getInt("nrParticipanti");
                    Proba Proba = new Proba(idProba, lungime,stil, nrParticipanti);
                    logger.traceExit(Proba);
                    return Proba;
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
    public Iterable<Proba> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Proba> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Proba")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idProba = result.getInt("idProba");
                    int lungime = result.getInt("lungime");
                    String stil = result.getString("stil");
                    int nrParticipanti = result.getInt("nrParticipanti");
                    Proba Proba = new Proba(idProba, lungime,stil, nrParticipanti);
                    tasks.add(Proba);
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
