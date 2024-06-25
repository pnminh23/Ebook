package com.example.ebook_pnminh.Singleton;

public class UidManager {
    private static UidManager instance;
    private String uid;

    private UidManager() {
        // Private constructor to prevent instantiation outside of this class.
    }

    public static synchronized UidManager getInstance() {
        if (instance == null) {
            instance = new UidManager();
        }
        return instance;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
