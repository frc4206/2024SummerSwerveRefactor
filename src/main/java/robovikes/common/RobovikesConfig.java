package robovikes.common;

import static robovikes.resources.Static.CONFIG_DIR;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public abstract class RobovikesConfig {

    protected void load(RobovikesConfig c, String filename){
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(CONFIG_DIR + filename));
            Field[] fields = c.getClass().getDeclaredFields();
            for(Field f : fields) {
                f.setAccessible(true);
                switch (f.getType().toString()){
                    case "null":
                        break;
                    case "boolean":
                        f.setBoolean(c, Boolean.parseBoolean(p.getProperty(f.getName())));
                        break;
                    case "byte":
                        f.setByte(c, Byte.parseByte(p.getProperty(f.getName())));
                        break;
                    // case "char":
                    //     f.setChar(c, p.getProperty(f.getName()).charAt(0));
                    //     break;
                    case "short":
                        f.setShort(c, Short.parseShort(p.getProperty(f.getName())));
                        break;
                    case "int":
                        f.setInt(c, Integer.parseInt(p.getProperty(f.getName())));
                        break;
                    case "long":
                        f.setLong(c, Long.parseLong(p.getProperty(f.getName())));
                        break;
                    case "float":
                        f.setFloat(c, Float.parseFloat(p.getProperty(f.getName())));
                        break;
                    case "double":
                        f.setDouble(c, Double.parseDouble(p.getProperty(f.getName())));
                        break;
                    default:
                        f.set(c, p.getProperty(f.getName()));
                        break;
                }
            }
        } catch (Exception e){
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
