package com.lxl.simplemusicplayer.entity

import android.os.Parcel
import android.os.Parcelable

data class MusicInfo(val id: Long,
                     val title: String,
                     val album: String,
                     val artist: String,
                     val displayName: String,
                     val duration: Long,
                     val size: Long,
                     val url: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readLong(),
            source.readLong(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(album)
        writeString(artist)
        writeString(displayName)
        writeLong(duration)
        writeLong(size)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MusicInfo> = object : Parcelable.Creator<MusicInfo> {
            override fun createFromParcel(source: Parcel): MusicInfo = MusicInfo(source)
            override fun newArray(size: Int): Array<MusicInfo?> = arrayOfNulls(size)
        }
    }
}