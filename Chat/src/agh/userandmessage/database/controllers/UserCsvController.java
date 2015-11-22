package agh.userandmessage.database.controllers;

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
public final class UserCsvController
{

   private final static String USERFILE = "Chat\\src\\agh\\userandmessage\\database\\user.csv.txt";
   private final static char SEPARATOR = ',';

   public static User readUser(String login){


      try {
         CSVReader reader = new CSVReader(new FileReader(USERFILE),SEPARATOR,'"',1);

         String[] line;
         while((line = reader.readNext()) != null){
            if(line[0].equals(login)) {
               User user = new User(line[0], line[1], Boolean.valueOf(line[2]), line[3], line[4]);
               ContactList list = ContactListCsvController.findforUser(user.getLogin());
               user.setContactList(list);
               return user;
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      return null;
   }

   public static void saveUser(User user){

      if(readUser(user.getLogin()) == null) {
         try {
            CSVWriter writer = new CSVWriter(new FileWriter(USERFILE, true), SEPARATOR);
            String[] line = (user.getLogin() + SEPARATOR + user.getPassword() + SEPARATOR + user.isOnline() + SEPARATOR
                    + user.getName() + SEPARATOR + user.getLastName())
                    .split(String.valueOf(SEPARATOR));
            writer.writeNext(line);
            writer.close();

         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
