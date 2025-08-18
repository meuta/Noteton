package com.obrigada_eu.noteton.di

import android.content.Context
import com.obrigada_eu.noteton.core.CacheManager
import com.obrigada_eu.noteton.data.db.NotesDatabase
import com.obrigada_eu.noteton.data.db.NoteDao
import com.obrigada_eu.noteton.data.repository.NotesRepositoryImpl
import com.obrigada_eu.noteton.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNoteDao(db: NotesDatabase): NoteDao {
        return db.noteDao()
    }

    @Provides
    @Singleton
    fun provideRepository(noteDao: NoteDao): NotesRepository {
        return NotesRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext context: Context): CacheManager = CacheManager(context)
}