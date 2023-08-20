package org.example.java_core.reflection_demo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionDemo {
    private String dummy;

    public ReflectionDemo() {

    }

    public void setDummy(String value) {
        this.dummy = value;
    }

    public String getDummy() {
        return this.dummy;
    }

    @Override
    public String toString() {
        return "ReflectionDemo [dummy=" + dummy + "]";
    }

    public static void getAttributes() {
        ReflectionDemo reflectionDemo = new ReflectionDemo();
        reflectionDemo.setDummy("I'm dummy");

        for (Field field : reflectionDemo.getClass().getDeclaredFields()) {
            System.out.println("\n Field: " + field.getName() + "\n Type: " + field.getType());
        }

        try {
            Field field = reflectionDemo.getClass().getDeclaredField("dummy");
            field.setAccessible(true);
            field.set(reflectionDemo, "Bella");
            System.out.println(reflectionDemo);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void getMethods() {
        Class<ReflectionDemo> reflectionClass = ReflectionDemo.class;

        Method[] methods = reflectionClass.getMethods();
        for (Method method : methods) {
            System.out.println();
            System.out.println("Method: " + method.getName());
            System.out.println("Parameters: " + Arrays.toString(method.getParameters()));
        }

        try {
            Method methodSetName = reflectionClass.getMethod("setDummy", String.class);

            ReflectionDemo fDemo = new ReflectionDemo();

            methodSetName.invoke(fDemo, "MTay MXay");
            System.out.println(fDemo);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getMethods();
    }
}
