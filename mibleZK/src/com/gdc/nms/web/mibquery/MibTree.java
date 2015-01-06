package com.gdc.nms.web.mibquery;

import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import net.percederberg.mibble.browser.MibNode;

public  class MibTree extends JTree {

    /**
     * Creates a new MIB tree.
     */
    public MibTree() {
        super(new MibNode("Mibble Browser", null));
    }

    /**
     * Returns the tool tip text for a specified mouse event.
     *
     * @param e              the mouse event
     *
     * @return the tool tipe text, or
     *         null for none
     */
    public String getToolTipText(MouseEvent e) {
        TreePath  path;
        MibNode   node;

        if (getRowForLocation(e.getX(), e.getY()) == -1) {
            return null;    
        }
        path = getPathForLocation(e.getX(), e.getY());
        node = (MibNode) path.getLastPathComponent();
        return node.getToolTipText();
    }
}