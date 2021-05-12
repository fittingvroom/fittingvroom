package com.fittingvroom.ui.view3d

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.fittingvroom.ui.view3d.nodes.DragTransformableNode
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer
import com.google.ar.sceneform.ux.TransformationSystem

//Если модель не отображается на эмуляторе, проверьте:

//Версии:
//Android Studio 3.1 or later.
//Android Emulator 27.2.9 or later.

//Настройка эмулятора для поддержки OpenGL ES 3.0:
//Нажать "More" (3 точки) в toolbar запущенного эмулятора.
//Выбрать Settings > Advanced > OpenGL ES API level > Renderer maximum (up to OpenGL ES 3.1).
//Перезапустить эмулятор.

//Подробнее: https://developers.google.com/sceneform/develop/android-quickstart?hl=ru


//Также, обязателен вызов resume() для SceneView в onResume() фрагмента или активити


//Временная ссылка для модели, пока не готов бэкэнд
val MODEL_URI = "https://github.com/fittingvroom/model_example/blob/main/model_example.glb?raw=true"

class SceneViewer {
    companion object {
        @JvmStatic
        fun showScene(context: Context?, resources: Resources, sceneView: SceneView?, progressBar: ProgressBar?) {
            if (progressBar != null)
                progressBar.visibility = View.VISIBLE
            else
                Toast.makeText(context, "Loading model...", Toast.LENGTH_SHORT).show()

            val source = RenderableSource.builder().setSource(
                    context,
                    Uri.parse(MODEL_URI),
                    RenderableSource.SourceType.GLB
            )
                    .setScale(0.7f) // Scale the original model to 70%.
                    .setRecenterMode(RenderableSource.RecenterMode.CENTER)
                    .build()

            ModelRenderable.builder()
                    .setSource(context, source)
                    .setRegistryId(MODEL_URI)
                    .build()
                    .thenAccept { renderable: ModelRenderable ->
                        if (progressBar != null)
                            progressBar.visibility = View.GONE
                        else
                            Toast.makeText(context, "Model loaded", Toast.LENGTH_SHORT).show()
                        addNodeToScene(renderable, sceneView, resources)
                    }
                    .exceptionally { throwable: Throwable? ->
                        if (progressBar != null)
                            progressBar.visibility = View.GONE
                        else
                            Toast.makeText(context, "Unable to load model: ${throwable?.message}", Toast.LENGTH_LONG).show()
                        null
                    }
        }

        @JvmStatic
        private fun addNodeToScene(model: ModelRenderable, sceneView: SceneView?, resources: Resources) {
            val scene = sceneView?.scene
            if (scene != null) {
                val transformationSystem = makeTransformationSystem(resources)
                val dragTransformableNode = DragTransformableNode(1f, transformationSystem)
                dragTransformableNode.renderable = model
                scene.addChild(dragTransformableNode)
                dragTransformableNode.select()
                scene.addOnPeekTouchListener { hitTestResult: HitTestResult?, motionEvent: MotionEvent? ->
                    transformationSystem.onTouch(
                            hitTestResult,
                            motionEvent
                    )
                }
            }
        }

        @JvmStatic
        private fun makeTransformationSystem(resources: Resources): TransformationSystem {
            val footprintSelectionVisualizer = FootprintSelectionVisualizer()
            return TransformationSystem(resources.displayMetrics, footprintSelectionVisualizer)
        }
    }
}