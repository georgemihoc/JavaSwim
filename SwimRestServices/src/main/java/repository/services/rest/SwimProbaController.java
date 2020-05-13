package repository.services.rest;

import model.Proba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ProbaDbRepository;


@RestController
@CrossOrigin
@RequestMapping("/swim/probe")
public class SwimProbaController {

    private static final String template = "Hello, %s!";

    @Autowired
    private ProbaDbRepository probaRepository;

    //test greeting
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    //Show all probe
    @RequestMapping( method=RequestMethod.GET)
    public Iterable<Proba> getAll(){
        return probaRepository.findAll();
    }

    //Get proba by id
    @RequestMapping(value = "/{idProba}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int idProba){
        Proba proba=probaRepository.findOne(idProba);
        if (proba==null)
            return new ResponseEntity<String>("Proba not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Proba>(proba, HttpStatus.OK);
    }

    // Create and save
    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba proba){
            probaRepository.save(proba);
            return proba;

    }

    // Update proba
    @RequestMapping(value = "/{idProba}", method = RequestMethod.PUT)
    public Proba update(@RequestBody Proba proba) {
            System.out.println("Updating proba ...");
             probaRepository.update(proba.getIdProba(),proba);
             return proba;
    }

    //Delete proba
    @RequestMapping(value="/{index}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable int index){
        System.out.println("Deleting proba ... "+ probaRepository.findOne(index));
        try {
            probaRepository.delete(index);
            return new ResponseEntity<Proba>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Delete proba exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}
