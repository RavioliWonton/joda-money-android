package org.joda.money.format;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.text.ParsePosition;

abstract class AbstractMoneyParseContext<T> {

    /**
     * The locale to parse using.
     */
    protected T locale;
    /**
     * The text to parse.
     */
    protected CharSequence text;
    /**
     * The text index.
     */
    protected int textIndex;
    /**
     * The text error index.
     */
    protected int textErrorIndex = -1;
    /**
     * The parsed currency.
     */
    protected CurrencyUnit currency;
    /**
     * The parsed amount.
     */
    protected BigDecimal amount;

    /**
     * Constructor.
     *
     * @param locale the locale, not null
     * @param text   the text to parse, not null
     * @param index  the current text index
     */
    AbstractMoneyParseContext(@NonNull T locale, @NonNull CharSequence text, int index) {
        this.locale = locale;
        this.text = text;
        this.textIndex = index;
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
    AbstractMoneyParseContext(@NonNull T locale, @NonNull CharSequence text, int index, int errorIndex, @Nullable CurrencyUnit currency, @Nullable BigDecimal amount) {
        this.locale = locale;
        this.text = text;
        this.textIndex = index;
        this.textErrorIndex = errorIndex;
        this.currency = currency;
        this.amount = amount;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the locale.
     *
     * @return the locale, not null
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

    /**
     * Gets the text being parsed.
     *
     * @return the text being parsed, never null
     */
    @NonNull
    public CharSequence getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the text being parsed, not null
     */
    public void setText(@NonNull CharSequence text) {
        MoneyFormatter.checkNotNull(text, "Text must not be null");
        this.text = text;
    }

    /**
     * Gets the length of the text being parsed.
     *
     * @return the length of the text being parsed
     */
    public int getTextLength() {
        return text.length();
    }

    /**
     * Gets a substring of the text being parsed.
     *
     * @param start the start index
     * @param end   the end index
     * @return the substring, not null
     */
    @NonNull
    public String getTextSubstring(int start, int end) {
        return text.subSequence(start, end).toString();
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the current parse position index.
     *
     * @return the current parse position index
     */
    public int getIndex() {
        return textIndex;
    }

    /**
     * Sets the current parse position index.
     *
     * @param index the current parse position index
     */
    public void setIndex(int index) {
        this.textIndex = index;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the error index.
     *
     * @return the error index, negative if no error
     */
    public int getErrorIndex() {
        return textErrorIndex;
    }

    /**
     * Sets the error index.
     *
     * @param index the error index
     */
    public void setErrorIndex(int index) {
        this.textErrorIndex = index;
    }

    /**
     * Sets the error index from the current index.
     */
    public void setError() {
        this.textErrorIndex = textIndex;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the parsed currency.
     *
     * @return the parsed currency, null if not parsed yet
     */
    @Nullable
    public CurrencyUnit getCurrency() {
        return currency;
    }

    /**
     * Sets the parsed currency.
     *
     * @param currency the parsed currency, may be null
     */
    public void setCurrency(@Nullable CurrencyUnit currency) {
        this.currency = currency;
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the parsed amount.
     *
     * @return the parsed amount, null if not parsed yet
     */
    @Nullable
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the parsed currency.
     *
     * @param amount the parsed amount, may be null
     */
    public void setAmount(@Nullable BigDecimal amount) {
        this.amount = amount;
    }

    //-----------------------------------------------------------------------

    /**
     * Checks if the parse has found an error.
     *
     * @return whether a parse error has occurred
     */
    public boolean isError() {
        return textErrorIndex >= 0;
    }

    /**
     * Checks if the text has been fully parsed such that there is no more text to parse.
     *
     * @return true if fully parsed
     */
    public boolean isFullyParsed() {
        return textIndex == getTextLength();
    }

    /**
     * Checks if the context contains a currency and amount suitable for creating
     * a monetary value.
     *
     * @return true if able to create a monetary value
     */
    public boolean isComplete() {
        return currency != null && amount != null;
    }

    //-----------------------------------------------------------------------

    /**
     * Creates a child context.
     *
     * @return the child context, never null
     */
    @NonNull
    abstract AbstractMoneyParseContext<T> createChild();

    /**
     * Merges the child context back into this instance.
     *
     * @param child the child context, not null
     */
    void mergeChild(@NonNull AbstractMoneyParseContext<T> child) {
        setLocale(child.getLocale());
        setText(child.getText());
        setIndex(child.getIndex());
        setErrorIndex(child.getErrorIndex());
        setCurrency(child.getCurrency());
        setAmount(child.getAmount());
    }

    //-----------------------------------------------------------------------

    /**
     * Converts the indexes to a parse position.
     *
     * @return the parse position, never null
     */
    public ParsePosition toParsePosition() {
        ParsePosition pp = new ParsePosition(textIndex);
        pp.setErrorIndex(textErrorIndex);
        return pp;
    }

    /**
     * Converts the context to a {@code BigMoney}.
     *
     * @return the monetary value, never null
     * @throws MoneyFormatException if either the currency or amount is missing
     */
    public BigMoney toBigMoney() {
        if (currency == null) {
            throw new MoneyFormatException("Cannot convert to BigMoney as no currency found");
        }
        if (amount == null) {
            throw new MoneyFormatException("Cannot convert to BigMoney as no amount found");
        }
        return BigMoney.of(currency, amount);
    }
}
