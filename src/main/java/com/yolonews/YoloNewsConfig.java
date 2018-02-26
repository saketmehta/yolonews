package com.yolonews;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * @author saket.mehta
 */
class YoloNewsConfig extends Configuration {
    private Long initialUserKarma;

    @JsonProperty
    public Long getInitialUserKarma() {
        return initialUserKarma;
    }
}
