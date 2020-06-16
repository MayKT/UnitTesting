package com.mkttestprojects.unittesting.di;


import com.mkttestprojects.unittesting.ui.note.NoteActivity;
import com.mkttestprojects.unittesting.ui.notelist.NoteListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NoteListActivity contributeNoteListActivity();

    @ContributesAndroidInjector
    abstract NoteActivity contributeNoteActivity();
}
