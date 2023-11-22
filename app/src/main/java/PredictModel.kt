import android.os.Parcel
import android.os.Parcelable

class PredictModel(
    val label: String,
    var confidence: Float = 0f,
    var description: String = "Description of label"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(label)
        parcel.writeFloat(confidence)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PredictModel> {
        override fun createFromParcel(parcel: Parcel): PredictModel {
            return PredictModel(parcel)
        }

        override fun newArray(size: Int): Array<PredictModel?> {
            return arrayOfNulls(size)
        }
    }
}
