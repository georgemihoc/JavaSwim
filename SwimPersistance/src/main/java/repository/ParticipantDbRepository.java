package repository;


import model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Properties;

public class ParticipantDbRepository implements IRepository<Integer, Participant> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ParticipantDbRepository(Properties props){
        logger.info("Initializing ParticipantDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);

    }

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    @Override
    public int size() {
//        logger.traceEntry();
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Participant")) {
//            try(ResultSet result = preStmt.executeQuery()) {
//                if (result.next()) {
//                    logger.traceExit(result.getInt("SIZE"));
//                    return result.getInt("SIZE");
//                }
//            }
//        }catch(SQLException ex){
//            logger.error(ex);
//            System.out.println("Error DB "+ex);
//        }
//        return 0;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                int size =
                        session.createQuery("from  Participant", Participant.class).
                                //  setFirstResult(1).setMaxResults(5).
                                        list().size();
                System.out.println(size + " participant(s) found:");
                close();
                tx.commit();
                return size;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        close();
        return 0;
    }

    @Override
    public void save(Participant entity) {
//        Old method
//        logger.traceEntry("saving participant {} ",entity);
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("insert into Participant values (?,?,?)")){
//            preStmt.setInt(1,entity.getIdParticipant());
//            preStmt.setString(2,entity.getNume());
//            preStmt.setInt(3,entity.getVarsta());
//            int result=preStmt.executeUpdate();
//        }catch (SQLException ex){
//            logger.error(ex);
//            System.out.println("Error DB "+ex);
//        }
//        logger.traceExit();
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        close();
    }

    @Override
    public void delete(Integer integer) {
//        Old method
//        logger.traceEntry("deleting task with {}",integer);
//        Connection con=dbUtils.getConnection();
//        try(PreparedStatement preStmt=con.prepareStatement("delete from Participant where idParticipant=?")){
//            preStmt.setInt(1,integer);
//            int result=preStmt.executeUpdate();
//        }catch (SQLException ex){
//            logger.error(ex);
//            System.out.println("Error DB "+ex);
//        }
//        logger.traceExit();
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Participant participant = findOne(integer);
                System.err.println("Delete participant " + participant.getIdParticipant());
                session.delete(participant);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        close();
    }

    @Override
    public void update(Integer integer, Participant entity) {
        //To do
    }

    @Override
    public Participant findOne(Integer integer) {
//        logger.traceEntry("finding task with id {} ",integer);
//        Connection con=dbUtils.getConnection();
//
//        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant where idParticipant=?")){
//            preStmt.setInt(1,integer);
//            try(ResultSet result=preStmt.executeQuery()) {
//                if (result.next()) {
//                    int idParticipant = result.getInt("idParticipant");
//                    String nume = result.getString("nume");
//                    int varsta = result.getInt("varsta");
//                    Participant participant = new Participant(idParticipant, nume, varsta);
//                    logger.traceExit(participant);
//                    return participant;
//                }
//            }
//        }catch (SQLException ex){
//            logger.error(ex);
//            System.out.println("Error DB "+ex);
//        }
//        logger.traceExit("No task found with id {}", integer);
//
//        return null;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Participant where idParticipant=" + integer;
                Participant participant = session.createQuery(queryString, Participant.class).getSingleResult();
                System.out.println(participant.getIdParticipant()+ " " + participant.getNume() + " " + participant.getVarsta());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        close();
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
//        Old method
//        Connection con=dbUtils.getConnection();
//        List<Participant> tasks=new ArrayList<>();
//        try(PreparedStatement preStmt=con.prepareStatement("select * from Participant")) {
//            try(ResultSet result=preStmt.executeQuery()) {
//                while (result.next()) {
//                    int idParticipant = result.getInt("idParticipant");
//                    String nume = result.getString("nume");
//                    int varsta = result.getInt("varsta");
//                    Participant participant = new Participant(idParticipant, nume, varsta);
//                    tasks.add(participant);
//                }
//            }
//        } catch (SQLException e) {
//            logger.error(e);
//            System.out.println("Error DB "+e);
//        }
//        logger.traceExit(tasks);
//        return tasks;
        initialize();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Participant> participanti =
                        session.createQuery("from Participant as p order by p.idParticipant asc", Participant.class).getResultList();
                System.out.println(participanti.size() + " participant(s) found:");
                tx.commit();
                close();
                return participanti;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        close();
        return null;
    }



}
