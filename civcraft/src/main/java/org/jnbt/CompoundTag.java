package org.jnbt;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

public final class CompoundTag extends Tag {
    private final Map<String, Tag> value;

    public CompoundTag(String name, Map<String, Tag> value) {
        super(name);
        this.value = Collections.unmodifiableMap(value);
    }

    public Map<String, Tag> getValue() {
        return this.value;
    }

    public String toString() {
        String name = this.getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }

        StringBuilder bldr = new StringBuilder();
        bldr.append("TAG_Compound").append(append).append(": ").append(this.value.size()).append(" entries\r\n{\r\n");
        for (Entry<String, Tag> entry : this.value.entrySet()) {
            bldr.append("   ").append(entry.getValue().toString().replaceAll("\r\n", "\r\n   ")).append("\r\n");
        }

        bldr.append("}");
        return bldr.toString();
    }
}