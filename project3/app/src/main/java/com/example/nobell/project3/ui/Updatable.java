package com.example.nobell.project3.ui;

/* Fragment stack */
public interface Updatable {
    /* This function is called when the fragment is re-activated. */
    public void reactivated();

    /* MainActivity calls this function of all Updatable Fragments in the fragmentStack. */
    public void notifyChanged();
}