package org.joda.money.format;

import androidx.annotation.NonNull;

abstract class AbstractMoneyPrintContext<T> {
    /**
     * The locale to print using.
     */
    private T locale;

    /**
     * Constructor.
     *
     * @param locale the locale, not null
     */
    AbstractMoneyPrintContext(T locale) {
        this.locale = locale;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the locale.
     *
     * @return the locale, never null
     */
    @NonNull
    public T getLocale() {
        return locale;
    }

    /**
     * Sets the locale.
     *
     * @param locale the locale, not null
     */
    public void setLocale(@NonNull T locale) {
        MoneyFormatter.checkNotNull(locale, "Locale must not be null");
        this.locale = locale;
    }
}
