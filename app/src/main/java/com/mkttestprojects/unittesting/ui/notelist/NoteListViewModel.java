package com.mkttestprojects.unittesting.ui.notelist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.unittesting.models.Note;
import com.mkttestprojects.unittesting.repository.NoteRepository;
import com.mkttestprojects.unittesting.ui.Resource;

import java.util.List;

import javax.inject.Inject;

public class NoteListViewModel extends ViewModel {

    private NoteRepository noteRepository;

    private MediatorLiveData<List<Note>> notes=new MediatorLiveData<>();

    @Inject
    public NoteListViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public LiveData<Resource<Integer>> deleteNote(final Note note) throws Exception{
        return noteRepository.deleteNote(note);
    }

    public LiveData<List<Note>> observeNotes(){
        return notes;
    }

    public void getNotes(){
        final LiveData<List<Note>> source = noteRepository.getNotes();
        notes.addSource(source, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> noteslist) {
                if(noteslist != null){
                    notes.setValue(noteslist);
                }
                notes.removeSource(source);
            }
        });
    }
}
