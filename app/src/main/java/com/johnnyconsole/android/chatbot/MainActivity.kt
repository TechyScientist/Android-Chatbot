package com.johnnyconsole.android.chatbot

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.johnnyconsole.android.chatbot.databinding.ActivityMainBinding
import com.johnnyconsole.android.chatbot.objects.AssertivenessLevel
import com.johnnyconsole.android.chatbot.objects.ChatSession
import com.johnnyconsole.android.chatbot.objects.FormalityLevel
import com.johnnyconsole.android.chatbot.objects.ImpulsivenessLevel
import com.johnnyconsole.android.chatbot.objects.MaturityRating
import com.johnnyconsole.android.chatbot.objects.Personality
import com.johnnyconsole.android.chatbot.objects.PlayfulnessLevel
import com.johnnyconsole.android.chatbot.objects.RelationshipType
import com.johnnyconsole.android.chatbot.objects.SensoryLevel
import com.johnnyconsole.android.chatbot.objects.WarmthLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private var modelDownloaded = false
    private var personalitySet = false

    // Chosen Model: Gemma 4 E2B Uncensored-MAX (HuggingFace/PeppX)
    private val modelUrl =
        "https://huggingface.co/PeppX/gemma-4-e2b-uncensored-litertlm/resolve/main/gemma-4-E2B-it-Uncensored-MAX.litertlm"
    private val modelFile = "gemma-4-E2B-it-Uncensored-MAX.litertlm"
    private val format = Json {
        encodeDefaults = true
        prettyPrint = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences("Chatbot", MODE_PRIVATE)
        modelDownloaded = preferences.getBoolean("ModelDownloaded", false)
        personalitySet = preferences.getBoolean("PersonalitySet", false)

        if (modelDownloaded && personalitySet) {
            navigateToChat()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            enableEdgeToEdge()
            setContentView(root)
            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }

            val roleBoxes = arrayOf(rbRoleFriend, rbRoleGirlfriend, rbRoleBoyfriend,
                rbRoleCompanion, rbRolePartner, rbRoleCoworker, rbRolePersonalRival,
                rbRoleAcademicRival, rbRoleMentor)

            for(button in roleBoxes) {
                button.setOnCheckedChangeListener { thisButton, checked ->
                    for(b in roleBoxes) {
                        b.isChecked = false
                    }
                    thisButton.isChecked = checked
                }
            }

            btSave.setOnClickListener {
                if (modelDownloaded && etBotName.text.isNotBlank() && etUserName.text.isNotBlank()) {
                    val botName = etBotName.text.toString()
                    val userName = etUserName.text.toString()
                    val relationship = when {
                        rbRoleFriend.isChecked -> RelationshipType.FRIEND
                        rbRoleGirlfriend.isChecked -> RelationshipType.GIRLFRIEND
                        rbRoleBoyfriend.isChecked -> RelationshipType.BOYFRIEND
                        rbRoleCompanion.isChecked -> RelationshipType.COMPANION
                        rbRolePartner.isChecked -> RelationshipType.PARTNER
                        rbRoleCoworker.isChecked -> RelationshipType.COWORKER
                        rbRolePersonalRival.isChecked -> RelationshipType.RIVAL_PERSONAL
                        rbRoleAcademicRival.isChecked -> RelationshipType.RIVAL_ACADEMIC
                        else -> RelationshipType.MENTOR
                    }
                    val assertiveness = when (rgAssertiveness.checkedRadioButtonId) {
                        rbAssertSubmissive.id -> AssertivenessLevel.SUBMISSIVE
                        rbAssertNeutral.id -> AssertivenessLevel.NEUTRAL
                        else -> AssertivenessLevel.DOMINANT
                    }
                    val warmth = when (rgWarmth.checkedRadioButtonId) {
                        rbWarmthCold.id -> WarmthLevel.COLD
                        rbWarmthFriendly.id -> WarmthLevel.FRIENDLY
                        else -> WarmthLevel.AFFECTIONATE
                    }
                    val formality = when (rgFormality.checkedRadioButtonId) {
                        rbFormalityCasual.id -> FormalityLevel.CASUAL
                        rbFormalityStandard.id -> FormalityLevel.STANDARD
                        else -> FormalityLevel.ELEGANT
                    }
                    val impulsiveness = when (rgImpulsiveness.checkedRadioButtonId) {
                        rbImpulsivenessCalculated.id -> ImpulsivenessLevel.CALCULATED
                        rbImpulsivenessBalanced.id -> ImpulsivenessLevel.BALANCED
                        else -> ImpulsivenessLevel.SPONTANEOUS
                    }
                    val playfulness = when (rgPlayfulness.checkedRadioButtonId) {
                        rbPlayfulnessSerious.id -> PlayfulnessLevel.SERIOUS
                        rbPlayfulnessLighthearted.id -> PlayfulnessLevel.LIGHTHEARTED
                        else -> PlayfulnessLevel.TEASING
                    }
                    val sensory = when (rgSensory.checkedRadioButtonId) {
                        rbSensoryAnalytical.id -> SensoryLevel.ANALYTICAL
                        rbSensoryBalanced.id -> SensoryLevel.BALANCED
                        else -> SensoryLevel.PHYSICAL
                    }
                    val maturity = when (rgMaturity.checkedRadioButtonId) {
                        rbMaturitySafe.id -> MaturityRating.SAFE
                        rbMaturitySuggestive.id -> MaturityRating.SUGGESTIVE
                        else -> MaturityRating.EXPLICIT
                    }

                    val session = ChatSession(
                        Personality(
                            userName, botName, relationship, assertiveness, warmth,
                            formality, impulsiveness, playfulness,
                            sensory, maturity
                        ),
                        emptyList()
                    )

                    preferences.edit {
                        putBoolean("PersonalitySet", true)
                        putString("session", format.encodeToString(session))
                    }

                    navigateToChat()
                }
            }

            val model = File(getExternalFilesDir(null), modelFile)

            if (!modelDownloaded) handleModelDownload(model)
        }
    }

    private fun handleModelDownload(model: File) {
        if (model.exists()) {
            model.delete()
        }
        val dialogView = layoutInflater.inflate(R.layout.layout_model_dialog, null)
        val progressBar = dialogView.findViewById<ProgressBar>(R.id.downloadProgressBar)
        val progressText = dialogView.findViewById<TextView>(R.id.downloadProgressText)

        val progressDialog = AlertDialog.Builder(this)
            .setTitle(R.string.download_title)
            .setMessage(R.string.download_message)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        progressDialog.show()

        lifecycleScope.launch(Dispatchers.Main) {
            val downloadSuccess = withContext(Dispatchers.IO) {
                downloadModelFile(model) { bytesRead, totalBytes ->
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
                preferences.edit {
                    putBoolean("ModelDownloaded", true)
                }
                modelDownloaded = true
            }
        }
    }

    private fun navigateToChat() {
        startActivity(Intent(this, ChatActivity::class.java))
        finish()
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