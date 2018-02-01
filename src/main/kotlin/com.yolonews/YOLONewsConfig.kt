package com.yolonews

import io.dropwizard.Configuration
import org.hibernate.validator.constraints.NotEmpty

class YOLONewsConfig : Configuration() {
    @NotEmpty
    var redisURL: String? = null
    @NotEmpty
    val tokenHeaderPrefix: String? = null
    @NotEmpty
    val tokenQueryParamKey: String? = null
}