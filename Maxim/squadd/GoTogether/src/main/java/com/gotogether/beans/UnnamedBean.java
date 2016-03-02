package com.gotogether.beans;

import com.gotogether.entities.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Максим on 01.03.2016.
 */
@ManagedBean(name = "UnnamedBean")
@RequestScoped
public class UnnamedBean implements Serializable{
    public static EntityManager em = Persistence.createEntityManagerFactory("SQUADD").createEntityManager();


    public static List<Contacts> getAllContacts() {
        return  em.createQuery("SELECT c FROM Contacts c").getResultList();
    }



}
