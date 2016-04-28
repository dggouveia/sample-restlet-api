package br.edu.ifpb.pos.restlet.app.server.utils;

import br.edu.ifpb.pos.restlet.app.server.entities.Person;
import br.edu.ifpb.pos.restlet.app.server.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class ConvertJson {

    public static User convertJsonToUser(String json) {
        Gson gson = new GsonBuilder().create();
        User p = gson.fromJson(json, User.class);
        return p;
    }
    
    public static Person convertJsonToPerson(String json) {
        Gson gson = new GsonBuilder().create();
        Person p = gson.fromJson(json, Person.class);
        return p;
    }
    
}
