package frc4206.robovikes.common;

import static frc4206.robovikes.resources.Static.CONFIG_DIR;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import frc4206.robovikes.subsystems.GenericSubsystem;

public abstract class LoadableConfig {

    public interface LoadableConfigFactory{
        public LoadableConfig create();
    }

    public LoadableConfig(){};

    protected void load(LoadableConfig c, String filename) {

        Path source = Paths.get(CONFIG_DIR + filename);

        System.out.println(source);

        try {
            TomlParseResult result = Toml.parse(source);
            result.errors().forEach(error -> System.err.println(error.toString()));

            // result.getTable(null);

            // this.load(c, result);

            // TomlTable tt = result.getTable("slot1");
            // System.out.println(tt.getDouble("kp"));

            Field[] fields = c.getClass().getDeclaredFields();

            for (Field f : fields) {
                f.setAccessible(true);
                String type_name = f.getType().getName();
                String id = f.getName();

                System.out.println("Trying to parse \"" + type_name + "\", " + id);

                // result.getTable()

                // Object tps = result.get(id);

                switch (type_name) {
                    case "int" -> {
                        int i = result.getLong(id).intValue();
                        f.setInt(c, i);
                        System.out.println(f.getInt(c));
                    }
                    // case "boolean" -> {
                    // boolean b = result.getBoolean(id).booleanValue();
                    // f.setBoolean(c, b);
                    // System.out.println(f.getBoolean(c));
                    // }
                    // case "char" -> f.setChar(c, s.charAt(0));
                    // case "short" -> f.setShort(c, Short.parseShort(s));
                    // case "int" -> f.setInt(c, Integer.parseInt(s));
                    // case "long" -> f.setLong(c, Long.parseLong(s));
                    // case "float" -> f.setFloat(c, Float.parseFloat(s));
                    // case "double" -> f.setDouble(c, Double.parseDouble(s));
                    default -> {
                        Class<? extends LoadableConfig> lc = (Class<? extends LoadableConfig>) Class.forName(type_name);
                        Constructor<? extends LoadableConfig> csts = lc.getConstructor();
                        
                        System.out.println(csts);

                        TomlTable tt = result.getTable(id);

                        Object z = csts.newInstance();
                        this.loadLC((LoadableConfig) z, tt);

                        this.print((LoadableConfig) z);

                        // Constructor<?>[] constructors = clazz.getConstructors();
                        // for (Constructor<?> constructor : constructors) {
                        //     System.out.println("  constructor: " + constructor);
                        //     Class<?>[] parameterTypes = constructor.getParameterTypes();
                        //     for (Class<?> parameterType : parameterTypes) {
                        //         System.out.println("    parameterType: " + parameterType);
                        //     }
                        // }
                        // System.out.println(f.getType());
                        // Object obj = f.getType().getDeclaredConstructor().newInstance();
                        // Class<?> clazz = Class.forName(type_name);
                        // System.out.println(clazz.getName());
                        // LoadableConfig lc = (LoadableConfig) clazz.getDeclaredConstructor().newInstance();
                        // lc.create();
                        // Constructor<? extends LoadableConfig> ctor = (Constructor<? extends LoadableConfig>) clazz.getConstructor();
                        // Object object = ctor.newInstance(new Object());

                        // if(Class.forName(type_name).isAssignableFrom(c.getClass())){
                        //     System.out.println(type_name + " is a freakin LoadableConfig");
                        // }

                        // LoadableConfig cq = (LoadableConfig) Class.forName(type_name).cast(type_name.getClass());

                        // System.out.println("here");

                        // // Constructor<?> ctor = cq.getConstructor(Double.class);

                        // // Object object = ctor.newInstance();

                        // System.out.println(cq.getClass());

                        // TomlTable tt = result.getTable(id);
                        // this.loadLC((LoadableConfig) object, tt);
                        // System.out.println("Tried this");
                        // LoadableConfig lc;
                        // System.out.println("Broke");
                        // this.load()
                    }
                }
            }

            // Long canID = result.getLong("canID");

            // System.out.println(canID);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // private <T> load()

        // try {
        // Properties p = new Properties();
        // p.load(new FileInputStream(CONFIG_DIR + filename));
        // Field[] fields = c.getClass().getDeclaredFields();
        // for (Field f : fields) {
        // f.setAccessible(true);
        // String s = p.getProperty(f.getName());

        // System.out.println(s + " is the string " + f.getType().toString());

        // switch (f.getType().toString()) {
        // case "boolean" -> f.setBoolean(c, Boolean.parseBoolean(s));
        // case "byte" -> f.setByte(c, Byte.parseByte(s));
        // case "char" -> f.setChar(c, s.charAt(0));
        // case "short" -> f.setShort(c, Short.parseShort(s));
        // case "int" -> f.setInt(c, Integer.parseInt(s));
        // case "long" -> f.setLong(c, Long.parseLong(s));
        // case "float" -> f.setFloat(c, Float.parseFloat(s));
        // case "double" -> f.setDouble(c, Double.parseDouble(s));
        // default -> System.out.println(f.getType().toString() + " : " + s);
        // }
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // System.exit(-1);
        // }
    }

    private void loadLC(LoadableConfig lc, TomlTable tt) throws Exception {

        System.out.println("here I am etf " + lc.getClass().getName());

        Field[] fields = lc.getClass().getDeclaredFields();

        for (Field f : fields) {
            System.out.println("   field is '" + f.getType() + "' " + f.getName());
            f.setAccessible(true);
            String type_name = f.getType().getCanonicalName();
            String id = f.getName();

            System.out.println("Trying to parse \"" + type_name + "\", " + id);

            // Object tps = tt.get(id);

            switch (type_name) {
                case "int" -> {
                    int i = tt.getLong(id).intValue();
                    f.setInt(lc, i);
                    System.out.println(f.getInt(lc));
                }
                case "double" -> {
                    double d = tt.getDouble(id).doubleValue();
                    f.setDouble(lc, d);
                }
                // case "boolean" -> {
                // boolean b = tt.getBoolean(id).booleanValue();
                // f.setBoolean(lc, b);
                // System.out.println(f.getBoolean(lc));
                // }
                default -> {
                    // load()
                }
            }
        }
    }

    public static void print(LoadableConfig c) {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(c.getClass().getCanonicalName());
        result.append(" {");
        result.append(newLine);

        // determine fields declared in this class only (no fields of superclass)
        Field[] fields = c.getClass().getDeclaredFields();

        // print field names paired with their values
        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                // requires access to private field:
                result.append(field.get(c));
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");
        result.append(newLine);

        System.out.println(result.toString());
    }

}
