package com.cecom.alchol;

public interface SelectDialogListener{
    void onPositiveClicked(boolean[] selectedElements, int[] ratio);
    void onNegativeClicked();
}