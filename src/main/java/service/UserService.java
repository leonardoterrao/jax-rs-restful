package service;

import models.User;

import javax.ejb.Stateless;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserService {

    private static final String DB_FILE = "/home/leonardo/users.dat";

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {

            File file = new File(DB_FILE);
            if (!file.exists()) {
                users.add(new User(1L, "Leonardo"));
                users.add(new User(2L, "João"));
                users.add(new User(3L, "Maria"));
                save(users);
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = (List<User>) ois.readObject();
                ois.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void save(List<User> users) {
        try {
            File file = new File(DB_FILE);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
