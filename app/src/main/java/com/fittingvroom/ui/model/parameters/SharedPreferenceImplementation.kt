package com.fittingvroom.ui.model.parameters

import android.content.Context
import com.fittingvroom.data.ModelPreferences

class SharedPreferencesImplementation(private val context: Context) : SaveModelParameters {

    companion object {
        const val MODEL_PREFERENCES = "com.fittingvroom.ui.model.parameters.sp_parameters_file"

        const val MODEL_PREFERENCES_IS_SAVED = "com.fittingvroom.ui.model.parameters.sp_parameters_file.IS_SAVED"
        const val MODEL_PREFERENCES_GENDER = "com.fittingvroom.ui.model.parameters.sp_parameters_file.GENDER" //пол
        const val MODEL_PREFERENCES_HEIGHT = "com.fittingvroom.ui.model.parameters.sp_parameters_file.HEIGHT" //рост
        const val MODEL_PREFERENCES_CHEST_GIRTH = "com.fittingvroom.ui.model.parameters.sp_parameters_file.CHEST_GIRTH" //обхват груди
        const val MODEL_PREFERENCES_WAIST_GIRTH = "com.fittingvroom.ui.model.parameters.sp_parameters_file.WAIST_GIRTH" //обхват талии
        const val MODEL_PREFERENCES_HIPS_GIRTH = "com.fittingvroom.ui.model.parameters.sp_parameters_file.HIPS_GIRTH" //обхват бёдер
        const val MODEL_PREFERENCES_CHEST_WIDTH = "com.fittingvroom.ui.model.parameters.sp_parameters_file.CHEST_WIDTH" //ширина груди
        const val MODEL_PREFERENCES_BACK_WIDTH = "com.fittingvroom.ui.model.parameters.sp_parameters_file.BACK_WIDTH" //ширина спины
    }

        private val modelSharedPref = context.getSharedPreferences(MODEL_PREFERENCES, Context.MODE_PRIVATE)
        private val editor = modelSharedPref.edit()

    override suspend fun putParameters(modelPreferences: ModelPreferences) {
        editor.putBoolean(MODEL_PREFERENCES_IS_SAVED, true)
        editor.putString(MODEL_PREFERENCES_GENDER, modelPreferences.gender)
        editor.putFloat(MODEL_PREFERENCES_HEIGHT, modelPreferences.height)
        editor.putFloat(MODEL_PREFERENCES_CHEST_GIRTH, modelPreferences.chestGirth)
        editor.putFloat(MODEL_PREFERENCES_WAIST_GIRTH, modelPreferences.waistGirth)
        editor.putFloat(MODEL_PREFERENCES_HIPS_GIRTH, modelPreferences.hipsGirth)
        editor.putFloat(MODEL_PREFERENCES_CHEST_WIDTH, modelPreferences.chestWidth)
        editor.putFloat(MODEL_PREFERENCES_BACK_WIDTH, modelPreferences.backWidth)
        editor.apply()
    }

    override suspend fun getParameters(): ModelPreferences {
        var result = ModelPreferences()
        if (modelSharedPref.contains(MODEL_PREFERENCES_IS_SAVED) && modelSharedPref.getBoolean(MODEL_PREFERENCES_IS_SAVED, false)) {
            result = ModelPreferences (
                    isSaved = true,
                    gender = modelSharedPref.getString(MODEL_PREFERENCES_GENDER, "Female"),
                    height = modelSharedPref.getFloat(MODEL_PREFERENCES_HEIGHT, 0F),
                    chestGirth = modelSharedPref.getFloat(MODEL_PREFERENCES_CHEST_GIRTH,  0F),
                    waistGirth = modelSharedPref.getFloat(MODEL_PREFERENCES_WAIST_GIRTH, 0F),
                    hipsGirth = modelSharedPref.getFloat(MODEL_PREFERENCES_HIPS_GIRTH, 0F),
                    chestWidth = modelSharedPref.getFloat(MODEL_PREFERENCES_CHEST_WIDTH, 0F),
                    backWidth = modelSharedPref.getFloat(MODEL_PREFERENCES_BACK_WIDTH, 0F)
            )
        }
        return result
    }
}