package com.example.jobcollisions.database;


/**
 * Created by igor on 11.06.17.
 */

public class CrimeDBSchema {
    public static final class CrimeTable{

        public static final String NAME = "crimes";

        public static final class Columns{

            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
            public static final String PHONE_NUM = "phoneNumber";
        }
    }
}
