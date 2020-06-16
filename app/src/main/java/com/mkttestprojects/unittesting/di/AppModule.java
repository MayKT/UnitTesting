package com.mkttestprojects.unittesting.di;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.mkttestprojects.unittesting.models.Note;
import com.mkttestprojects.unittesting.persistance.NoteDao;
import com.mkttestprojects.unittesting.persistance.NoteDatabase;
import com.mkttestprojects.unittesting.repository.NoteRepository;
import com.mkttestprojects.unittesting.viewmodels.ViewModelProviderFactory;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static com.mkttestprojects.unittesting.persistance.NoteDatabase.DATABASE_NAME;

@Module
public  class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }

    @Singleton
    @Provides
    static NoteRepository provideNoteRepoitory(NoteDao noteDao){
        return new NoteRepository(noteDao);
    }

}
