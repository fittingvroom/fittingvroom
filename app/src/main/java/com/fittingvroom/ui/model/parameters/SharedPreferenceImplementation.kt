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

    override suspend fun putParameters(modelPreferences: ModelPreferences, allParmetersOk : Boolean) {
        editor.putBoolean(MODEL_PREFERENCES_IS_SAVED, allParmetersOk)
        editor.putString(MODEL_PREFERENCES_GENDER, modelPreferences.gender)
        editor.putString(MODEL_PREFERENCES_HEIGHT, modelPreferences.height)
        editor.putString(MODEL_PREFERENCES_CHEST_GIRTH, modelPreferences.chestGirth)
        editor.putString(MODEL_PREFERENCES_WAIST_GIRTH, modelPreferences.waistGirth)
        editor.putString(MODEL_PREFERENCES_HIPS_GIRTH, modelPreferences.hipsGirth)
        editor.putString(MODEL_PREFERENCES_CHEST_WIDTH, modelPreferences.chestWidth)
        editor.putString(MODEL_PREFERENCES_BACK_WIDTH, modelPreferences.backWidth)
        editor.apply()
    }

    override suspend fun getParameters(): ModelPreferences {
        var result = ModelPreferences()
        if (modelSharedPref.contains(MODEL_PREFERENCES_IS_SAVED) && modelSharedPref.getBoolean(MODEL_PREFERENCES_IS_SAVED, false)) {
            result = ModelPreferences (
                    isSaved = modelSharedPref.getBoolean(MODEL_PREFERENCES_IS_SAVED, false),
                    gender = modelSharedPref.getString(MODEL_PREFERENCES_GENDER, "Female"),
                    height = modelSharedPref.getString(MODEL_PREFERENCES_HEIGHT, ""),
                    chestGirth = modelSharedPref.getString(MODEL_PREFERENCES_CHEST_GIRTH,  ""),
                    waistGirth = modelSharedPref.getString(MODEL_PREFERENCES_WAIST_GIRTH, ""),
                    hipsGirth = modelSharedPref.getString(MODEL_PREFERENCES_HIPS_GIRTH, ""),
                    chestWidth = modelSharedPref.getString(MODEL_PREFERENCES_CHEST_WIDTH, ""),
                    backWidth = modelSharedPref.getString(MODEL_PREFERENCES_BACK_WIDTH, "")
            )
        }
        return result
    }
}