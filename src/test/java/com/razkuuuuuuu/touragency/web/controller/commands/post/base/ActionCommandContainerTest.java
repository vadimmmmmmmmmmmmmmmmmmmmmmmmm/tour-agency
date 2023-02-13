package com.razkuuuuuuu.touragency.web.controller.commands.post.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionCommandContainerTest {
    private static ActionCommandContainer container;
    @BeforeAll
    static void setUp() {
        container = new ActionCommandContainer();
    }

    @Test
    void testEmptyName() {
        assertFalse(container.hasCommand(""));
    }

    @Test
    void testNull() {
        assertFalse(container.hasCommand(null));
    }

    @Test
    void getCommand() {
        assertNull(container.getCommand(null));
    }
}