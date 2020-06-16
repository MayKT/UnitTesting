package com.mkttestprojects.unittesting.ui.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mkttestprojects.unittesting.models.Note;
import com.mkttestprojects.unittesting.repository.NoteRepository;
import com.mkttestprojects.unittesting.ui.Resource;
import com.mkttestprojects.unittesting.util.DateUtil;

import org.reactivestreams.Subscription;

import java.security.spec.ECField;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

import static com.mkttestprojects.unittesting.repository.NoteRepository.NOTE_TITLE_NULL;

public class NoteViewModel extends ViewModel
{
    private static final String TAG = "NoteViewModel";

    public enum ViewState {VIEW,EDIT}

    private NoteRepository noteRepository;

    private MutableLiveData<Note> note = new MutableLiveData<>();
    private MutableLiveData<ViewState> viewState = new MutableLiveData<>();
    public boolean isNewNote;
    public Subscription insertSubscription;
    public Subscription updateSubscription;
    public static final String NO_CONTENT_ERROR = "Can't save note with no content";

    @Inject
    public NoteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    public LiveData<Note> observeNote(){
        return note;
    }
    public LiveData<ViewState> observeViewState(){
        return viewState;
    }
    public void setViewState(ViewState viewState){
        this.viewState.setValue(viewState);
    }
    public void setIsNewNote(boolean isNewNote){
        this.isNewNote = isNewNote;
    }
    public LiveData<Resource<Integer>> saveNote() throws Exception{

        if(!shouldAllowSave()){
            throw new Exception(NO_CONTENT_ERROR);
        }
        cancelPendingTransaction();
        return new NoteInsertUpdateHelper<Integer>() {
            @Override
            public void setNoteId(int noteId) {
                isNewNote = false;
                Note currentNote = note.getValue();
                currentNote.setId(noteId);
                note.setValue(currentNote);
            }

            @Override
            public LiveData<Resource<Integer>> getAction() throws Exception {
                if(isNewNote){
                    return insertNote();
                }
                else {
                    return updateNote();
                }
            }

            @Override
            public String defineAction() {
                if(isNewNote){
                    return ACTION_INSERT;
                }
                else {
                    return ACTION_UPDATE;
                }
            }

            @Override
            public void onTransactionComplete() {
                updateSubscription = null;
                insertSubscription = null;
            }
        }.getAsLiveData();
    }
    private void cancelUpdateTransaction(){
        updateSubscription.cancel();
        updateSubscription = null;

    }
    private void cancelInsertTransaction(){
        insertSubscription.cancel();
        insertSubscription = null;
    }
    private void cancelPendingTransaction(){
        if(insertSubscription != null){
            cancelInsertTransaction();
        }
        if(updateSubscription != null){
            cancelUpdateTransaction();
        }
    }
    private boolean shouldAllowSave() throws Exception{
        try {
            return removeWhiteSpace(note.getValue().getContent()).length()>0;
        }
        catch (NullPointerException e){
            throw new Exception(NO_CONTENT_ERROR);
        }
    }
    public void updateNote(String title, String content) throws Exception{
        if(title == null || title.equals("")){
            throw new NullPointerException("Title can't be null");
        }
        String temp = removeWhiteSpace(content);
        if(temp.length() > 0){
            Note updatedNote = new Note(note.getValue());
            updatedNote.setTitle(title);
            updatedNote.setContent(content);
            updatedNote.setTimestamp(DateUtil.getCurrentTimeStamp());

            note.setValue(updatedNote);
        }
    }
    private String removeWhiteSpace(String string){
        string = string.replace("\n", "");
        string = string.replace(" ", "");
        return string;
    }

    public void setNote(Note note) throws Exception{
        if(note.getTitle() == null || note.getTitle().equals("")){
            throw new Exception(NOTE_TITLE_NULL);
        }
        this.note.setValue(note);
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception{
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.insertNote(note.getValue())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        insertSubscription = subscription;
                    }
                })
        );
    }
    public LiveData<Resource<Integer>> updateNote() throws Exception{
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.updateNote(note.getValue())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                updateSubscription = subscription;
                            }
                        })
        );
    }

    public boolean shouldNavigateBack(){
        if(viewState.getValue() == ViewState.VIEW){
            return true;
        }
        else{
            return false;
        }
    }


}
