package agh.userandmessage.database.controllers;


import agh.userandmessage.model.Contact;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Peter on 2015-11-22.
 * Project name : ChatProject
 */
public final class ContactCsvController {


    private final static String CONTACTFILE = "Chat\\src\\agh\\userandmessage\\database\\contact.csv.txt";
    private final static char SEPARATOR = ',';

    public static Contact readContact(String login){

        try {
            CSVReader reader = new CSVReader(new FileReader(CONTACTFILE),SEPARATOR,'"',1);

            String[] line;
            while(((line = reader.readNext()) != null)){
                if(line[0].equals(login)) {

                    return new Contact(line[0],line[1],line[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveContact(Contact contact){

        if(UserCsvController.readUser(contact.getLogin()) == null){
            return;
        }

        if(readContact(contact.getLogin()) == null) {
            try {
                CSVWriter writer = new CSVWriter(new FileWriter(CONTACTFILE, true), SEPARATOR);
                String[] line = (contact.getLogin() + SEPARATOR + contact.getName() +  SEPARATOR + contact.getLastName())
                                .split(String.valueOf(SEPARATOR));

                writer.writeNext(line);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
