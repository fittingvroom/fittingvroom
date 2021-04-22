package com.fittingvroom.ui.fitting

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fittingvroom.databinding.FragmentFittingBinding
import com.fittingvroom.ui.fitting.nodes.DragTransformableNode
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer
import com.google.ar.sceneform.ux.TransformationSystem
import java.util.function.Consumer
import java.util.function.Function


class FittingFragment : Fragment() {

    private var binding: FragmentFittingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFittingBinding.inflate(inflater, container, false)
        val view = binding?.root
        init()
        return view
    }

    private fun init() {
        val GLTF_ASSET =
            "https://github.com/shindje/model/blob/master/model(server_telegram).glb?raw=true"

        Toast.makeText(
            context, "Loading model...", Toast.LENGTH_SHORT
        ).show()

        var source = RenderableSource.builder().setSource(
            context,
            Uri.parse(GLTF_ASSET),
            RenderableSource.SourceType.GLB
        )
            .setScale(0.7f) // Scale the original model to 70%.
            .setRecenterMode(RenderableSource.RecenterMode.CENTER)
            .build()

        ModelRenderable.builder()
            .setSource(
                context,
                source
            )
            .setRegistryId(GLTF_ASSET)
            .build()
            .thenAccept { renderable: ModelRenderable ->
                Toast.makeText(
                    context, "Model loaded", Toast.LENGTH_SHORT
                ).show()
                addNodeToScene(renderable)
            }
            .exceptionally{ throwable: Throwable? ->
                    val toast: Toast = Toast.makeText(
                        context, "Unable to load renderable " +
                                GLTF_ASSET, Toast.LENGTH_LONG
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    null
                }
    }

    private fun addNodeToScene(model: ModelRenderable) {
        val scene = binding!!.fittingSceneView.scene
        if (scene != null) {
            val transformationSystem = makeTransformationSystem()
            var dragTransformableNode = DragTransformableNode(1f, transformationSystem)
            dragTransformableNode?.renderable = model
            scene.addChild(dragTransformableNode)
            dragTransformableNode?.select()
            scene
                .addOnPeekTouchListener { hitTestResult: HitTestResult?, motionEvent: MotionEvent? ->
                    transformationSystem.onTouch(
                        hitTestResult,
                        motionEvent
                    )
                }
        }
    }

    private fun makeTransformationSystem(): TransformationSystem {
        val footprintSelectionVisualizer = FootprintSelectionVisualizer()
        return TransformationSystem(resources.displayMetrics, footprintSelectionVisualizer)
    }

    override fun onResume() {
        super.onResume()
        binding?.fittingSceneView?.resume()
    }


    override fun onPause() {
        super.onPause()
        binding?.fittingSceneView?.pause()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}