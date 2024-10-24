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

    private static final String class_name = "LoadableConfig";

    public class LoadableConfigException extends Exception {
        public LoadableConfigException(Class<?> c) {
            super(c.getCanonicalName() + " is not an instance of a " + class_name);
        }
    }

    public interface LoadableConfigFactory {
        public LoadableConfig create();
    }

    private boolean isLoadableConfig(Class<?> c) {
        return LoadableConfig.class.isAssignableFrom(c);
    }

    protected void load(LoadableConfig c, String filename) {
        try {
            Path source = Paths.get(CONFIG_DIR + filename);

            TomlParseResult result = Toml.parse(source);
            result.errors().forEach(error -> System.err.println(error.toString()));

            Field[] fields = c.getClass().getDeclaredFields();

            for (Field f : fields) {
                f.setAccessible(true);
                String type_name = f.getType().getName();
                String id = f.getName();

                /**
                 * The most common types are checked first, which are 
                 * probably double, String, and int for FRC
                 */
                switch (type_name) {
                    case "double" -> f.setDouble(c, result.getDouble(id));
                    case "java.lang.String" -> f.set(c, result.getString(id));
                    case "int" -> f.setInt(c, result.getLong(id).intValue());
                    case "boolean" -> f.setBoolean(c, result.getBoolean(id));
                    case "char" -> f.setChar(c, result.getString(id).charAt(0));
                    case "long" -> f.setLong(c, result.getLong(id));
                    case "float" -> f.setFloat(c, (float) result.getDouble(id).doubleValue());
                    case "short" -> f.setShort(c, (short) result.getLong(id).intValue());
                    case "byte" -> f.setByte(c, (byte) result.getLong(id).byteValue());
                    default -> {
                        Class<?> lc = Class.forName(type_name);
                        if (!isLoadableConfig(lc)) {
                            throw new LoadableConfigException(lc);
                        } else {
                            Constructor<? extends LoadableConfig> cnstrctr = (Constructor<? extends LoadableConfig>) lc.getConstructor();
                            LoadableConfig nlc = cnstrctr.newInstance();
                            this.loadLC(nlc, result.getTable(id));
                            LoadableConfig.print(nlc);
                            f.set(c, nlc);
                        }
                    }
                }
            }

        } catch (LoadableConfigException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void loadLC(LoadableConfig c, TomlTable tt) throws Exception {
        Field[] fields = c.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            String type_name = f.getType().getCanonicalName();
            String id = f.getName();
            switch (type_name) {
                    case "double" -> f.setDouble(c, tt.getDouble(id));
                    case "java.lang.String" -> f.set(c, tt.getString(id));
                    case "int" -> f.setInt(c, tt.getLong(id).intValue());
                    case "boolean" -> f.setBoolean(c, tt.getBoolean(id));
                    case "char" -> f.setChar(c, tt.getString(id).charAt(0));
                    case "long" -> f.setLong(c, tt.getLong(id));
                    case "float" -> f.setFloat(c, (float) tt.getDouble(id).doubleValue());
                    case "short" -> f.setShort(c, (short) tt.getLong(id).intValue());
                    case "byte" -> f.setByte(c, (byte) tt.getLong(id).byteValue());
                    default -> {
                        Class<?> lc = Class.forName(type_name);
                        if (!isLoadableConfig(lc)) {
                            throw new LoadableConfigException(lc);
                        } else {
                            Constructor<? extends LoadableConfig> cnstrctr = (Constructor<? extends LoadableConfig>) lc.getConstructor();
                            LoadableConfig nlc = cnstrctr.newInstance();
                            this.loadLC(nlc, tt.getTable(id));
                            LoadableConfig.print(nlc);
                            f.set(c, nlc);
                        }
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
