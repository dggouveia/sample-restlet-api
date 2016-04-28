package br.edu.ifpb.pos.restlet.app.server.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author douglasgabriel
 * @version 0.1
 */
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @Column(length = 10)
    private String person_code;
    @Column(length = 10, nullable = false)
    private String name;
    @Column(length = 10, nullable = false)
    private String password;

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        this.person_code = person_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
