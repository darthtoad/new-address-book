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
            AddressBook newAddressBook = new AddressBook(first, last, newAddress, number);
            ArrayList addressBooks = AddressBook.getAll();
            model.put("addressBooks", addressBooks);
            return new ModelAndView(model, "book.hbs");
        }, new HandlebarsTemplateEngine());


        // get: show more address details
        get("/addressBook/:addressId", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToFind = Integer.parseInt(req.params("addressId"));
            AddressBook foundEntry = AddressBook.findById(idOfPostToFind); //use it to find post
            model.put("foundEntry", foundEntry); //add it to model for template to display
            return new ModelAndView(model, "details.hbs");
        }, new HandlebarsTemplateEngine());


        // get: show edit form
        get("/addressBook/:addressId/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToEdit = Integer.parseInt(request.params("addressId"));
            AddressBook editAddressBook = AddressBook.findById(idOfPostToEdit);
            model.put("editAddressBook", editAddressBook);
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
            AddressBook foundEntry = AddressBook.findById(idOfPostToEdit);
            foundEntry.edit(first, last, newAddress, number);
            model.put("foundEntry", foundEntry);
            return new ModelAndView(model, "details.hbs");
        }, new HandlebarsTemplateEngine());

        // post: delete one post
        get("/addressBook/:addressId/delete",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToDelete = Integer.parseInt(request.params("addressId"));
            AddressBook deleteAddressBook = AddressBook.findById(idOfPostToDelete);
            deleteAddressBook.deleteEntry();
            ArrayList addressBooks = AddressBook.getAll();
            model.put("addressBooks", addressBooks);
            return new ModelAndView(model, "book.hbs");
        }, new HandlebarsTemplateEngine());



        }




    }
}
