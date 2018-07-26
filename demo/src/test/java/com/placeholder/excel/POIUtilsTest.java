package com.placeholder.excel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class POIUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void readAllToList() {
    }

    @Test
    public void readAllToList1() {
    }

    @Test
    public void readAllToMap() {
    }

    @Test
    public void readAllToObject() throws Exception {
        String[] titles = new String[] {"name", "age", "sex", "password"};
        POIUtils.Filterable filterable = null;
        List<User> users = POIUtils.readAllToObject("/home/l/Desktop/e.xls", User.class, filterable, titles);
        System.out.println(users.size());
        for (User user : users) {
            System.out.println(user);
        }
    }


    public static class User {
        private String name;
        private int age;
        private String sex;
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

}


