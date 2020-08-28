package org.jnbt;

public final class ByteArrayTag extends Tag {
    private final byte[] value;

    public ByteArrayTag(String name, byte[] value) {
        super(name);
        this.value = value;
    }

    public byte[] getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder hex = new StringBuilder();
        byte[] var5;
        int var4 = (var5 = this.value).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            String hexDigits = Integer.toHexString(b).toUpperCase();
            if (hexDigits.length() == 1) {
                hex.append("0");
            }

            hex.append(hexDigits).append(" ");
        }

        String name = this.getName();
        String append = "";
        if (name != null && !name.equals("")) {
            append = "(\"" + this.getName() + "\")";
        }

        return "TAG_Byte_Array" + append + ": " + hex.toString();
    }
}
