package game;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.function.Consumer;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TopLevel extends Observable implements ActionListener {
    private final HashMap<String, TopLevel.ButtonHandler> buttonMap = new HashMap();
    private final HashMap<String, ButtonGroup> buttonGroups = new HashMap();
    private final HashMap<String, JLabel> labelMap = new HashMap();
    private static final HashMap<String, Integer> MESSAGE_TYPE_MAP = new HashMap();
    protected final JFrame frame;

    public void display(boolean visible) {
        if (visible) {
            this.frame.pack();
        }

        this.frame.setVisible(visible);
    }

    protected TopLevel(String title, boolean exitOnClose) {
        this.frame = new JFrame(title);
        this.frame.setUndecorated(true);
        this.frame.getRootPane().setWindowDecorationStyle(1);
        this.frame.setLayout(new GridBagLayout());
        if (exitOnClose) {
            this.frame.setDefaultCloseOperation(3);
        }

    }

    protected void addMenuButton(String label, Consumer<String> func) {
        String[] names = label.split("->");
        if (names.length <= 1) {
            throw new IllegalArgumentException("cannot parse label");
        } else {
            JMenu menu = this.getMenu(names, names.length - 2);
            JMenuItem item = new JMenuItem(names[names.length - 1]);
            item.setActionCommand(label);
            item.addActionListener(this);
            menu.add(item);
            this.buttonMap.put(label, new TopLevel.ButtonHandler(func, label, item));
        }
    }

    protected void add(Widget widget, LayoutSpec layout) {
        this.frame.add(widget.me, layout.params());
    }

    protected void addLabel(String text, String id, LayoutSpec layout) {
        if (this.labelMap.containsKey(id)) {
            throw new IllegalArgumentException("duplicate label id: " + id);
        } else {
            JLabel label = new JLabel(text);
            this.labelMap.put(id, label);
            this.frame.add(label, layout.params());
        }
    }

    protected void setLabel(String id, String text) {
        JLabel label = (JLabel)this.labelMap.get(id);
        if (label == null) {
            throw new IllegalArgumentException("unknown label id: " + id);
        } else {
            label.setText(text);
        }
    }

    public void setPreferredFocus(final Widget widget) {
        this.frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                widget.requestFocusInWindow();
            }
        });
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof AbstractButton) {
            String key = e.getActionCommand();
            TopLevel.ButtonHandler h = (TopLevel.ButtonHandler)this.buttonMap.get(key);
            if (h == null) {
                return;
            }

            h.doAction();
        }

    }

    private JMenu getMenu(String[] label, int last) {
        if (this.frame.getJMenuBar() == null) {
            this.frame.setJMenuBar(new JMenuBar());
        }

        JMenuBar bar = this.frame.getJMenuBar();
        JMenu menu = null;

        int k;
        for(k = 0; k < bar.getMenuCount(); ++k) {
            menu = bar.getMenu(k);
            if (menu.getText().equals(label[0])) {
                break;
            }

            menu = null;
        }

        if (menu == null) {
            menu = new JMenu(label[0]);
            bar.add(menu);
        }

        for(k = 1; k <= last; ++k) {
            JMenu menu0 = menu;
            menu = null;

            for(int i = 0; i < menu0.getItemCount(); ++i) {
                JMenuItem item = menu0.getItem(i);
                if (item != null) {
                    if (item.getText().equals(label[k])) {
                        if (!(item instanceof JMenu)) {
                            throw new IllegalStateException("inconsistent menu label");
                        }

                        menu = (JMenu)item;
                        break;
                    }

                    menu = null;
                }
            }

            if (menu == null) {
                menu = new JMenu(label[k]);
                menu0.add(menu);
            }
        }

        return menu;
    }


    static {
        MESSAGE_TYPE_MAP.put("information", Integer.valueOf(1));
        MESSAGE_TYPE_MAP.put("warning", Integer.valueOf(2));
        MESSAGE_TYPE_MAP.put("error", Integer.valueOf(0));
        MESSAGE_TYPE_MAP.put("plain", Integer.valueOf(-1));
        MESSAGE_TYPE_MAP.put("information", Integer.valueOf(1));
        MESSAGE_TYPE_MAP.put("question", Integer.valueOf(3));
    }

    private static class ButtonHandler {
        private Consumer<String> _func;
        private String _id;
        private AbstractButton _src;

        ButtonHandler(Consumer<String> func, String id, AbstractButton src) {
            this._src = src;
            this._id = id;
            this._func = func;
        }

        void doAction() {
            if (this._func != null) {
                this._func.accept(this._id);
            }

        }
    }
}