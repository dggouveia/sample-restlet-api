package br.edu.ifpb.pos.restlet.app.server.resources;

import br.edu.ifpb.pos.restlet.app.server.dao.UserDao;
import br.edu.ifpb.pos.restlet.app.server.entities.User;
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
public class UserResource extends ServerResource{

    private UserDao dao = new UserDao();
    
    @Get
    public Representation getUser(){
        try{            
            String code = (String) getRequest().getAttributes().get("code");
            return new StringRepresentation(new Gson().toJson(dao.find(code, "", "").get(0)));
        }catch(Exception e){
            e.printStackTrace();
            return new StringRepresentation("{"
                    + "error: "+e.getMessage()
                    + "}");
        }
    }
    
    @Put
    public Representation update(Representation r) {
        try {
            String code = (String) getRequest().getAttributes().get("code");
            dao.update(code, ConvertJson.convertJsonToUser(r.getText()));
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
    public Representation deleteUser(){
        try{         
            String code = (String) getRequest().getAttributes().get("code");
            User user = dao.find(code, "", "").get(0);            
            dao.delete(user);
            return new StringRepresentation("{"
                    + "rows_deleted: 1"
                    + "}");
        }catch(Exception e){
            return new StringRepresentation("{"
                    + "error: "+e.getMessage()
                    + "}");
        }
    }
    
}
