package br.edu.ifpb.pos.restlet.app;

import br.edu.ifpb.pos.restlet.app.client.ConcoleClient;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
public class Loader {

    public static void main(String[] args) throws Exception {
        new ConcoleClient().execute(args);
    }
    
}
