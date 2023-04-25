package com.sendpost.dreamsoft.BusinessCard;

import com.sendpost.dreamsoft.R;

public enum ModelObject {

    CARD1(R.layout.business_card1),
    CARD2(R.layout.business_card2),
    CARD3(R.layout.business_card3),
    CARD4(R.layout.business_card4);

    private int mLayoutResId;

    ModelObject(int layoutResId) {
        mLayoutResId = layoutResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}