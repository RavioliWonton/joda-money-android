package org.joda.money;

import android.content.Context;
import android.content.res.AssetManager;

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

public class FileCurrencyUnitDataProvider extends BaseCurrencyUnitDataProvider {

    /**
     * Regex format for the csv line.
     */
    private static final Pattern REGEX_LINE = Pattern.compile("([A-Z]{3}),(-1|[0-9]{1,3}),(-1|[0-9]),([A-Z]*)#?.*");
    private final AssetManager assetManager;
    private final String filepath;

    /**
     * Constructor needs a Context to load file from assets
     */
    public FileCurrencyUnitDataProvider(@NonNull Context context, String filepath) {
        this.assetManager = context.getAssets();
        this.filepath = filepath;
    }

    /**
     * Registers all the currencies known by this provider.
     *
     * @throws Exception if an error occurs
     */
    @Override
    public void registerCurrencies() throws Exception {
        loadCurrenciesFromFile(filepath, true);
    }

    /**
     * Loads Currencies from a file
     *
     * @param fileName    the file to load, not null
     * @param isNecessary whether or not the file is necessary
     * @throws Exception if a necessary file is not found
     */
    private void loadCurrenciesFromFile(@NonNull String fileName, boolean isNecessary) throws Exception {
        try (InputStream in = assetManager.open(fileName)) {
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
                    registerCurrency(currencyCode, numericCode, digits, countryCodes);
                }
            }
            reader.close();
        } catch (Exception ex) {
            if (isNecessary) throw ex;
        }
    }
}
