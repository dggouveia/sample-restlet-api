package br.edu.ifpb.pos.restlet.app.server;

import br.edu.ifpb.pos.restlet.app.server.resources.PersonResource;
import br.edu.ifpb.pos.restlet.app.server.resources.PersonsResource;
import br.edu.ifpb.pos.restlet.app.server.resources.UserResource;
import br.edu.ifpb.pos.restlet.app.server.resources.UsersResource;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class Loader {

    public static void main(String[] args) throws Exception {
        startServer();
    }
    
    private static void startServer () throws Exception{
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8090);
        Router router = new Router();
        router.attach("/persons", PersonsResource.class);        
        router.attach("/person/{code}", PersonResource.class);
        router.attach("/users", UsersResource.class);
        router.attach("/user/{code}", UserResource.class);
        Application app1 = new Application();
        app1.setInboundRoot(router);
        component.getDefaultHost().attach("/app", app1);
        component.start();
    }
}
