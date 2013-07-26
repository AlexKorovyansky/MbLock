package com.alexkorovyansky.mblock.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MbLock
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLock implements Parcelable{
    public String name;
    public String macAddress;

    public MbLock(String name, String macAddress) {
        this.name = name;
        this.macAddress = macAddress;
    }

    // Parcelling part
    public MbLock(Parcel in){
        this.name = in.readString();
        this.macAddress = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MbLock) {
            MbLock mbLock = (MbLock) o;
            return macAddress.equals(mbLock.macAddress);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return macAddress.hashCode();
    }

    @Override
    public String toString() {
        return "MbLock{" +
                "name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(macAddress);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public MbLock createFromParcel(Parcel in) {
            return new MbLock(in);
        }

        public MbLock[] newArray(int size) {
            return new MbLock[size];
        }
    };
}
