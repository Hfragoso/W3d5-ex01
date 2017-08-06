package com.example.heber.w3d5_ex01;

import android.provider.BaseColumns;

/**
 * Created by heber on 8/5/2017.
 */

public final class FeedReaderContract {
    private FeedReaderContract() {

    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_GENDER = "userGender";
        public static final String COLUMN_NAME = "userName";
        public static final String COLUMN_ADDRESS = "userAddress";
        public static final String COLUMN_EMAIL = "userEmail";
        public static final String COLUMN_DOB = "userDOB";
        public static final String COLUMN_PHONE = "userPhone";
        public static final String COLUMN_CELL = "userCell";
        public static final String COLUMN_PICTURE_PATH = "userPicturePath";
        public static final String COLUMN_NATIONALITY = "userNationality";

    }
}
