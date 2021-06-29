package com.flat.models.fx;

import com.flat.models.json.language.JsonLanguage;
import edu.emory.mathcs.backport.java.util.Arrays;
import javafx.collections.FXCollections;

import java.util.List;

/**
 * @author Christopher Brantley <ccbrantley@uncg.edu>
 */
public class FxLanguageData {
    private final static List<JsonLanguage> languages = FXCollections.observableArrayList();

    public static void injectData(JsonLanguage[] _languages) {
        FxLanguageData.languages.removeAll(FxLanguageData.languages);
        FxLanguageData.languages.addAll(Arrays.asList(_languages));
    }

    // Getters for object's attributes.
    public static List<JsonLanguage> getLanguages() {
        return languages;
    }

}