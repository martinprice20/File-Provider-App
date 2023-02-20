package com.martinprice20.fileproviderapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.martinprice20.fileproviderapp.databinding.ActivityFileSelectBinding
import java.io.File

class FileSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileSelectBinding
    private lateinit var privateRootDir: File
    private lateinit var secretDataDir: File
    private lateinit var secretFiles: Array<File>
    private lateinit var secretFileNames: Array<String>
    private lateinit var resultIntent: Intent
    private lateinit var finishButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileSelectBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set up an Intent to send back to apps that request a file
        resultIntent = Intent(Intent.ACTION_SEND)
        // Get the files/ subdirectory of internal storage
        privateRootDir = filesDir
        // Get the files/images subdirectory;
        secretDataDir = File(privateRootDir, "secretdata")
        // Get the files in the images subdirectory
        secretFiles = secretDataDir.listFiles() as Array<File>
        // Set the Activity's result to null to begin with
        setResult(Activity.RESULT_CANCELED, null)

        finishButton = binding.finishButton
        finishButton.setOnClickListener {

            finish()
        }

        secretFileNames = secretFiles.map { it.absolutePath }.toTypedArray()

        val listView = binding.secretFilesList
        val secretListAdapter = ArrayAdapter(
            this,
            R.layout.activity_listview,
            R.id.secret_text_view,
            secretFileNames
        )
        listView.adapter = secretListAdapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val requestFile = File(secretFileNames[position])
            val fileUri: Uri? = try {
                FileProvider.getUriForFile(
                    this@FileSelectActivity,
                    "com.martinprice20.fileproviderapp.fileprovider",
                    requestFile)
            } catch (e: java.lang.IllegalArgumentException) {
                Log.e("File Selector", "The Selected file can't be shared: $requestFile")
                null
            }

            if (fileUri != null) {
                resultIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                resultIntent.setDataAndType(fileUri, contentResolver.getType(fileUri))
                setResult(Activity.RESULT_OK, resultIntent)
            } else {
                resultIntent.setDataAndType(null, "")
                setResult(RESULT_CANCELED, resultIntent)
            }

        }
    }
}