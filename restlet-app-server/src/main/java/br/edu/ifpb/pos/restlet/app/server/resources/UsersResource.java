package br.edu.ifpb.pos.restlet.app.server.resources;

import br.edu.ifpb.pos.restlet.app.server.dao.UserDao;
import br.edu.ifpb.pos.restlet.app.server.entities.User;
import br.edu.ifpb.pos.restlet.app.server.utils.ConvertJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class UsersResource extends ServerResource{

    private UserDao dao = new UserDao();
    
    @Post
    public Representation save(Representation r) {
        try {
            dao.save(ConvertJson.convertJsonToUser(r.getText()));
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
//    public Representation update(Representation r) {
//        try {
//            dao.update(ConvertJson.convertJsonToUser(r.getText()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new StringRepresentation("{"
//                    + "error: 500 - "+e.getMessage()
//                    + "}");
//        }
//        return new StringRepresentation("{"
//                    + "message: 201 - Update successful"
//                    + "}");
//    }
    
    @Get
    public Representation getUser(){
        try{            
            return new StringRepresentation(new Gson().toJson(dao.find(getQuery().getValues("person_code")
                    , getQuery().getValues("name")
                    , getQuery().getValues("password"))));
        }catch(Exception e){            
            return new StringRepresentation("{"
                    + "error: " + e.getMessage()
                    + "}");
        }
    }
    
    @Delete
    public Representation deleteUser(){
        try{           
            List<User> users = dao.find(getQuery().getValues("person_code")
                    , getQuery().getValues("name")
                    , getQuery().getValues("password"));
            for (User user : users)
                dao.delete(user);
            return new StringRepresentation("{"
                    + "rows_deleted: "+users.size()
                    + "}");
        }catch(Exception e){
            return new StringRepresentation("{"
                    + "error: " + e.getMessage()
                    + "}");
        }
    }

}
