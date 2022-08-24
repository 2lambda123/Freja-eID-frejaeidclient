package com.verisec.frejaeid.client.beans.general;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class OrganisationIdAttribute {

    private final String key;
    private final String friendlyName;
    private final String value;

    /**
     * Creates instance of {@linkplain OrganisationIdAttribute}.
     *
     * @param key            identifier of the attribute. It is not displayed to the end user.
     *
     * @param friendlyName   name of attribute that will be set to user (for
     *                       example "employee number"). Maximum length is 30 characters. It cannot be
     *                       {@code null} or empty.
     * @param value          value of attribute that will be set for user. Maximum length is 128 characters.
     *                       It cannot be {@code null} or empty.
     * @return information about organisation id attribute
     */
    public static OrganisationIdAttribute create(String key, String friendlyName, String value) {
        return new OrganisationIdAttribute(key, friendlyName, value);
    }
    @JsonCreator
    private OrganisationIdAttribute(@JsonProperty("key") String key,
                                    @JsonProperty("friendlyName") String friendlyName,
                                    @JsonProperty("value") String value) {
        this.key = key;
        this.friendlyName = friendlyName;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationIdAttribute that = (OrganisationIdAttribute) o;
        return Objects.equals(key, that.key) && Objects.equals(friendlyName,
                                                               that.friendlyName) && Objects.equals(
                value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, friendlyName, value);
    }

    @Override
    public String toString() {
        return "OrganisationIdAttribute{" +
                "key='" + key + '\'' +
                ", friendlyName='" + friendlyName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
