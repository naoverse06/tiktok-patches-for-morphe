package app.morphe.patches.tiktok.interaction.offlinevideos

import app.morphe.patcher.Fingerprint
import app.morphe.util.getReference
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.NarrowLiteralInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

internal object OfflineModeSheetOptionsFingerprint : Fingerprint(
    returnType = "V",
    custom = { method, classDef ->
        classDef.endsWith("/OfflineModeSheetPageAssem;") &&
            method.name == "<clinit>" &&
            method.parameterTypes.isEmpty()
    },
)

internal object OfflineModeListConstructorFingerprint : Fingerprint(
    returnType = "V",
    custom = { method, classDef ->
        classDef.endsWith("/OfflineModeListVM;") &&
            method.name == "<init>" &&
            method.parameterTypes.isEmpty()
    },
)

internal object OfflineModeOptionConfigFingerprint : Fingerprint(
    returnType = "V",
    custom = { method, classDef ->
        if (method.name != "<clinit>" || method.parameterTypes.isNotEmpty()) {
            false
        } else {
            val instructions = method.implementation?.instructions?.toList().orEmpty()
            val staticListFields = instructions
                .mapNotNull { instruction ->
                    if (instruction.opcode != Opcode.SPUT_OBJECT) {
                        null
                    } else {
                        instruction.getReference<FieldReference>()?.takeIf { field ->
                            field.type == "Ljava/util/List;"
                        }
                    }
                }
            val hasOfflineOptionLimit = instructions.any { instruction ->
                (instruction as? NarrowLiteralInstruction)?.narrowLiteral == 200
            }
            val hasExpectedOptionFields = staticListFields.any { it.name == "LIZLLL" } &&
                staticListFields.any { it.name == "LJ" }

            hasOfflineOptionLimit &&
                (classDef.type == "LX/0seq;" || hasExpectedOptionFields)
        }
    },
)

internal object OfflineModeOptionEnumFingerprint : Fingerprint(
    returnType = "V",
    custom = { method, classDef ->
        if (method.name != "<clinit>" || method.parameterTypes.isNotEmpty()) {
            false
        } else {
            val instructions = method.implementation?.instructions?.toList().orEmpty()
            val hasDownloadOptionName = instructions.any { instruction ->
                instruction.getReference<StringReference>()?.string == "DOWNLOAD_240_VIDEOS"
            }
            val hasCustomLimitSize = instructions.any { instruction ->
                (instruction as? NarrowLiteralInstruction)?.narrowLiteral == 500
            }
            val hasOfflineOptionConstructor = instructions.any { instruction ->
                (instruction.opcode == Opcode.INVOKE_DIRECT || instruction.opcode == Opcode.INVOKE_DIRECT_RANGE) &&
                    instruction.getReference<MethodReference>()?.let { reference ->
                        reference.definingClass == classDef.type &&
                            reference.name == "<init>" &&
                            reference.returnType == "V" &&
                            reference.parameterTypes == listOf("Ljava/lang/String;", "I", "I", "I", "I")
                    } == true
            }

            hasDownloadOptionName && hasCustomLimitSize && hasOfflineOptionConstructor
        }
    },
)
