package com.reece;

import com.reece.ui.AddressBookInfoFrame;
import javax.swing.*;

/**
 * This class is the entry point of the address book app.
 *
 * @author zhimeng
 * @version 1.0 5/8/2018
 * @since 1.0
 */
public class App {

    public static void main(String argv[]) {
        /* gets the user interface */
        JFrame addressBookInfoFrame = new AddressBookInfoFrame("Address Book Info");
        addressBookInfoFrame.setVisible(true);
    }
}