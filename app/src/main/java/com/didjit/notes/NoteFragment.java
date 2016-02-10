package com.didjit.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;


/**
 * Created by didjit on 06.01.16.
 */
public class NoteFragment extends Fragment {
    private static final String ARG_NOTE_ID = "note_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Note mNote;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mDoneCheckBox;

    public static NoteFragment newInstance(UUID noteId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID, noteId);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID noteId =  (UUID) getArguments().getSerializable(ARG_NOTE_ID);
        mNote = NoteLab.get(getActivity()).getNote(noteId);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPause() {
        super.onPause();
        NoteLab.get(getActivity()).updateNote(mNote);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        mTitleField = (EditText) v.findViewById(R.id.note_title);
        mTitleField.setText(mNote.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mNote.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDateButton = (Button) v.findViewById(R.id.note_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mNote.getDate());
                dialog.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        mDoneCheckBox = (CheckBox) v.findViewById(R.id.note_done);
        mDoneCheckBox.setChecked(mNote.isDone());
        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNote.setDone(isChecked);
            }
        });

        return v;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note, menu);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_note:

                UUID noteId=mNote.getId();
                NoteLab.get(getActivity()).deleteNote(noteId);

                getActivity().finish();
               Intent intent = NotePagerActivity.newIntent(getActivity(), mNote.getId());
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mNote.setDate(date);
            updateDate();
        }
    }
    private void updateDate() {
        String dateFormat="EEE, dd MMMM, yyyy";
        String dateString= DateFormat.format(dateFormat,mNote.getDate()).toString();
        mDateButton.setText(dateString);
    }
}
