package com.iluwatar.bloc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.*;
import javax.swing.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlocUiTest_Purified {

    private JFrame frame;

    private JLabel counterLabel;

    private JButton incrementButton;

    private JButton decrementButton;

    private JButton toggleListenerButton;

    private Bloc bloc;

    private StateListener<State> stateListener;

    @BeforeEach
    void setUp() {
        bloc = new Bloc();
        frame = new JFrame("BloC example");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        counterLabel = new JLabel("Counter: 0", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        incrementButton = new JButton("Increment");
        decrementButton = new JButton("Decrement");
        toggleListenerButton = new JButton("Disable Listener");
        frame.setLayout(new BorderLayout());
        frame.add(counterLabel, BorderLayout.CENTER);
        frame.add(incrementButton, BorderLayout.NORTH);
        frame.add(decrementButton, BorderLayout.SOUTH);
        frame.add(toggleListenerButton, BorderLayout.EAST);
        stateListener = state -> counterLabel.setText("Counter: " + state.value());
        bloc.addListener(stateListener);
        incrementButton.addActionListener(e -> bloc.increment());
        decrementButton.addActionListener(e -> bloc.decrement());
        toggleListenerButton.addActionListener(e -> {
            if (bloc.getListeners().contains(stateListener)) {
                bloc.removeListener(stateListener);
                toggleListenerButton.setText("Enable Listener");
            } else {
                bloc.addListener(stateListener);
                toggleListenerButton.setText("Disable Listener");
            }
        });
        frame.setVisible(true);
    }

    @AfterEach
    void tearDown() {
        frame.dispose();
        bloc = new Bloc();
    }

    private void simulateButtonClick(JButton button) {
        for (var listener : button.getActionListeners()) {
            listener.actionPerformed(null);
        }
    }

    @Test
    void testToggleListenerButton_1() {
        assertEquals("Counter: 0", counterLabel.getText());
    }

    @Test
    void testToggleListenerButton_2() {
        assertEquals("Counter: 2", counterLabel.getText());
    }
}
