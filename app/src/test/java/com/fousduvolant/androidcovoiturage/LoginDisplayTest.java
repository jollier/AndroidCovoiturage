package com.fousduvolant.androidcovoiturage;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import model.User;

import com.fousduvolant.androidcovoiturage.LoginDisplayActivity.*;
import static org.junit.Assert.*;

/**
 * Created by jollier on 14/02/2016.
 */
public class LoginDisplayTest {

    @Test
    public void testTestLoginExist() throws Exception {
        assertEquals(4, 2 + 2);
        ConnexionFiles connect = new ConnexionFiles();
        User user = new User();
        user = null;

        try {
            user = connect.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertNotNull(user);
    }
}