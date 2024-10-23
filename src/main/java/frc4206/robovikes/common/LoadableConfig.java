package frc4206.robovikes.common;

import static frc4206.robovikes.resources.Static.CONFIG_DIR;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

public abstract class LoadableConfig {

    protected void load(LoadableConfig c, String filename) {

        Path source = Paths.get(CONFIG_DIR + filename);

        System.out.println(source);

		try {
			TomlParseResult result = Toml.parse(source);

            // Object canID = result.get("canID");

            result.errors().forEach(error -> System.err.println(error.toString()));

            Field[] fields = c.getClass().getDeclaredFields();

            for (Field f : fields) {
                f.setAccessible(true);
                String type_name = f.getType().getSimpleName();
                String id = f.getName();

                System.out.println("Trying to parse " + type_name + " " + id);

                Object tps = result.get(id);

                switch(type_name){
                    case "int" -> {
                        int i = result.getLong(id).intValue();
                        f.setInt(c, i);
                        System.out.println(f.getInt(c));
                    }
                    case "boolean" -> {
                        boolean b = result.getBoolean(id).booleanValue();
                        f.setBoolean(c, b);
                        System.out.println(f.getBoolean(c));
                    }
                    // case "char" -> f.setChar(c, s.charAt(0));
                    // case "short" -> f.setShort(c, Short.parseShort(s));
                    // case "int" -> f.setInt(c, Integer.parseInt(s));
                    // case "long" -> f.setLong(c, Long.parseLong(s));
                    // case "float" -> f.setFloat(c, Float.parseFloat(s));
                    // case "double" -> f.setDouble(c, Double.parseDouble(s));
                    case "default" -> System.out.println("I dunno yet");
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
