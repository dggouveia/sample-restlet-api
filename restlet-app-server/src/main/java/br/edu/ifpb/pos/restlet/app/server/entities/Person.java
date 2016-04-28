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
@Table(name = "tb_pessoa")
public class Person {

    @Id
    @Column(length = 10)
    private String code;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 254)
    private String address;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
