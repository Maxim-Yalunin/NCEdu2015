package com.gotogether;


import com.gotogether.beans.UnnamedBean;
import com.gotogether.entities.Contacts;

import java.util.List;

/**
 * Created by Максим on 01.03.2016.
 */
public class Main {


    public static void main(String[] args) {

        List<Contacts> contactsList = UnnamedBean.getAllContacts();
        for(Contacts c: contactsList) {
            System.out.println(c.toString());
        }
    }


}
