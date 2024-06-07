package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Country;

public class RegionsData {
    private Integer id;
    private String name;
    private String code;
    private Country country;

    public RegionsData(
            @JsonProperty("id")
            Integer id,
            @JsonProperty("name")
            String name,
            @JsonProperty("code")
            String code,
            @JsonProperty("country")
            Country country
    ) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Country getCountry() {
        return country;
    }
}
