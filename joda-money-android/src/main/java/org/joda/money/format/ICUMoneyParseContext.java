package org.joda.money.format;

import android.icu.util.ULocale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;

/**
 * Context used when parsing money using built-in {@code android.icu.util.ULocale}.
 * <p>
 * This class is mutable and intended for use by a single thread.
 * A new instance is created for each parse.
 */
public final class ICUMoneyParseContext extends AbstractMoneyParseContext<ULocale> {

    /**
     * Constructor.
     *
     * @param locale the locale, not null
     * @param text   the text to parse, not null
     * @param index  the current text index
     */
    ICUMoneyParseContext(@NonNull ULocale locale, @NonNull CharSequence text, int index) {
        super(locale, text, index);
    }

    /**
     * Constructor.
     *
     * @param locale     the locale, not null
     * @param text       the text to parse, not null
     * @param index      the current text index
     * @param errorIndex the error index
     * @param currency   the currency
     * @param amount     the parsed amount
     */
    ICUMoneyParseContext(@NonNull ULocale locale, @NonNull CharSequence text, int index, int errorIndex, @Nullable CurrencyUnit currency, @Nullable BigDecimal amount) {
        super(locale, text, index, errorIndex, currency, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Creates a child context.
     *
     * @return the child context, never null
     */
    @Override
    @NonNull
    ICUMoneyParseContext createChild() {
        return new ICUMoneyParseContext(locale, text, textIndex, textErrorIndex, currency, amount);
    }
}
