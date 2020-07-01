package pl.vertty.commands.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.Packet;


public class ReflectionUtil {
    private static final String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

    public static Class<?> getOBCClass(String name) {
        Class<?> c = null;
        try {
            c = Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Class<?> getCraftClass(String name) {
        String className = "net.minecraft.server." + getVersion() + name;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Class<?> getBukkitClass(String name) {
        String className = "org.bukkit.craftbukkit." + getVersion() + name;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Class<?> getNMSClass(String name) {
        Class<?> c = null;
        try {
            c = Class.forName("net.minecraft.server." + version + "." + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static void setValue(Field f, Object o, Object v) {
        try {
            f.setAccessible(true);
            f.set(o, v);
            f.setAccessible(false);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getHandle(Entity entity) {
        try {
            return getMethod(entity.getClass(), "getHandle").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getHandle(World world) {
        try {
            return getMethod(world.getClass(), "getHandle").invoke(world);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(final Class<?> clazz, final String field) {
        Field f = null;
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
        return getField(target, null, fieldType, index);
    }

    public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
        for (final Field field : target.getDeclaredFields()) {
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);

                return new FieldAccessor<T>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("Cannot access reflection.", e);
                        }
                    }

                    @Override
                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        if (target.getSuperclass() != null)
            return getField(target.getSuperclass(), name, fieldType, index);
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }

    public static Field getPrivateField(Class<?> cl, String field_name) {
        try {
            Field field = cl.getDeclaredField(field_name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    public static Object getValue(Object instance, String fieldName) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static Method getMethod(Class<?> cl, String method, Class<?>... args) {
        for (Method m : cl.getMethods())
            if (m.getName().equals(method) && classListEqual(args, m.getParameterTypes()))
                return m;
        return null;
    }

    public static Method getMethod(Class<?> cl, String method) {
        for (Method m : cl.getMethods())
            if (m.getName().equals(method))
                return m;
        return null;
    }

    private static boolean classListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++)
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        return equal;
    }

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = name.substring(name.lastIndexOf('.') + 1) + ".";
        return version;
    }

    public interface ConstructorInvoker {
        public Object invoke(Object... arguments);
    }

    public static String getReflectionString(){
        return "net.minecraft.server.g.";
    }

    public interface MethodInvoker {
        public Object invoke(Object target, Object... arguments);
    }

    public static void sendPacket(Player p, Packet<?> packet) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendPacket(Packet<?> packet) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendPacket(p, packet);
        }
    }

    public static void sendPacket(Packet<?> packet, Player[] except) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i > except.length; i++) {
                if (!except[i].equals(p)) {
                    sendPacket(p, packet);
                }
            }
        }
    }

    public interface FieldAccessor<T> {
        public T get(Object target);

        public void set(Object target, Object value);

        public boolean hasField(Object target);
    }



}