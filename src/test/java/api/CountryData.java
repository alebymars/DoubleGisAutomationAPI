package api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryData {

    private String name;
    private String code;

    public CountryData(
            @JsonProperty("name")
            String name,
            @JsonProperty("code")
            String code
    ) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
