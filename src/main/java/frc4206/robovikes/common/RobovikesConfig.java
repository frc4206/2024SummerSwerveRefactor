package frc4206.robovikes.common;

import static frc4206.robovikes.resources.Static.CONFIG_DIR;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public abstract class RobovikesConfig {

    protected void load(RobovikesConfig c, String filename) {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(CONFIG_DIR + filename));
            Field[] fields = c.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                String s = p.getProperty(f.getName());
                switch (f.getType().toString()) {
                    case "boolean" -> f.setBoolean(c, Boolean.parseBoolean(s));
                    case "byte" -> f.setByte(c, Byte.parseByte(s));
                    case "char" -> f.setChar(c, s.charAt(0));
                    case "short" -> f.setShort(c, Short.parseShort(s));
                    case "int" -> f.setInt(c, Integer.parseInt(s));
                    case "long" -> f.setLong(c, Long.parseLong(s));
                    case "float" -> f.setFloat(c, Float.parseFloat(s));
                    case "double" -> f.setDouble(c, Double.parseDouble(s));
                    default -> f.set(c, s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void print(RobovikesConfig c) {
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
