package br.edu.ifpb.pos.restlet.app.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class ConcoleClient {

    private final String HELP = "help";
    private final String INSERT = "insert";
    private final String UPDATE = "update";
    private final String DELETE = "delete";
    private final String SELECT = "select";
    private final String SEARCH = "search";
    private final String TYPE = "type";
    private String type = "";
    private int hostIndex = 0;
    private String[] allowedTypes = {"json_person", "json_user"};
    private String[] hosts = {"http://localhost:8090/app/persons"
            , "http://localhost:8090/app/users"
            , "http://localhost:8090/app/person"
            , "http://localhost:8090/app/user"};
    private Options ops;

    public ConcoleClient() {
        ops = new Options();
        ops.addOption(null, HELP, false, "Show help");
        ops.addOption(null, TYPE, true, "Set the type of the entity to manipulate: [json_person | json_user]");
        ops.addOption(null, INSERT, true, "Insert a new instance");
        ops.addOption(null, SELECT, true, "Retrieve all instances that match with query");
        ops.addOption(null, DELETE, true, "Delete all instances that match with query");
        ops.addOption(null, UPDATE, true, "Update a instance that already exists");
        ops.addOption(null, SEARCH, true, "Make a search with parameters");
    }

    public void execute(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(ops, args);
        if (line.hasOption(HELP)) {
            printHelp();
        }
        if (line.hasOption(TYPE)) {
            setType(line.getOptionValue(TYPE));
        }
        if (line.hasOption(INSERT)) {
            insert(line.getOptionValue(INSERT));
        }
        if (line.hasOption(SELECT)) {
            select(line.getOptionValue(SELECT));
        }
        if (line.hasOption(DELETE)) {
            delete(line.getOptionValue(DELETE));
        }
        if (line.hasOption(UPDATE)) {
            update(line.getOptionValue(UPDATE));
        }
        if (line.hasOption(SEARCH)) {
            search(line.getOptionValue(SEARCH));
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("--[insert | update | delete | select] {key:value,...} --type [json_person | json_user]", ops);
    }

    private void insert(String obj) {
        if (type.isEmpty()) {
            System.out.println("ERR: Entity type is missing!");
            return;
        }
        System.out.println("Sending request to: " + hostIndex);
        ClientResource client = new ClientResource(hosts[hostIndex]);
        StringRepresentation json = new StringRepresentation(obj);
        try {
            client.post(json).write(System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(String obj) {
        if (type.isEmpty()) {
            System.out.println("ERR: Entity type is missing!");
            return;
        }
        System.out.println("Sending request to: " + hostIndex);
        ClientResource client = new ClientResource(mountSingleResourceURL(obj));
        StringRepresentation json = new StringRepresentation(obj);
        try {
            client.put(json).write(System.out);
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search(String json) {
        if (type.isEmpty()) {
            System.out.println("ERR: Entity type is missing!");
            return;
        }
        System.out.println("Sending request to: " + hostIndex);
        ClientResource client = new ClientResource(mountQueryURL(json));
        try {
            client.get().write(System.out);
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void select(String json) {
        if (type.isEmpty()) {
            System.out.println("ERR: Entity type is missing!");
            return;
        }
        System.out.println("Sending request to: " + hosts[hostIndex+2]);
        ClientResource client = new ClientResource(mountSingleResourceURL(json));
        try {
            client.get().write(System.out);
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean setType(String newType) {
        System.out.println("tipo: " + newType);
        for (int i = 0; i < allowedTypes.length; i++) {
            if (newType.equals(allowedTypes[i])) {
                hostIndex = i;
                type = newType;
                return true;
            }
        }
        System.out.println("ERR: unexistent type");
        return false;
    }

    private void delete(String json) {
        if (type.isEmpty()) {
            System.out.println("ERR: Entity type is missing!");
            return;
        }
        System.out.println("Sending request to: " + hosts[hostIndex+2]);
        ClientResource client = new ClientResource(mountSingleResourceURL(json));
        try {
            client.delete().write(System.out);
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String mountQueryURL(String json) {
        JsonObject obj = new Gson().fromJson(json, JsonObject.class);
        StringBuilder sb = new StringBuilder(hosts[hostIndex]);
        if (obj != null) {
            if (type.equals("json_person")) {
                JsonElement elm;
                if ((elm = obj.get("code")) != null) {
                    sb.append("?code=").append(elm.getAsString());
                }
                if ((elm = obj.get("name")) != null) {
                    sb.append("?name=").append(elm.getAsString());
                }
                if ((elm = obj.get("address")) != null) {
                    sb.append("?address=").append(elm.getAsString());
                }
            } else if (type.equals("json_user")) {
                JsonElement elm;
                if ((elm = obj.get("person_code")) != null) {
                    sb.append("?person_code=").append(elm.getAsString());
                }
                if ((elm = obj.get("name")) != null) {
                    sb.append("?name=").append(elm.getAsString());
                }
                if ((elm = obj.get("password")) != null) {
                    sb.append("?password=").append(elm.getAsString());
                }
            }
        }
        return sb.toString();
    }
    
    private String mountSingleResourceURL(String json) {
        JsonObject obj = new Gson().fromJson(json, JsonObject.class);
        StringBuilder sb = new StringBuilder(hosts[hostIndex+2]);
        boolean hasCode = false;
        if (obj != null) {
            if (type.equals("json_person")) {
                JsonElement elm;
                if ((elm = obj.get("code")) != null) {
                    sb.append("/").append(elm.getAsString());
                    hasCode = true;
                }
            } else if (type.equals("json_user")) {
                JsonElement elm;
                if ((elm = obj.get("person_code")) != null) {
                    sb.append("/").append(elm.getAsString());
                    hasCode = true;
                }                
            }
        }
        if (!hasCode){
            System.out.println("ERR: Entitiy code is missing!");            
        }
        return sb.toString();
    }
    
//    private String mountUpdateURL(String json) {
//        JsonObject obj = new Gson().fromJson(json, JsonObject.class);
//        StringBuilder sb = new StringBuilder(hosts[hostIndex+2]);
//        if (obj != null) {
//            if (type.equals("json_person")) {
//                JsonElement elm;
//                if ((elm = obj.get("code")) != null) {
//                    sb.append("/").append(elm.getAsString());
//                }
//            } else if (type.equals("json_user")) {
//                JsonElement elm;
//                if ((elm = obj.get("person_code")) != null) {
//                    sb.append("/").append(elm.getAsString());
//                }                
//            }
//        }
//        return sb.toString();
//    }

}
