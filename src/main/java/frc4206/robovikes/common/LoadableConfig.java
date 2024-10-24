package frc4206.robovikes.common;

import static frc4206.robovikes.resources.Static.CONFIG_DIR;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

public abstract class LoadableConfig {

    /**
     * This custom exception is defined so that
     * we have a stack trace available when the
     * class of the field does not extend the
     * LoadableConfig class. This makes the recursion
     * of LoadableConfigs safe.
     */
    public class ExtensionException extends Exception {
        private static final String class_name = LoadableConfig.class.getName();

        public ExtensionException(Class<?> c) {
            super(c.getCanonicalName() + " is not an instance of " + class_name);
        }

        protected static boolean isLoadableConfig(Class<?> c) {
            return LoadableConfig.class.isAssignableFrom(c);
        }
    }

    /**
     * This function is how extensions of this class invoke the
     * initialization of all of the class fields. It is also
     * a 'helper' function to the actual recursive load function.
     */
    protected void load(LoadableConfig c, String filename) {
        try {
            Path source = Paths.get(CONFIG_DIR + filename);

            /* These steps are required and defined by the 'tomlj' library. */
            TomlParseResult result = Toml.parse(source);
            result.errors().forEach(error -> System.err.println(error.toString()));

            this.load(c, result);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private <T extends TomlTable> void load(LoadableConfig c, T tt) throws Exception {
        Field[] fields = c.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            String type_name = f.getType().getName();
            String id = f.getName();

            /**
             * This massive switch case is responsible for assigning
             * to the fields of a LoadableConfig the values
             * found inside the config file, that is, within
             * the *.toml. The default case is unique, as it
             * requires some unique casting and instance creation
             * for a class where the type is only determinable by
             * the name. The subclass MUST exetend this one.
             */
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
                    if (!ExtensionException.isLoadableConfig(lc)) {
                        throw new ExtensionException(lc);
                    } else {
                        /**
                         * This portion of the code invokes a new instance
                         * of the config class, calls this function recursively,
                         * and assigns the field to the newly mapped instance.
                         */
                        Constructor<? extends LoadableConfig> cnstrctr = (Constructor<? extends LoadableConfig>) lc
                                .getConstructor();
                        LoadableConfig nlc = cnstrctr.newInstance();
                        this.load(nlc, tt.getTable(id));
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
