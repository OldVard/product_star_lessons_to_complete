package org.example.analytics;

public class CountryStats {
    private String country;
    private long citiesCount;

    public CountryStats(String country, long citiesCount) {
        this.country = country;
        this.citiesCount = citiesCount;
    }

    public CountryStats() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getCitiesCount() {
        return citiesCount;
    }

    public void setCitiesCount(long citiesCount) {
        this.citiesCount = citiesCount;
    }

    @Override
    public String toString() {
        return "CountryStats [country=" + country + ", citiesCount=" + citiesCount + "]";
    }

    public static CountryStats fromObject(Object object) {
        Object[] objects = (Object[]) object;
        return new CountryStats((String) objects[1], (long) objects[0]);
    }
}
