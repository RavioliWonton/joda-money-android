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
package org.joda.money;

import android.content.Context;
import android.content.res.AssetManager;
import android.icu.util.Currency;
import android.icu.util.ULocale;
import android.os.Build;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads Currencies firstly from embedded ICU library or system JDK library then from our data file.
 * <p>
 * This reads currencies firstly from {@link android.icu.util.Currency} library when API Level is
 * above 24 and register them.
 * Because system JDK {@link java.util.Currency} library lacks information about numeric code in
 * legacy Android SDK so it wouldn't be used.
 * Then it reads the mandatory resource named {@code /assets/MoneyData.csv}.
 * The content in the file may replace entries in library since it update frequently
 * and independently from built-in database.
 */
class CurrencyUnitDataProvider extends BaseCurrencyUnitDataProvider {

    /**
     * Default sources name, which is bundled in this library.
     */
    private static final String defaultFile = "MoneyData.csv";
    /**
     * Regex format for the csv line.
     */
    private static final Pattern REGEX_LINE = Pattern.compile("([A-Z]{3}),(-1|[0-9]{1,3}),(-1|[0-9]),([A-Z]*)#?.*");
    /**
     * AssetManager to read asset from sources.
     */
    private final AssetManager assetManager;

    /**
     * Constructor needs a Context to load file from assets.
     */
    CurrencyUnitDataProvider(@NonNull Context context) {
        this.assetManager = context.getAssets();
    }

    /**
     * Registers all the currencies known by this provider.
     *
     * @throws Exception if an error occurs
     */
    @Override
    public void registerCurrencies() throws Exception {
        defaultLoadCurrencies();
    }

    /**
     * Loads Currencies firstly from embedded ICU library then from our data file
     *
     * @throws Exception if necessary file is not found
     */
    private void defaultLoadCurrencies() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (ULocale locale : Currency.getAvailableULocales()) {
                Currency currency = Currency.getInstance(locale);
                if (currency != null) {
                    if (CurrencyUnit.registeredCurrencies().stream().noneMatch(unit -> unit.getCode().equals(currency.getCurrencyCode())))
                        registerCurrency(currency.getCurrencyCode(), currency.getNumericCode(), currency.getDefaultFractionDigits(), List.of(locale.getCountry()));
                    else registerCountry(currency.getCurrencyCode(), List.of(locale.getCountry()));
                }
            }
        }
        try (InputStream in = assetManager.open(defaultFile)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = REGEX_LINE.matcher(line);
                if (matcher.matches()) {
                    List<String> countryCodes = new ArrayList<>();
                    String codeStr = matcher.group(4);
                    String currencyCode = Objects.requireNonNull(matcher.group(1));
                    if (Objects.requireNonNull(codeStr).length() % 2 == 1) {
                        continue;  // invalid line
                    }
                    for (int i = 0; i < codeStr.length(); i += 2) {
                        countryCodes.add(codeStr.substring(i, i + 2));
                    }
                    int numericCode = Integer.parseInt(Objects.requireNonNull(matcher.group(2)));
                    int digits = Integer.parseInt(Objects.requireNonNull(matcher.group(3)));
                    if (CurrencyUnit.registeredCurrencies().stream().noneMatch(currencyUnit ->
                            currencyCode.equals(currencyUnit.getCode()) &&
                                    numericCode == currencyUnit.getNumericCode() &&
                                    digits == currencyUnit.getDecimalPlaces()))
                        registerCurrency(currencyCode, numericCode, digits, countryCodes);
                    else if (!CurrencyUnit.of(currencyCode).getCountryCodes().containsAll(countryCodes)) {
                        countryCodes.removeAll(CurrencyUnit.of(currencyCode).getCountryCodes());
                        registerCountry(currencyCode, countryCodes);
                    }
                }
            }
            reader.close();
        }
    }
}
