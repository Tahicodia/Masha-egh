package ua.parcelplus.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Composite: вузол, що містить інші вузли. Склад дітей прихований від клієнтів поза пакетом. */
public abstract class Container implements LoadNode {
    private final String label;
    private final List<LoadNode> children = new ArrayList<>();

    protected Container(String label) {
        this.label = label;
    }

    public Container add(LoadNode node) {
        children.add(node);
        return this;
    }

    List<LoadNode> children() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String label() {
        return label;
    }

    abstract String kind();

    void describe(int depth, List<String> out) {
        String pad = "  ".repeat(depth);
        out.add(pad + kind() + " " + label);
        for (LoadNode child : children) {
            if (child instanceof Container c) {
                c.describe(depth + 1, out);
            } else {
                out.add(pad + "  " + child);
            }
        }
    }
}
