package dto;


import model.Inscriere;
import model.Organizator;
import model.Participant;
import model.Proba;

public class DTOUtils {
    public static Organizator getFromDTO(UserDTO usdto){
        String id=usdto.getId();
        String pass=usdto.getPasswd();
        return new Organizator(1,id, pass);

    }
    public static UserDTO getDTO(Organizator user){
        String id=user.getUsername();
        String pass=user.getPassword();
        return new UserDTO(user.getIdOrganizator(),id, pass);
    }

    public static Inscriere getFromDTO(InscriereDTO mdto){
        int idInscriere = mdto.getIdInscriere();
        int idParticipant = mdto.getIdParticipant();
        int idProba = mdto.getIdProba();
        return new Inscriere(idInscriere, idParticipant, idProba);

    }

    public static InscriereDTO getDTO(Inscriere inscriere, String nume, int varsta){
        int inscriereId=inscriere.getIdInscriere();
        int participantId = inscriere.getIdParticipant();
        int probaId = inscriere.getIdProba();
        return new InscriereDTO(inscriereId, participantId, probaId, nume, varsta);
    }

//    public static UserDTO[] getDTO(User[] users){
//        UserDTO[] frDTO=new UserDTO[users.length];
//        for(int i=0;i<users.length;i++)
//            frDTO[i]=getDTO(users[i]);
//        return frDTO;
//    }
//
    public static Organizator[] getFromDTO(UserDTO[] users){
        Organizator[] loggedUsers=new Organizator[users.length];
        for(int i=0;i<users.length;i++){
            loggedUsers[i]=getFromDTO(users[i]);
        }
        return loggedUsers;
    }
    public static Proba getFromDTO(ProbaDTO usdto){
        return new Proba(usdto.getIdProba(),usdto.getLungime(),usdto.getStil(),usdto.getNrParticipanti());
    }
    public static Proba[] getFromDTO(ProbaDTO[] probe){
        Proba[] friends = new Proba[probe.length];
        for(int i = 0;i< probe.length; i++){
            friends[i] = getFromDTO(probe[i]);
        }
        return friends;
    }
    public static ProbaDTO getDTO(Proba user){
//        String id=user.getId();
//        String pass=user.getPasswd();
        return new ProbaDTO(user.getIdProba(),user.getLungime(),user.getStil(),user.getNrParticipanti());
    }
    public static ProbaDTO[] getDTO(Proba[] users){
        ProbaDTO[] frDTO=new ProbaDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }
    ////PARTICIPANT
    public static Participant getFromDTO(ParticipantDTO usdto){
        return new Participant(usdto.idParticipant,usdto.nume,usdto.varsta);
    }
    public static Participant[] getFromDTO(ParticipantDTO[] probe){
        Participant[] friends = new Participant[probe.length];
        for(int i = 0;i< probe.length; i++){
            friends[i] = getFromDTO(probe[i]);
        }
        return friends;
    }
    public static ParticipantDTO getDTO(Participant user){
        return new ParticipantDTO(user.getIdParticipant(),user.getNume(),user.getVarsta());
    }
    public static ParticipantDTO[] getDTO(Participant[] users){
        ParticipantDTO[] frDTO=new ParticipantDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }

    ///INSCRIERE
    public static Inscriere[] getFromDTO(InscriereDTO[] probe){
        Inscriere[] friends = new Inscriere[probe.length];
        for(int i = 0;i< probe.length; i++){
            friends[i] = getFromDTO(probe[i]);
        }
        return friends;
    }
    public static InscriereDTO getDTO(Inscriere user){
        return new InscriereDTO(user.getIdInscriere(),user.getIdParticipant(),user.getIdProba(),"null",0);
    }
    public static InscriereDTO[] getDTO(Inscriere[] users){
        InscriereDTO[] frDTO=new InscriereDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }
}
