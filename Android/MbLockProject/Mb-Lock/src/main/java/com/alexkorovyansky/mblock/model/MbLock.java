package com.alexkorovyansky.mblock.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * MbLock
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MbLock implements Parcelable{
    public String id;
    public String majorId;
    public String majorName;

    public MbLock(String id, String majorId, String majorName) {
        this.id = id;
        this.majorId = majorId;
        this.majorName = majorName;
    }

    // Parcelling part
    public MbLock(Parcel in){
        this.id = in.readString();
        this.majorId = in.readString();
        this.majorName = in.readString();
    }

    @Override
    public String toString() {
        return "MbLock{" +
                "id='" + id + '\'' +
                ", majorId='" + majorId + '\'' +
                ", majorName='" + majorName + '\'' +
                '}';
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(majorId);
        dest.writeString(majorName);
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
