package com.didjit.notes;

import android.support.v4.app.Fragment;

/**
 * Created by didjit on 09.01.16.
 */
public class NoteListActivity extends SingleFragmentActivity {
    protected Fragment createFragment(){
        return  new NoteListFragment();
    }
}
