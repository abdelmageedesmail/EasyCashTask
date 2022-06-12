package com.abdelmageed.easycashtask.uitls

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.abdelmageed.easycashtask.data.locale.db.ConstanceVar
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.safetynet.SafetyNet
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class Utils {
    companion object {

        private val mRandom = java.security.SecureRandom()


        /**
         * Emulator check
         */
        private val TAG = "UTILS"

        private val GENY_FILES = arrayOf(
            "/dev/socket/genyd",
            "/dev/socket/baseband_genyd"
        )
        private val PIPES = arrayOf(
            "/dev/socket/qemud",
            "/dev/qemu_pipe"
        )
        private val X86_FILES = arrayOf(
            "ueventd.android_x86.rc",
            "x86.prop",
            "ueventd.ttVM_x86.rc",
            "init.ttVM_x86.rc",
            "fstab.ttVM_x86",
            "fstab.vbox86",
            "init.vbox86.rc",
            "ueventd.vbox86.rc"
        )
        private val ANDY_FILES = arrayOf(
            "fstab.andy",
            "ueventd.andy.rc"
        )
        private val NOX_FILES = arrayOf(
            "fstab.nox",
            "init.nox.rc",
            "ueventd.nox.rc"
        )

        fun checkFiles(targets: Array<String>): Boolean {
            for (pipe in targets) {
                val file = File(pipe)
                if (file.exists()) {
                    return true
                }
            }
            return false
        }

        fun checkIsEmulator(): Boolean {
            return (checkFiles(GENY_FILES)
                    || checkFiles(ANDY_FILES)
                    || checkFiles(NOX_FILES)
                    || checkFiles(X86_FILES)
                    || checkFiles(PIPES))
        }

        /**
         * safety net check return server key response
         * send this response to api (Server Side) to check if this key is safe or not
         */

        fun getSafetyNet(context: Context) {
            val nonceData = "Safety Net Sample: " + System.currentTimeMillis()
            val nonce = getRequestNonce(nonceData)

            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
                == ConnectionResult.SUCCESS
            ) {
                if (nonce != null) {
                    SafetyNet.getClient(context).attest(nonce, ConstanceVar.API_KEY)
                        .addOnSuccessListener {
                            Log.e(TAG, "successSafety...." + it.jwsResult)
                        }.addOnFailureListener {
                            Log.e(TAG, "errorSafety...." + it.toString())
                        }
                }
            } else {
                // Prompt user to update Google Play services.
                Toast.makeText(context, "Ops no google services first", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        private fun getRequestNonce(data: String): ByteArray? {
            val byteStream = ByteArrayOutputStream()
            val bytes = ByteArray(16)
            mRandom.nextBytes(bytes)
            try {
                byteStream.write(bytes)
                byteStream.write(data.toByteArray())
            } catch (e: IOException) {
                return null
            }
            return byteStream.toByteArray()
        }

    }
}