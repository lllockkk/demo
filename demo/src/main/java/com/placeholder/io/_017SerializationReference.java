package com.placeholder.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 2017/8/20.
 *
 * 比较不提供方式的序列化的引用是否相同
 */
public class _017SerializationReference {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        House house = new House();
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("dog", house));
        animals.add(new Animal("hamster", house));
        animals.add(new Animal("cat", house));
        System.out.println("animals: " + animals);

        // write
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
        oos1.writeObject(animals);
        oos1.writeObject(animals);

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(baos2);
        oos2.writeObject(animals);

        // read
        ObjectInputStream ois1 = new ObjectInputStream(new ByteArrayInputStream(baos1.toByteArray()));
        List animals1 = (List) ois1.readObject();
        List animals2 = (List) ois1.readObject();

        ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(baos2.toByteArray()));
        List animals3 = (List) ois2.readObject();

        System.out.println(animals1);
        System.out.println(animals2);
        System.out.println(animals3);
    }
}

class House implements Serializable {

}
class Animal implements Serializable {
    private String name;
    private House preferredHouse;
    Animal(String name, House h) {
        this.name = name;
        this.preferredHouse = h;
    }

    public String toString() {
        return name + "[" + super.toString() + "], " + preferredHouse + "\n";
    }
}