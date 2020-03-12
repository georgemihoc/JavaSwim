package repository;

import model.Organizator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class OrganizatorDbRepository implements IRepository<Integer, Organizator> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public OrganizatorDbRepository(Properties props){
        logger.info("Initializing OrganizatorDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Organizator")) {
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
    public void save(Organizator entity) {
        logger.traceEntry("saving Organizator {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Organizator values (?,?,?)")){
            preStmt.setInt(1,entity.getIdOrganizator());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
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
        try(PreparedStatement preStmt=con.prepareStatement("delete from Organizator where idOrganizator=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Organizator entity) {
        //To do
    }

    @Override
    public Organizator findOne(Integer integer) {
        logger.traceEntry("finding task with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Organizator where idOrganizator=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int idOrganizator = result.getInt("idOrganizator");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Organizator Organizator = new Organizator(idOrganizator, username, password);
                    logger.traceExit(Organizator);
                    return Organizator;
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
    public Iterable<Organizator> findAll() {
        //logger.();
        Connection con=dbUtils.getConnection();
        List<Organizator> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Organizator")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idOrganizator = result.getInt("idOrganizator");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Organizator Organizator = new Organizator(idOrganizator, username, password);
                    tasks.add(Organizator);
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
