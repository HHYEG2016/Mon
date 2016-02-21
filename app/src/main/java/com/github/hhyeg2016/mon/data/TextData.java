package com.github.hhyeg2016.mon.data;

public class TextData extends Data {
    private String state;
    private String subState;
    private String address;

    public TextData(Long logTime, String state, String subState, String address) {
        this.setType("TextData");
        this.setLogTime(logTime);
        this.setState(state);
        this.setSubState(subState);
        this.setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        this.subState = subState;
    }

}
