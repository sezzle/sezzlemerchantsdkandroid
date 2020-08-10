package com.sezzle.sezzlemerchantsdkandroid.model

data class SezzleError(val code: String? = null,
                       val message: String? = null,
                       val location: String? = null,
                       val debug_uuid: String? = null) {
    override fun toString(): String {
        val builder: StringBuilder =
            StringBuilder("Sezzle error message: ").append(message)
                .append(", message: ").append(message)

        if (location != null) {
            builder.append(", location: ").append(location)
        }

        if (debug_uuid != null) {
            builder.append(", debug_uuid: ").append(debug_uuid)
        }

        return builder.toString()
    }
}
