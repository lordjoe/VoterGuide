package com.lordjoe.voter.votesmart;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.lordjoe.voter.PersistentVoterItem;
import com.lordjoe.voter.State;

import java.util.List;
import java.util.Map;

/**
 * com.lordjoe.voter.votesmart.GoogleDatabase
 * User: Steve
 * Date: 7/22/2016
 */
public class GoogleDatabase {

    public static UserService getUserService() {
       return UserServiceFactory.getUserService();
        }
    public static DatastoreService getDatastoreService() {
        return DatastoreServiceFactory.getDatastoreService();
      }



    protected static String getTypeName(Class c) {
        return c.getSimpleName();
    }

    public static Key createKey(String id, Class c)  {
        return KeyFactory.createKey(getTypeName(c),id);
    }
    public static Key createKey(String id,Class c,PersistentVoterItem parent)  {
        return KeyFactory.createKey(parent.getKey(),getTypeName(c),id);
    }

    public static User getUser() {
       return getUserService().getCurrentUser();

    }

    private static void populateStates(DatastoreService ds) {
        try {
                for (State s :  State.values()) {
                String name = s.toString();
                Key key = KeyFactory.createKey("State", name);
                Entity en = new Entity(key);
                en.setProperty("name",name);
                ds.put(en);

            }
            Key  key = KeyFactory.createKey("State", "California");
            Entity entity = ds.get(key);
            Map<String, Object> properties = entity.getProperties();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    public static  void guaranteeStates(DatastoreService ds) {
           Query q = new Query("State");
        PreparedQuery pq= ds.prepare(q);
        int count = pq.countEntities( );
        if(count == 0)  {
            populateStates(ds);
            List<Entity> list=pq.asList(FetchOptions.Builder.withLimit(1000000));
            for (Entity entity : list) {
                String name = (String)entity.getProperty("name");
                System.out.println(name);
            }
        }



    }


}
