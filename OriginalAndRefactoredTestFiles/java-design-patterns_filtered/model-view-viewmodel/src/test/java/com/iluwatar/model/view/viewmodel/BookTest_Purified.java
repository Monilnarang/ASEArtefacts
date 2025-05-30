package com.iluwatar.model.view.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookTest_Purified {

    BookViewModel bvm;

    Book testBook;

    List<Book> testBookList;

    Book testBookTwo;

    Book testBookThree;

    @BeforeEach
    void setUp() {
        bvm = new BookViewModel();
        testBook = new Book("Head First Design Patterns: A Brain-Friendly Guide", "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson", "Head First Design Patterns Description");
        testBookList = bvm.getBookList();
        testBookTwo = new Book("Head First Design Patterns: A Brain-Friendly Guide", "Eric Freeman, Bert Bates, Kathy Sierra, Elisabeth Robson", "Head First Design Patterns Description");
        testBookThree = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides", "Design Patterns Description");
    }

    @Test
    void testToString_1() {
        assertEquals(testBook.toString(), testBookTwo.toString());
    }

    @Test
    void testToString_2() {
        assertNotEquals(testBook.toString(), testBookThree.toString());
    }

    @Test
    void testHashCode_1() {
        assertTrue(testBook.equals(testBookTwo) && testBookTwo.equals(testBook));
    }

    @Test
    void testHashCode_2() {
        assertEquals(testBook.hashCode(), testBookTwo.hashCode());
    }

    @Test
    void testLoadData_1() {
        assertNotNull(testBookList);
    }

    @Test
    void testLoadData_2() {
        assertTrue(testBookList.get(0).toString().contains("Head First Design Patterns"));
    }

    @Test
    void testDeleteData_1_testMerged_1() {
        bvm.setSelectedBook(testBook);
        assertNotNull(bvm.getSelectedBook());
        bvm.deleteBook();
        assertNull(bvm.getSelectedBook());
    }

    @Test
    void testDeleteData_2() {
        assertTrue(testBookList.get(0).toString().contains("Head First Design Patterns"));
    }

    @Test
    void testDeleteData_4() {
        assertFalse(testBookList.get(0).toString().contains("Head First Design Patterns"));
    }
}
