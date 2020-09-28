/*
 *  Copyright 2009-present, Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.money.format;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Context used when parsing money using system {@code java.util.Locale}.
 * <p>
 * This class is mutable and intended for use by a single thread.
 * A new instance is created for each parse.
 */
public final class MoneyParseContext extends AbstractMoneyParseContext<Locale> {

    /**
     * Constructor.
     *
     * @param locale the locale, not null
     * @param text   the text to parse, not null
     * @param index  the current text index
     */
    MoneyParseContext(@NonNull Locale locale, @NonNull CharSequence text, int index) {
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
    MoneyParseContext(@NonNull Locale locale, @NonNull CharSequence text, int index, int errorIndex, @Nullable CurrencyUnit currency, @Nullable BigDecimal amount) {
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
    MoneyParseContext createChild() {
        return new MoneyParseContext(locale, text, textIndex, textErrorIndex, currency, amount);
    }
}
