package com.jogsoft.apps.assignment.auth;

import com.jogsoft.apps.assignment.auth.model.AuthData;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class AuthAppProcessorTest {

    private AuthAppProcessor authAppProcessor;

    @Before
    public void setUp(){
        authAppProcessor = new AuthAppProcessor();
    }


    @Test
    public void processFileShouldReturnCorrectCountOfData() throws Exception {
        /*
        roles=RO;RW;SU
        RO-Perm=Read:File
        RW-Perm=Read,Write,Delete:File
        Folder-Perm=Create,Delete:Folder
        perms=RO->RO-Perm;RW->RW-Perm;SU->RW-Perm,Folder-Perm
        users=Sam->SU
        */

        AuthData data = authAppProcessor.processFile("test-auth-data.properties");

        // data should contain 3 role permissions and one user role mapping
        Assert.assertNotNull(data);
        Assert.assertNotNull(data.getRolePermissionMappings());
        Assert.assertThat(data.getRolePermissionMappings().size(), CoreMatchers.is(3));
        Assert.assertThat(data.getUserRoleMappings().size(), CoreMatchers.is(1));
    }


    @Test(expected = FileNotFoundException.class)
    public void processFileShouldThrowExecptionWhenFileNotFound() throws Exception {
        authAppProcessor.processFile("fake-news-auth-data.properties");
    }
}