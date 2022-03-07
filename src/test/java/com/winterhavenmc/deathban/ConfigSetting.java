package com.winterhavenmc.deathban;


public enum ConfigSetting {

    LANGUAGE("en-US"),
    ENABLED_WORLDS("[]"),
    DISABLED_WORLDS("[]"),
    BAN_IP("false"),
    BAN_TIME("1440"),
    KICK_DELAY("1"),
    LOG_BANS("true"),
    SOUND_EFFECTS("true"),
    ;

    private final String value;

    ConfigSetting(String value) {
        this.value = value;
    }

    public String getKey() {
        return this.name().toLowerCase().replace('_', '-');
    }
    public String getValue() {
        return this.value;
    }

}
