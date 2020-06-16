package com.mkttestprojects.unittesting;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.mkttestprojects.unittesting.persistance.NoteDao;
import com.mkttestprojects.unittesting.persistance.NoteDatabase;

import org.junit.After;
import org.junit.Before;

public class NoteDatabaseTest {

    // System under test
    private NoteDatabase noteDatabase;

    public NoteDao getNoteDao(){
        return noteDatabase.getNoteDao();
    }

    @Before
    public void init(){
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish(){
        noteDatabase.close();
    }
}
