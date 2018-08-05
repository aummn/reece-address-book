package com.reece.ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Closes a window. Subclasses this class to define more event handling.
 * <code>WindowCloser</code> listens to window events and releases the system's
 * windowing resources when detecting the window it attached is closing.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class WindowCloser extends WindowAdapter {
    
    /**
     * Creates a new <code>WindowCloser</code>
     */
    public WindowCloser() {}
    
    /**
     * Closes a window. Invoked when a window is in the process of being closed.
     *
     * @param wevt a low-level event that indicates
     * that a window has changed its status
     */
    public void windowClosing(WindowEvent wevt) {
        
        Window window = wevt.getWindow();
        window.setVisible(false);
        
        /* releases all of the native screen resources used by this window */
        window.dispose();
        System.exit(0);
        
    }
}