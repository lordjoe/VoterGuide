package com.lordjoe.voter.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.lordjoe.voter.votesmart.GoogleDatabase;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * com.lordjoe.voter.servlets.RacesServlet
 * User: Steve
 * Date: 7/23/2016
 */
public class RacesServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String state = req.getParameter("state");
        GoogleDatabase.guaranteeStates(datastore);
//        Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
//        String content = req.getParameter("content");
//        Date date = new Date();
//        Entity greeting = new Entity("Greeting", guestbookKey);
//        greeting.setProperty("user", user);
//        greeting.setProperty("date", date);
//        greeting.setProperty("content", content);
//
//         datastore.put(greeting);

        resp.sendRedirect("/StateRaces.jsp?state=" + state);
    }
}
