package br.edu.ifpb.pos.restlet.app.server.resources;

import br.edu.ifpb.pos.restlet.app.server.dao.PersonDao;
import br.edu.ifpb.pos.restlet.app.server.entities.Person;
import br.edu.ifpb.pos.restlet.app.server.utils.ConvertJson;
import com.google.gson.Gson;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class PersonResource extends ServerResource{

    private PersonDao dao = new PersonDao();
    
    @Get
    public Representation get(){
        try{            
            String code = (String) getRequest().getAttributes().get("code");
            return new StringRepresentation(new Gson().toJson(dao.find(code, "", "").get(0)));
        }catch(Exception e){
            return new StringRepresentation("{"
                    + "error: "+e.getMessage()
                    + "}");
        }
    }
    
    @Put
    public Representation update(Representation r) {
        try {
            String code = (String) getRequest().getAttributes().get("code");
            dao.update(code, ConvertJson.convertJsonToPerson(r.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            return new StringRepresentation("{"
                    + "error: "+e.getMessage()
                    + "}");
        }
        return new StringRepresentation("{"
                    + "message: Update successful"
                    + "}");
    }
    
    @Delete
    public Representation delete(){
        try{         
            String code = (String) getRequest().getAttributes().get("code");
            Person person = dao.find(code, "", "").get(0);            
            dao.delete(person);
            return new StringRepresentation("{"
                    + "rows_deleted: 1"
                    + "}");
        }catch(Exception e){
            e.printStackTrace();
            return new StringRepresentation("{"
                    + "error: 500"
                    + "}");
        }
    }
    
}
