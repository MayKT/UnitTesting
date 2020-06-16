package com.mkttestprojects.unittesting.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mkttestprojects.unittesting.ui.note.NoteViewModel;
import com.mkttestprojects.unittesting.ui.notelist.NoteListViewModel;
import com.mkttestprojects.unittesting.viewmodels.ViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {
    
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel.class)
    public abstract ViewModel bindNoteViewModel(NoteViewModel noteViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel.class)
    public abstract ViewModel bindNoteListViewModel(NoteListViewModel noteListViewModel);
}
