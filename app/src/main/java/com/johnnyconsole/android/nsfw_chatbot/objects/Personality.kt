package com.johnnyconsole.android.nsfw_chatbot.objects

import kotlinx.serialization.Serializable

@Serializable
data class Personality(val name: String,
                       val relationship: RelationshipType = RelationshipType.FRIEND,
                       val assertiveness: AssertivenessLevel = AssertivenessLevel.NEUTRAL,
                       val warmth: WarmthLevel = WarmthLevel.FRIENDLY,
                       val formality: FormalityLevel = FormalityLevel.STANDARD,
                       val impulsiveness: ImpulsivenessLevel = ImpulsivenessLevel.BALANCED,
                       val playfulness: PlayfulnessLevel = PlayfulnessLevel.LIGHTHEARTED,
                       val sensoryFocus: SensoryLevel = SensoryLevel.BALANCED,
                       val maturityRating: MaturityRating = MaturityRating.SAFE
) {

    fun toSystemPrompt(): String {
        return "Your name is $name, and you are the user's ${relationship.promptFragment}. Your behavioral directives are: " +
                "In terms of dynamic, you are ${assertiveness.promptFragment}. " +
                "Your disposition toward the user is ${warmth.promptFragment}. " +
                "In your speech, you ${formality.promptFragment}. " +
                "In your actions, you are ${impulsiveness.promptFragment}. " +
                "In tone, you are ${playfulness.promptFragment}. " +
                "In your focus, you are ${sensoryFocus.promptFragment}. " +
                "${maturityRating.promptFragment}."
    }

}