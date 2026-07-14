package com.johnnyconsole.android.nsfw_chatbot

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.johnnyconsole.android.nsfw_chatbot.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Chosen Model: Gemma 4 E2B Uncensored-MAX (HuggingFace/PeppX)
    private val modelUrl =
        "https://huggingface.co/PeppX/gemma-4-e2b-uncensored-litertlm/resolve/main/gemma-4-E2B-it-Uncensored-MAX.litertlm"
    private val modelFile = "gemma-4-E2B-it-Uncensored-MAX.litertlm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val preferences = getSharedPreferences("NSFWChatbot", MODE_PRIVATE)
        var modelDownloaded = preferences.getBoolean("ModelDownloaded", false)

        with(binding) {
            enableEdgeToEdge()
            setContentView(root)
            ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }

            val model = File(getExternalFilesDir(null), modelFile)

            if (!modelDownloaded) {
                if (model.exists()) {
                    model.delete()
                }
                val dialogView = layoutInflater.inflate(R.layout.layout_model_dialog, null)
                val progressBar = dialogView.findViewById<ProgressBar>(R.id.downloadProgressBar)
                val progressText = dialogView.findViewById<TextView>(R.id.downloadProgressText)

                val progressDialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.download_title)
                    .setMessage(R.string.download_message)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create()

                progressDialog.show()

                lifecycleScope.launch(Dispatchers.Main) {
                    val downloadSuccess = withContext(Dispatchers.IO) {
                        downloadModelFile(model) { bytesRead, totalBytes ->
                            // Update progress on the Main Thread
                            lifecycleScope.launch(Dispatchers.Main) {
                                if (totalBytes > 0) {
                                    val progress = (bytesRead * 100 / totalBytes).toInt()
                                    progressBar.progress = progress
                                    progressText.text = getString(
                                        R.string.download_progress,
                                        progress, bytesRead / (1024 * 1024),
                                        totalBytes / (1024 * 1024)
                                    )
                                } else {
                                    progressText.text = getString(
                                        R.string.download_progress,
                                        0, bytesRead / (1024 * 1024),
                                        totalBytes / (1024 * 1024)
                                    )
                                }
                            }
                        }
                    }

                    progressDialog.dismiss()
                    if (downloadSuccess) {
                        preferences.edit { putBoolean("ModelDownloaded", true) }
                        modelDownloaded = true
                    }
                }
            }
            if (modelDownloaded) {
                //TODO: Add listener to set-up button here
            }
        }
    }

    private fun downloadModelFile(outputFile: File,
        onProgress: (bytesRead: Long, totalBytes: Long) -> Unit
    ): Boolean {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(modelUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                return false
            }

            val fileLength = connection.contentLengthLong
            val input = connection.inputStream
            val output = FileOutputStream(outputFile)

            val data = ByteArray(4096)
            var total: Long = 0
            var count: Int

            while (input.read(data).also { count = it } != -1) {
                total += count
                onProgress(total, fileLength)
                output.write(data, 0, count)
            }

            output.flush()
            output.close()
            input.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (outputFile.exists()) outputFile.delete()
            return false
        } finally {
            connection?.disconnect()
        }
    }
}