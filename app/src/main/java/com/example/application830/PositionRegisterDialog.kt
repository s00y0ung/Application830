package com.example.application830

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.application830.databinding.PositionRegisterDialogBinding

class PositionRegisterDialog(private val context : AppCompatActivity) {

    private lateinit var listener: PositionRegisterDialog.PositionRegisterListener
    private lateinit var binding : PositionRegisterDialogBinding
    private val dig = Dialog(context)

    interface PositionRegisterListener {
        fun onYesClicked(Content:String)
        fun imgClicked(Content:String)
    }

    fun show(){

        binding = PositionRegisterDialogBinding.inflate(context.layoutInflater)

        dig.setContentView(binding.root)

        context.dialogResize(this,1.0f,0.4f)

        //갤러리에 사진 등록해야됨
        binding.imgTrashcan.setOnClickListener {
            //listener.imgClicked("갤러리")
        }
        binding.registerYesBtn.setOnClickListener{
            //등록하는거 관리자에게 전달할 수 있도록 하기 Todo
            listener.onYesClicked("등록요청이 되었습니다.")
            //Toast.makeText(context, "등록 요청이 되었습니다!", Toast.LENGTH_LONG).show()
            dig.dismiss()
        }
        binding.registerNoBtn.setOnClickListener {
            dig.dismiss()
        }
        dig.show()

    }

    fun setOnYesBtnClicked(listener:(String) -> Unit){
        this.listener = object: PositionRegisterDialog.PositionRegisterListener {
            override fun imgClicked(Content: String) {
                listener(Content)
            }

            override fun onYesClicked(Content: String) {
                listener(Content)
            }
        }
    }

    //dialog 크기조절
    fun Context.dialogResize(dialog: PositionRegisterDialog, width: Float, height: Float){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.dig.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.dig.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }
}