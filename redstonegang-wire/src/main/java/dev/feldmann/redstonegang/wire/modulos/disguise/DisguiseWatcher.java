package dev.feldmann.redstonegang.wire.modulos.disguise;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class DisguiseWatcher {

    HashMap<Integer, WatcherValue> values = new HashMap();

    public HashMap<Integer, WatcherValue> getValues() {
        return values;
    }

    public void add(int key, Object obj, Class classe) {
        WatcherValueType type = null;
        for (WatcherValueType typ : WatcherValueType.values()) {
            if (typ.type == classe) {
                type = typ;
                break;
            }
        }
        if (type == null) return;

        if (values.containsKey(key)) {
            WatcherValue value = values.get(key);
            if (value.getValue() != obj) {
                value.setValue(obj);
                update();
            }
        } else {
            values.put(key, new WatcherValue(key, type, obj));
            update();
        }
    }

    public void add(int key, Object obj) {
        this.add(key, obj, obj.getClass());
    }

    public void update() {

    }

    public enum WatcherValueType {
        BYTE(Byte.class),
        SHORT(Short.class),
        INTEGER(Integer.class),
        FLOAT(Float.class),
        STRING(String.class),
        ITEMSTACK(ItemStack.class),
        BLOCKPOS(Vector.class),
        ROTATIONS(Rotation.class);

        Class type;

        WatcherValueType(Class type) {
            this.type = type;

        }
    }

    public class WatcherValue {
        int key;
        WatcherValueType type;
        Object value;
        boolean update = true;

        public WatcherValue(int key, WatcherValueType type, Object value) {
            this.key = key;
            this.type = type;
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setUpdate(boolean update) {
            this.update = update;
        }

        public boolean isUpdate() {
            return update;
        }

        public void setValue(Object value) {
            this.value = value;
            update = true;
        }

        public WatcherValueType getType() {
            return type;
        }

        public int getKey() {
            return key;
        }
    }

    public static class Rotation {
        private float x, y, z;

        public Rotation(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }
    }

}
