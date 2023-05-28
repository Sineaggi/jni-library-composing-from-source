package com.github.sineaggi.jniutils;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class AWTUtilTest {

    @Test
    public void basicTest() {
        Component component;
        if (true) {
            Frame f = new Frame();
            f.setSize(500, 500);
            f.setVisible(true);
            component = f;
        } else {
            JFrame f = new JFrame("Swing Paint Demo");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(250, 250);
            f.setVisible(true);
            component = f;
        }

        AWTUtil.run(component, (platformInfo) -> {

        });

    }

}
