package agh.model.simple;

import java.util.ArrayList;

/**
 * Created by Kurtz on 11.01.2016.
 */
public class SimplifiedUserList extends ArrayList<SimplifiedUser> {
    @Override
    public int hashCode() {
        int hash = 0;
        for (SimplifiedUser user : this) {
            hash += user.hashCode();
        }
        return hash;
    }
}
