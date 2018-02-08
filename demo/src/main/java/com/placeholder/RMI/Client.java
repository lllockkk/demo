package com.placeholder.RMI;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

/**
 * Created by L on 2017/8/21.
 */
public class Client {
    public static void main(String[] args) {
        String url = "rmi://localhost:1099/";
        try {
            Context namingContext = new InitialContext();
            HelloService service1 = (HelloService) namingContext.lookup(url + "HelloService");
            String data = "This is RMI Client.";
            System.out.println(service1.service(data));
        } catch (NamingException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
