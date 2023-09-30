package com.example.kidsdrawingapp

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.nvt.color.ColorPickerDialog
import java.lang.String

class MainActivity : AppCompatActivity() {

    private var drawingView: DrawingView? = null
    private var mImageButtonCurrentPaint: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20F)

        val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.ll_paint_colors)
        mImageButtonCurrentPaint = linearLayoutPaintColors[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
        )

        val ib_brush : ImageButton = findViewById(R.id.ib_brush)
        ib_brush.setOnClickListener{

            showBrushSizeChooserDialog()
        }

        val eraserButton = findViewById<ImageButton>(R.id.ib_eraser)
        eraserButton.setOnClickListener{
            drawingView?.setColor("white")
        }

        val randomColorBtn: ImageButton = findViewById(R.id.ib_randomColor)
        randomColorBtn.setOnClickListener{
            val colorPicker = ColorPickerDialog(
                this,
                Color.BLACK, // color init
                true, // true is show alpha
                object : ColorPickerDialog.OnColorPickerListener {
                    override fun onCancel(dialog: ColorPickerDialog?) {
                        // handle click button Cancel
                    }

                    override fun onOk(dialog: ColorPickerDialog?, colorPicker: Int) {
                        // handle click button OK
                        drawingView?.setColor(String.format("#%06X", 0xFFFFFF and colorPicker))
                    }
                })
            colorPicker.show()
        }

    }

    private fun showBrushSizeChooserDialog()
    {
        val brushDialog = Dialog(this)
        brushDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setCancelable(false)
        brushDialog.setTitle("Brush size:")

        val smallBtn: ImageButton  = brushDialog.findViewById(R.id.ib_small_brush)
        val mediumBtn: ImageButton  = brushDialog.findViewById(R.id.ib_medium_brush)
        val largeBtn: ImageButton  = brushDialog.findViewById(R.id.ib_large_brush)

        smallBtn.setOnClickListener{
            drawingView?.setSizeForBrush(10F)
            brushDialog.dismiss()
        }

        mediumBtn.setOnClickListener{
            drawingView?.setSizeForBrush(20F)
            brushDialog.dismiss()
        }

        largeBtn.setOnClickListener{
            drawingView?.setSizeForBrush(30F)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    fun paintClicked(view:View)
    {
        if(view != mImageButtonCurrentPaint){
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawingView?.setColor(colorTag)

            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            )

            mImageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal)
            )

            mImageButtonCurrentPaint = view
        }
    }



}