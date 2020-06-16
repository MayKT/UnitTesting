package com.mkttestprojects.unittesting.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class NoteTest {

    public static final String TIMESTAMP_1 = "05-2019";
    public static final String TIMESTAMP_2 = "04-2019";

    /*
    Compare two equal notes
     */
    @Test
    void isNoteEqual_identicalProperties() {

        // Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(1);

        // Act

        // Assert
        assertEquals(note1, note2);
        System.out.println("The notes are equal!");
    }

    /*
    Compare notes with two different ids
     */
    @Test
    void isNoteEqual_differentIds_returnFalse() {

        // Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note2.setId(2);

        // Act

        // Assert
        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal! They have different ids.");
    }

    /*
    Compare notes with two different timestamps
     */

    @Test
    void isNoteEqual_differentTimestamps() {
        // Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1", TIMESTAMP_2);
        note2.setId(1);

        // Act


        // Assert
        assertNotEquals(note1, note2);
        System.out.println("The notes are equal!");
    }


    /*
    Compare two notes with different title
     */
    @Test
    void isNoteEqual_differentTitle() {
        // Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #2", "This is note #1", TIMESTAMP_2);
        note2.setId(1);

        // Act

        // Assert
        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal! They have different title.");
    }

    /*
    Compare two notes with different content
     */
    @Test
    void isNoteEqual_differentContent() {
        // Arrange
        Note note1 = new Note("Note #1", "This is note #1", TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #2", TIMESTAMP_2);
        note2.setId(1);

        // Act

        // Assert
        assertNotEquals(note1, note2);
        System.out.println("The notes are not equal! They have different contents.");

    }
}
