package com.googlecode.memwords.domain;

import java.util.Comparator;

/**
 * Comparator which sorts {@link CardBasicInformation} instances by name.
 * @author JB
 */
public final class CardBasicInformationComparator implements Comparator<CardBasicInformation> {

    /**
     * A reusable instance of this class, which is thread-safe
     */
    public static final CardBasicInformationComparator INSTANCE =
        new CardBasicInformationComparator();

    @Override
    public int compare(CardBasicInformation o1, CardBasicInformation o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
