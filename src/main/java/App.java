import dao.Sql2oAddressDao;
import dao.Sql2oEntryDao;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import models.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

/**
 * Created by Guest on 1/16/18.
 */

public class App {
    public static void main(String[] args){
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oEntryDao entryDao = new Sql2oEntryDao(sql2o);
        Sql2oAddressDao addressDao = new Sql2oAddressDao(sql2o);

        //get: show index
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
//            ArrayList addressBooks = AddressBook.getAll();
//            model.put("addressBook", addressBooks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

    //get: show name of inputted person
    get("/addressBook/display", (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        List addressBooks = entryDao.getAll();
        model.put("addressBooks", addressBooks);
        return new ModelAndView(model, "book.hbs");
    }, new HandlebarsTemplateEngine());

    //get: show new post form
    post("/addressBook/new", (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        String street = req.queryParams("street");
        String city = req.queryParams("city");
        String state = req.queryParams("state");
        String country = req.queryParams("country");
        String zip = req.queryParams("zip");
        Address newAddress = new Address(street, city, state, country, zip);
        String first = req.queryParams("first");
        String last = req.queryParams("last");
        String number = req.queryParams("phone");
        Entry newAddressBook = new Entry(first, last, addressDao.getAll().size(), number);
        List entries = entryDao.getAll();
        model.put("entries", entries);
        return new ModelAndView(model, "book.hbs");
    }, new HandlebarsTemplateEngine());


    // get: show more address details
    get("/addressBook/:addressId", (req, res) -> {
        Map<String, Object> model = new HashMap<>();
        int idOfPostToFind = Integer.parseInt(req.params("addressId"));
        Entry foundEntry = entryDao.findById(idOfPostToFind); //use it to find post
        model.put("foundEntry", foundEntry); //add it to model for template to display
        return new ModelAndView(model, "details.hbs");
    }, new HandlebarsTemplateEngine());


    // get: show edit form
    get("/addressBook/:addressId/edit", (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        int idOfPostToEdit = Integer.parseInt(request.params("addressId"));
        Entry editEntry = entryDao.findById(idOfPostToEdit);
        model.put("editEntry", editEntry);
        return new ModelAndView(model, "edit.hbs");
    }, new HandlebarsTemplateEngine());


     //post: update an address
    post("/addressBook/:addressId/edit", (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        String street = request.queryParams("street");
        String city = request.queryParams("city");
        String state = request.queryParams("state");
        String country = request.queryParams("country");
        String zip = request.queryParams("zip");
        Address newAddress = new Address(street, city, state, country, zip);
        String first = request.queryParams("first");
        String last = request.queryParams("last");
        String number = request.queryParams("phone");
        int idOfPostToEdit = Integer.parseInt(request.params("addressId"));
        Entry foundEntry = entryDao.findById(idOfPostToEdit);
        foundEntry.editEntry(first, last, addressDao.getAll().size(), number);
        model.put("foundEntry", foundEntry);
        return new ModelAndView(model, "details.hbs");
    }, new HandlebarsTemplateEngine());

    // post: delete one post
    get("/addressBook/:addressId/delete",(request, response) -> {
        Map<String, Object> model = new HashMap<>();
        int idOfPostToDelete = Integer.parseInt(request.params("addressId"));
        Entry deleteEntry = entryDao.findById(idOfPostToDelete);
        entryDao.deleteById(deleteEntry.getId());
        List entries = entryDao.getAll();
        model.put("entries", entries);
        return new ModelAndView(model, "book.hbs");
    }, new HandlebarsTemplateEngine());



    }




}
