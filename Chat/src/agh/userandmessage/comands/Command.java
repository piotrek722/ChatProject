package agh.userandmessage.comands;

import java.io.Serializable;

/**
 * Created by Peter on 2015-11-22.
 * Project name : ChatProject
 */
public interface Command extends Serializable {

   void execute();

}
