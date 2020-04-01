package dto;


import model.Inscriere;
import model.Organizator;

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
}
