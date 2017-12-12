package com.riteboiler.frontoffice.util;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.riteboiler.frontoffice.model.Preference;

/**
 * This class is a helper class to wrap preferences. 
 * This is used for saving the preferences to XML.
 * @author Chad Meza
 */
@XmlRootElement(name = "preferences")
public class PreferenceWrapper {

    private List<Preference> preferences;

    @XmlElement(name = "preference")
    public List<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }
}