package app.morphe.patches.tiktok.misc.navigation

import app.morphe.patcher.Fingerprint
import app.morphe.util.getReference
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

internal object HomeTabAbilityListFingerprint : Fingerprint(
    definingClass = "/TabAbilityAssem;",
    name = "eT1",
    returnType = "Ljava/util/List;",
    parameters = listOf("Z"),
)

internal object BottomTabBuildListFingerprint : Fingerprint(
    returnType = "V",
    parameters = listOf("Ljava/util/List;"),
    custom = { method, classDef ->
        val isGlobal4383 = classDef.endsWith("/0tBq;") && method.name == "LJJL"
        val isJp4383 = method.name == "LJJL" &&
            method.implementation?.instructions?.let { instructions ->
                var hasBottomOptimizationString = false
                var hasUpdateViewString = false
                var hasBottomTabViewReference = false

                instructions.forEach { instruction ->
                    instruction.getReference<StringReference>()?.string?.let { string ->
                        if (string == "bottom_tab_animation_optimization") {
                            hasBottomOptimizationString = true
                        }
                        if (string.contains("updateViewExpWithVirtualItem current bottom tab tag:")) {
                            hasUpdateViewString = true
                        }
                    }

                    instruction.getReference<FieldReference>()?.let { field ->
                        if (field.name == "BOTTOM_TAB_VIEW") {
                            hasBottomTabViewReference = true
                        }
                    }
                }

                hasBottomOptimizationString && hasUpdateViewString && hasBottomTabViewReference
            } == true

        isGlobal4383 || isJp4383
    },
)
