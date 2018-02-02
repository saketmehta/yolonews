package com.yolonews.common;

import java.util.Map;

/**
 * @author saket.mehta
 */
public interface Mappable {
    void fromMap(Map<String, String> map);

    Map<String, String> toMap();
}
