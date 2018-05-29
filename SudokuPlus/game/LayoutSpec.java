package game;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class LayoutSpec {
    private static final HashSet<String> ALL_SPECS = new HashSet(Arrays.asList("x", "y", "fill", "height", "ht", "width", "wid", "anchor", "weightx", "weighty", "ileft", "iright", "itop", "ibottom"));
    private static final HashMap<Object, Integer> INT_NAMES = new HashMap();
    private static final Object[][] INT_NAMES_INIT = new Object[][]{{"center", Integer.valueOf(10)}, {"north", Integer.valueOf(11)}, {"south", Integer.valueOf(15)}, {"east", Integer.valueOf(13)}, {"west", Integer.valueOf(17)}, {"southwest", Integer.valueOf(16)}, {"southeast", Integer.valueOf(14)}, {"northwest", Integer.valueOf(18)}, {"northeast", Integer.valueOf(12)}, {"remainder", Integer.valueOf(0)}, {"rest", Integer.valueOf(0)}, {"horizontal", Integer.valueOf(2)}, {"horiz", Integer.valueOf(2)}, {"vertical", Integer.valueOf(3)}, {"vert", Integer.valueOf(3)}, {"both", Integer.valueOf(1)}};
    private final GridBagConstraints _params = new GridBagConstraints();

    public LayoutSpec(Object... specs) {
        this._params.weightx = 1.0D;
        this._params.weighty = 1.0D;
        this._params.insets = new Insets(0, 0, 0, 0);
        this.add(specs);
    }

    public void add(Object... specs) {
        if (specs.length % 2 == 1) {
            throw new IllegalArgumentException("Missing last value");
        } else {
            int i;
            for(i = 0; i < specs.length; i += 2) {
                if (!(specs[i] instanceof String) || !ALL_SPECS.contains(specs[i])) {
                    throw new IllegalArgumentException("Illegal LayoutSpec key: " + specs[i]);
                }

                if (!(specs[i + 1] instanceof Integer) && !(specs[i + 1] instanceof Double) && !(specs[i + 1] instanceof String)) {
                    throw new IllegalArgumentException("Illegal value for" + specs[i] + " key");
                }
            }

            for(i = 0; i < specs.length; i += 2) {
                Object key = specs[i];
                Object val = specs[i + 1];
                this.addKey(key, val);
            }

        }
    }

    public GridBagConstraints params() {
        return this._params;
    }

    private void addKey(Object key, Object val) {
        String var3 = key.toString();
        byte var4 = -1;
        switch(var3.hashCode()) {
            case 120:
                if (var3.equals("x")) {
                    var4 = 0;
                }
                break;
            case 121:
                if (var3.equals("y")) {
                    var4 = 1;
                }
                break;
            case 3143043:
                if (var3.equals("fill")) {
                    var4 = 9;
                }
                break;
            case 113126854:
                if (var3.equals("width")) {
                    var4 = 2;
                }
                break;
            case 1230441728:
                if (var3.equals("weightx")) {
                    var4 = 10;
                }
                break;
            case 1230441729:
                if (var3.equals("weighty")) {
                    var4 = 11;
                }
                break;
        }

        switch(var4) {
            case 0:
                this._params.gridx = this.toInt(val);
                break;
            case 1:
                this._params.gridy = this.toInt(val);
                break;
            case 2:
                this._params.gridwidth = this.toInt(val);
                break;
            case 3:
                this._params.gridheight = this.toInt(val);
                break;
            case 4:
                this._params.anchor = this.toInt(val);
                break;
            case 5:
                this._params.insets.left = this.toInt(val);
                break;
            case 6:
                this._params.insets.right = this.toInt(val);
                break;
            case 7:
                this._params.insets.top = this.toInt(val);
                break;
            case 8:
                this._params.insets.bottom = this.toInt(val);
                break;
            case 9:
                this._params.fill = this.toInt(val);
                break;
            case 10:
                this._params.weightx = this.toDouble(val);
                break;
            case 11:
                this._params.weighty = this.toDouble(val);
        }

    }

    private int toInt(Object x) {
        if (x instanceof Integer) {
            return ((Integer)x).intValue();
        } else if (x instanceof Double) {
            return (int)((Double)x).doubleValue();
        } else if (!(x instanceof String)) {
            return -1;
        } else {
            Object o = ((String)x).toLowerCase();
            return INT_NAMES.containsKey(o) ? ((Integer)INT_NAMES.get(o)).intValue() : -1;
        }
    }

    private double toDouble(Object x) {
        return x instanceof Double ? ((Double)x).doubleValue() : (double)this.toInt(x);
    }

    static {
        Object[][] var0 = INT_NAMES_INIT;
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            Object[] pair = var0[var2];
            INT_NAMES.put(pair[0], (Integer)pair[1]);
        }

    }
}