package repository;

import model.Inscriere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class InscriereDbRepository implements IRepository<Integer, Inscriere> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public InscriereDbRepository(Properties props){
        logger.info("Initializing InscriereDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Inscriere")) {
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
    public void save(Inscriere entity) {
        logger.traceEntry("saving Inscriere {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Inscriere values (?,?,?)")){
            preStmt.setInt(1,entity.getIdInscriere());
            preStmt.setString(2,entity.getIdParticipant());
            preStmt.setString(3,entity.getIdProba());
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
        try(PreparedStatement preStmt=con.prepareStatement("delete from Inscriere where idInscriere=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Inscriere entity) {
        //To do
    }

    @Override
    public Inscriere findOne(Integer integer) {
        logger.traceEntry("finding task with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Inscriere where idInscriere=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int idInscriere = result.getInt("idInscriere");
                    String idParticipant = result.getString("idParticipant");
                    String idProba = result.getString("idProba");
                    Inscriere Inscriere = new Inscriere(idInscriere, idParticipant, idProba);
                    logger.traceExit(Inscriere);
                    return Inscriere;
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
    public Iterable<Inscriere> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Inscriere> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Inscriere")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idInscriere = result.getInt("idInscriere");
                    String idParticipant = result.getString("idParticipant");
                    String idProba = result.getString("idProba");
                    Inscriere Inscriere = new Inscriere(idInscriere, idParticipant,idProba);
                    tasks.add(Inscriere);
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
