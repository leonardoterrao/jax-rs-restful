package service;

import models.User;

import javax.ejb.Stateless;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserService {

    private static final String DB_FILE = "users.dat";

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        File file = new File(DB_FILE);
        if (!file.exists()) {
            users.add(User.builder().id(1L).nome("Leonardo").build());
            users.add(User.builder().id(2L).nome("João").build());
            users.add(User.builder().id(3L).nome("Maria").build());
            save(users);
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (List<User>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    public void save(List<User> users) {
        File file = new File(DB_FILE);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
