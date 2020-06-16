package com.mkttestprojects.unittesting;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.mkttestprojects.unittesting.models.Note;
import com.mkttestprojects.unittesting.util.LiveDataTestUtil;
import com.mkttestprojects.unittesting.util.TestUtil;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NoteDaoTest extends NoteDatabaseTest {

    public static final String TEST_TITLE = "This is test title";
    public static final String TEST_CONTENT = "This is test content";
    public static final String TEST_TIMESTAMP = "06-2020";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    /*
    Insert / Read / Delete
     */
    @Test
    public void insertReadDelete() throws  Exception{
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Insert
        getNoteDao().insertNote(note).blockingGet();//wait until inserted

        //Read
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = listLiveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);
        assertEquals(note.getContent(),insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(),insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(),insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note,insertedNotes.get(0));

        //Delete
        getNoteDao().deleteNote(note).blockingGet();

        // confirm the database is empty
        insertedNotes = listLiveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0,insertedNotes.size());

    }

    /*
    Insert / Read / Update / Read / Delete
     */
    @Test
    public void insertUpdateReadDelete() throws  Exception{
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Insert
        getNoteDao().insertNote(note).blockingGet();//wait until inserted

        //Read
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = listLiveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);
        assertEquals(note.getContent(),insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(),insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(),insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note,insertedNotes.get(0));

        // Update
        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();

        // Read
        insertedNotes = listLiveDataTestUtil.getValue(getNoteDao().getNotes());

        assertEquals(note.getContent(),insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(),insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(),insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note,insertedNotes.get(0));

        //Delete
        getNoteDao().deleteNote(note).blockingGet();

        // confirm the database is empty
        insertedNotes = listLiveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0,insertedNotes.size());

    }

    /*
    Insert note with null title throws exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insertNullTitle_throwsSQLiteConstraintException()throws  Exception{

        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        // insert
        getNoteDao().insertNote(note).blockingGet();
    }

    /*
    Insert ,Update note with null title , throws exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insertUpdateNullTitle_throwsSQLiteConstraintException() throws Exception
    {
        Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        //insert
        getNoteDao().insertNote(note).blockingGet();

        // read
        LiveDataTestUtil<List<Note>> listLiveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = listLiveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(insertedNotes);

        //update
        note = new Note(insertedNotes.get(0));
        note.setTitle(null);
        getNoteDao().updateNote(note).blockingGet();

    }

}
