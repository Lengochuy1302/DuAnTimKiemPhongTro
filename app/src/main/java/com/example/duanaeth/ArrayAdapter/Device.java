package com.example.duanaeth.ArrayAdapter;

public class Device {
    private String ID, TenDevice;
    public Device() {
    }

    public Device(String ID, String tenDevice) {
        this.ID = ID;
        TenDevice = tenDevice;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenDevice() {
        return TenDevice;
    }

    public void setTenDevice(String tenDevice) {
        TenDevice = tenDevice;
    }
}
