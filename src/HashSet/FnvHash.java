package HashSet;

import java.nio.charset.StandardCharsets;

class FnvHash {
    private static final int FNV_INIT = 0x811c9dc5;
    private static final int FNV_PRIME = 0x01000193;

    public static int hash(String input) {
        if (input == null) return 0;

        int hash = FNV_INIT;
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        for (byte b : bytes) {
            hash ^= (b & 0xff);
            hash *= FNV_PRIME;
        }
        return hash;
    }
}
