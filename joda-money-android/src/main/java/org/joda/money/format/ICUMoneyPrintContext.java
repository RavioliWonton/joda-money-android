package org.joda.money.format;

import android.icu.util.ULocale;

public final class ICUMoneyPrintContext extends AbstractMoneyPrintContext<ULocale> {

    /**
     * Constructor.
     *
     * @param locale the locale, not null
     */
    ICUMoneyPrintContext(ULocale locale) {
        super(locale);
    }
}
