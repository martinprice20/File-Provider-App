package com.martinprice20.fileproviderapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.martinprice20.fileproviderapp.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var setTitle: EditText
    private lateinit var setContent: EditText
    private lateinit var encryptButton: Button
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setTitle = binding.setFileName
        setContent = binding.setFileContent
        encryptButton = binding.encryptButton
        saveButton = binding.saveButton
        saveButton.setOnClickListener {
            val title = setTitle.text.toString().trim()
            val content = setContent.text.toString().trim()
            if (title.isNotEmpty() && content.trim().isNotEmpty()
            ) {
                try {
                    val folder = File(this.filesDir, File.separator + FILE_PATH)
                    if (!folder.exists()) {
                        folder.mkdir()
                    }
                    val filePath = File(this.filesDir, FILE_PATH)
                    val newFile = File(filePath, title)
                    FileOutputStream(newFile).use {
                        it.write(content.toByteArray())
                    }
                    setTitle.text.clear()
                    setContent.text.clear()
                    Toast.makeText(this, "Success! New file created in $FILE_PATH", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    Log.e(TAG, e.toString())
                    Toast.makeText(this, "There was an error creating the file", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "You need to add a file name AND some file content", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val FILE_PATH = "secretdata"
        const val TAG = "Main Activity File IO Exception"
    }
}