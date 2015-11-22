package agh.userandmessage.database.controllers;

import agh.userandmessage.model.Contact;
import agh.userandmessage.model.ContactList;
import agh.userandmessage.model.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Peter on 2015-11-22.
 * Project name : ChatProject
 */
public final class ContactListCsvController {

    private final static String CONTACTLISTFILE = "Chat\\src\\agh\\userandmessage\\database\\contactlist.csv.txt";
    private final static char SEPARATOR = ',';

    public static ContactList findforUser(String userlogin){

        ContactList list = new ContactList();
        try {
            CSVReader reader = new CSVReader(new FileReader(CONTACTLISTFILE),SEPARATOR,'"',1);

            String[] line;
            while((line = reader.readNext()) != null){
                if(line[0].equals(userlogin)) {
                    list.add(ContactCsvController.readContact(line[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void saveContactforUser(User user, Contact contact){

        if(UserCsvController.readUser(user.getLogin()) == null && ContactCsvController.readContact(contact.getLogin())==null){
            return;
        }

        ContactList list = findforUser(user.getLogin());

        if(!list.contains(contact)) {
            try {
                CSVWriter writer = new CSVWriter(new FileWriter(CONTACTLISTFILE, true), SEPARATOR);
                String[] line = (user.getLogin() + SEPARATOR + contact.getLogin())
                        .split(String.valueOf(SEPARATOR));
                writer.writeNext(line);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveContactList(User user){

        if(UserCsvController.readUser(user.getLogin()) == null){
            return;
        }

        for(Contact contact: user.getContactList()){
            saveContactforUser(user,contact);
        }

    }

}
