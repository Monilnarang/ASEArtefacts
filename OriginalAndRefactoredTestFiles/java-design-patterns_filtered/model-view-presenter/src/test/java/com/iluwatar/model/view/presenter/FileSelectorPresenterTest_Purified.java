package com.iluwatar.model.view.presenter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSelectorPresenterTest_Purified {

    private FileSelectorPresenter presenter;

    private FileSelectorStub stub;

    private FileLoader loader;

    @BeforeEach
    void setUp() {
        this.stub = new FileSelectorStub();
        this.loader = new FileLoader();
        presenter = new FileSelectorPresenter(this.stub);
        presenter.setLoader(loader);
    }

    @Test
    void wiring_1() {
        assertNotNull(stub.getPresenter());
    }

    @Test
    void wiring_2() {
        assertTrue(stub.isOpened());
    }

    @Test
    void fileConfirmationWhenNameIsNull_1() {
        presenter.start();
        presenter.fileNameChanged();
        presenter.confirmed();
        assertFalse(loader.isLoaded());
    }

    @Test
    void fileConfirmationWhenNameIsNull_2() {
        stub.setFileName(null);
        assertEquals(1, stub.getMessagesSent());
    }

    @Test
    void fileConfirmationWhenFileDoesNotExist_1() {
        presenter.start();
        presenter.fileNameChanged();
        presenter.confirmed();
        assertFalse(loader.isLoaded());
    }

    @Test
    void fileConfirmationWhenFileDoesNotExist_2() {
        stub.setFileName("RandomName.txt");
        assertEquals(1, stub.getMessagesSent());
    }

    @Test
    void fileConfirmationWhenFileExists_1() {
        presenter.start();
        presenter.fileNameChanged();
        presenter.confirmed();
        assertTrue(loader.isLoaded());
    }

    @Test
    void fileConfirmationWhenFileExists_2() {
        stub.setFileName("etc/data/test.txt");
        assertTrue(stub.dataDisplayed());
    }

    @Test
    void testNullFile_1() {
        presenter.start();
        presenter.fileNameChanged();
        presenter.confirmed();
        assertFalse(loader.isLoaded());
    }

    @Test
    void testNullFile_2() {
        stub.setFileName(null);
        assertFalse(stub.dataDisplayed());
    }
}
