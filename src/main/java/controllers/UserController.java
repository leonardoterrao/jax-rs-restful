package controllers;

import com.google.gson.Gson;
import models.User;
import service.UserService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Path("/all")
    @GET
    @Produces("application/json")
    public JsonArray getAll() {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        userService.getAll()
                .stream()
                .forEach(u -> builder.add(Json.createObjectBuilder()
                        .add("id", u.getId())
                        .add("nome", u.getNome())));
        return builder.build();//http://localhost:8080/jax-rs-restful/api/users/all
    }

    @Path("/post")
    @POST
    @Consumes("application/json")
    public Response createUserInJson(String userJson) {
        Gson gson = new Gson();
        final User user = gson.fromJson(userJson, User.class);
        final List<User> all = userService.getAll();
        all.add(user);
        userService.save(all);
        return Response
                .status(201)
                .entity("The user '" + user.getNome() + "' was saved.")
                .build();
    }

}
