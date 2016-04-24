package com.yandex.mobile_school.artistviewer.sqlite;

import android.net.Uri;

import java.util.List;

/**
 * Created by Sovan on 23.04.2016.
 */
public class SQLiteUriMatcher {
    static final int NO_MATCH = -1;
    static final int MATCH_ALL = 1;
    static final int MATCH_ID = 2;
    static int match(Uri uri) {
        final List<String> pathSegments = uri.getPathSegments();
        switch(pathSegments.size()) {
            case 1:
                return MATCH_ALL;
            case 2:
                return MATCH_ID;
            default:
                return NO_MATCH;
        }
    }
}
