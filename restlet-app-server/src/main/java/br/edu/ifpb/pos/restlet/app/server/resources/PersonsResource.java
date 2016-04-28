package br.edu.ifpb.pos.restlet.app.server.resources;

import br.edu.ifpb.pos.restlet.app.server.entities.Person;
import br.edu.ifpb.pos.restlet.app.server.dao.PersonDao;
import br.edu.ifpb.pos.restlet.app.server.utils.ConvertJson;
import com.google.gson.Gson;
import java.util.List;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class PersonsResource extends ServerResource {

    private PersonDao dao = new PersonDao();

    @Post
    public Representation save(Representation r) {
        try {
            dao.save(ConvertJson.convertJsonToPerson(r.getText()));
            return new StringRepresentation("{"
                    + "message: \"Entity created!\""
                    + "}");
        } catch (Exception e) {
            e.printStackTrace();
            return new StringRepresentation("{"
                    + "message: \"Can't create entity!\","
                    + "error: \"" + e.getMessage() +"\""
                    + "}");
        }
    }
    
//    @Put
//    public Status update(Representation r) {
//        try {
//            dao.update(ConvertJson.convertJsonToPerson(r.getText()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Status.CLIENT_ERROR_BAD_REQUEST;
//        }
//        return Status.SUCCESS_ACCEPTED;
//    }
    
    @Get
    public Representation getPersons(){
        try{            
            return new StringRepresentation(new Gson().toJson(dao.find(getQuery().getValues("code")
                    , getQuery().getValues("name")
                    , getQuery().getValues("address"))));
        }catch(Exception e){
            return new StringRepresentation("{"
                    + "error: " + e.getMessage()
                    + "}");
        }
    }
    
    @Delete
    public Representation deletePersons(){
        try{           
            List<Person> persons = dao.find(getQuery().getValues("code")
                    , getQuery().getValues("name")
                    , getQuery().getValues("address"));
            for (Person person : persons)
                dao.delete(person);
            return new StringRepresentation("{"
                    + "rows_deleted: "+persons.size()
                    + "}");
        }catch(Exception e){
            return new StringRepresentation("{"
                    + "error: " + e.getMessage()
                    + "}");
        }
    }

}
